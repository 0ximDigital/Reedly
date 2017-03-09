package oxim.digital.reedly.data.feed.service.parser;

import android.support.annotation.Nullable;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.einmalfel.earl.EarlParser;
import com.einmalfel.earl.Feed;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import oxim.digital.reedly.data.feed.service.model.ApiFeed;
import oxim.digital.reedly.data.feed.service.model.ApiFeedItem;
import oxim.digital.reedly.data.util.CurrentTimeProvider;
import rx.Single;

public final class FeedParserImpl implements FeedParser {

    private final CurrentTimeProvider currentTimeProvider;

    public FeedParserImpl(final CurrentTimeProvider currentTimeProvider) {
        this.currentTimeProvider = currentTimeProvider;
    }

    @Override
    public Single<ApiFeed> parseFeed(final InputStream inputStream) {
        return Single.defer(() -> Single.just(EarlParser.parseOrThrow(inputStream, 0))
                                        .map(this::mapToApiFeed));
    }

    private ApiFeed mapToApiFeed(final Feed parsedFeed) {
        final List<ApiFeedItem> apiFeedItems = Stream.of(parsedFeed.getItems())
                                                     .map(feedItem -> new ApiFeedItem(feedItem.getTitle(), feedItem.getLink(), feedItem.getDescription(),
                                                                                      getTimeForDate(feedItem.getPublicationDate())))
                                                     .collect(Collectors.toList());
        return new ApiFeed(parsedFeed.getTitle(), parsedFeed.getImageLink(), parsedFeed.getLink(), parsedFeed.getDescription(), apiFeedItems);
    }

    private long getTimeForDate(@Nullable final Date date) {
        return (date != null) ? date.getTime() : currentTimeProvider.getCurrentTime();
    }
}
