package oxim.digital.reedly.data.feed.service.parser;

import com.einmalfel.earl.EarlParser;
import com.einmalfel.earl.Feed;

import java.io.InputStream;

import rx.Single;

public final class FeedParserImpl implements FeedParser {

    public FeedParserImpl() {
    }

    @Override
    public Single<Feed> parseFeed(final InputStream inputStream) {
        return Single.defer(() -> Single.just(EarlParser.parseOrThrow(inputStream, 0)));
    }
}
