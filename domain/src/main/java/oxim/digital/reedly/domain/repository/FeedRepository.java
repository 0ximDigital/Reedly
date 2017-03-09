package oxim.digital.reedly.domain.repository;

import oxim.digital.reedly.domain.model.Feed;
import rx.Single;

public interface FeedRepository {

    Single<Feed> getFeed(String url);

    // Get user feeds
    // Add feed
    // remove feed
    // getItemsForFeed

}
