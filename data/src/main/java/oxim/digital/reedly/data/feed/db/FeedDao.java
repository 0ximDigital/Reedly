package oxim.digital.reedly.data.feed.db;

import java.util.List;

import oxim.digital.reedly.data.feed.service.model.ApiFeed;
import oxim.digital.reedly.data.feed.service.model.ApiFeedItem;
import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.model.FeedItem;
import rx.Completable;
import rx.Single;

public interface FeedDao {

    Single<List<Feed>> getAllFeeds();

    Completable insertFeed(ApiFeed apiFeed);

    Completable updateFeed(int feedId, List<ApiFeedItem> apiFeedItems);

    Single<List<FeedItem>> getFeedItemsForFeed(int feedId);

    Single<Boolean> doesFeedExist(String feedUrl);

    Completable deleteFeed(int feedId);

    Completable markFeedItemAsRead(final int feedItemId);

    Completable favouriteFeedItem(final int feedItemId);

    Completable unFavouriteFeedItem(final int feedItemId);
}
