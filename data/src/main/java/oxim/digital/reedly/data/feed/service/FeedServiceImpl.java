package oxim.digital.reedly.data.feed.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import oxim.digital.reedly.data.feed.converter.FeedModelConverter;
import oxim.digital.reedly.data.feed.parser.FeedParser;
import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.service.FeedService;
import rx.Single;

public final class FeedServiceImpl implements FeedService {

    private final FeedParser feedParser;
    private final FeedModelConverter feedModelConverter;

    public FeedServiceImpl(final FeedParser feedParser, final FeedModelConverter feedModelConverter) {
        this.feedParser = feedParser;
        this.feedModelConverter = feedModelConverter;
    }

    @Override
    public Single<Feed> fetchFeed(final String url) {
        try {
            final InputStream inputStream = new URL(url).openConnection().getInputStream();
            return feedParser.parseFeed(inputStream)
                             .map(feedModelConverter::apiToDomain)
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
}
