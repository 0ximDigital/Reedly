package oxim.digital.reedly.data.feed.service.parser;

import com.einmalfel.earl.Feed;

import java.io.InputStream;

import oxim.digital.reedly.data.feed.service.model.ApiFeed;
import rx.Single;

public interface FeedParser {

    Single<Feed> parseFeed(InputStream inputStream);
}
