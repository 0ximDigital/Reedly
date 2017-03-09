package oxim.digital.reedly.data.feed.parser;

import java.io.InputStream;

import oxim.digital.reedly.data.feed.model.ApiFeed;
import rx.Single;

public interface FeedParser {

    Single<ApiFeed> parseFeed(InputStream inputStream);
}
