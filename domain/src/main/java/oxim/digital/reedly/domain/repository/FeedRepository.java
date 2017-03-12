package oxim.digital.reedly.domain.repository;

import java.util.List;

import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.model.FeedItem;
import rx.Completable;
import rx.Single;

public interface FeedRepository {

    Single<List<Feed>> getUserFeeds();

    Single<List<FeedItem>> getFeedItems(int feedId);

    Single<Boolean> feedExists(String feedUrl);

    Completable createNewFeed(String feedUrl);

    Completable deleteFeed(int feedId);

    Completable updateFeedItems(Feed feed);

    Completable markFeedItemAsRead(int feedItemId);

    Completable favouriteFeedItem(int feedItemId);

    Completable unFavouriteFeedItem(int feedItemId);

    Single<Long> getUnreadFeedItemsCount();

    Single<List<FeedItem>> getFavouriteFeedItems();
}
