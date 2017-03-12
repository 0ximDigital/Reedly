package oxim.digital.reedly.data.feed.service;

import android.support.annotation.Nullable;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.einmalfel.earl.Feed;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

import oxim.digital.reedly.data.feed.service.model.ApiFeed;
import oxim.digital.reedly.data.feed.service.model.ApiFeedItem;
import oxim.digital.reedly.data.feed.service.parser.FeedParser;
import oxim.digital.reedly.data.util.CurrentTimeProvider;
import rx.Single;

public final class FeedServiceImpl implements FeedService {

    private final FeedParser feedParser;
    private final CurrentTimeProvider currentTimeProvider;

    public FeedServiceImpl(final FeedParser feedParser, final CurrentTimeProvider currentTimeProvider) {
        this.feedParser = feedParser;
        this.currentTimeProvider = currentTimeProvider;
    }

    @Override
    public Single<ApiFeed> fetchFeed(final String feedUrl) {
        try {
            final InputStream inputStream = new URL(feedUrl).openConnection().getInputStream();
            return feedParser.parseFeed(inputStream)
                             .map(parsedFeed -> mapToApiFeed(parsedFeed, feedUrl))
                             .doOnSuccess(feed -> closeStream(inputStream))
                             .doOnError(throwable -> closeStream(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
            return Single.error(e);
        }
    }

    private void closeStream(final InputStream inputStream) {
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ApiFeed mapToApiFeed(final Feed parsedFeed, final String feedUrl) {
        final List<ApiFeedItem> apiFeedItems = Stream.of(parsedFeed.getItems())
                                                     .map(feedItem -> new ApiFeedItem(feedItem.getTitle(), feedItem.getLink(), getTimeForDate(feedItem.getPublicationDate())))
                                                     .collect(Collectors.toList());
        return new ApiFeed(parsedFeed.getTitle(), parsedFeed.getImageLink(), parsedFeed.getLink(), parsedFeed.getDescription(), feedUrl, apiFeedItems);
    }

    private long getTimeForDate(@Nullable final Date date) {
        return (date != null) ? date.getTime() : currentTimeProvider.getCurrentTime();
    }
}
