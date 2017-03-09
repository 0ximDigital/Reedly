package oxim.digital.reedly.data.feed.db;

import java.util.List;

import oxim.digital.reedly.data.feed.service.model.ApiFeed;
import oxim.digital.reedly.data.feed.service.model.ApiFeedItem;
import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.model.FeedItem;
import rx.Completable;
import rx.Single;

public class FeedDaoImpl implements FeedDao {

    @Override
    public Completable insertFeed(final ApiFeed apiFeed) {
        return null;
    }

    @Override
    public Completable insertFeedTopicForFeed(final ApiFeedItem apiFeedItem, final ApiFeed apiFeed) {
        return null;
    }

    @Override
    public Single<Feed> getFeedForUrl(final String url) {
        return null;
    }

    @Override
    public Single<List<FeedItem>> getFeedItemsForFeed(final Feed feed) {
        return null;
    }

    @Override
    public Completable deleteFeed(final Feed feed) {
        return null;
    }
}
