package oxim.digital.reedly.data.feed.db;

import java.util.List;

import oxim.digital.reedly.data.feed.service.model.ApiFeed;
import oxim.digital.reedly.data.feed.service.model.ApiFeedItem;
import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.model.FeedItem;
import rx.Completable;
import rx.Single;

public interface FeedDao {

    // insertOrUpdate ?
    Completable insertFeed(ApiFeed apiFeed);

    Completable insertFeedTopicForFeed(ApiFeedItem apiFeedItem, ApiFeed apiFeed);

    Single<Feed> getFeedForUrl(String url);

    Single<List<FeedItem>> getFeedItemsForFeed(Feed feed);

    Completable deleteFeed(Feed feed);

}
