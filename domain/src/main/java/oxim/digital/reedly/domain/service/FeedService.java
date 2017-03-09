package oxim.digital.reedly.domain.service;

import oxim.digital.reedly.domain.model.Feed;
import rx.Observable;
import rx.Single;

public interface FeedService {

    Single<Feed> fetchFeed(final String url);
}
