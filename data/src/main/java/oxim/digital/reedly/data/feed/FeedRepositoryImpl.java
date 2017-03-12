package oxim.digital.reedly.data.feed;

import java.util.List;

import oxim.digital.reedly.data.feed.db.FeedDao;
import oxim.digital.reedly.data.feed.service.FeedService;
import oxim.digital.reedly.data.util.PreferenceUtils;
import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.model.FeedItem;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;
import rx.Single;
import rx.schedulers.Schedulers;

public final class FeedRepositoryImpl implements FeedRepository {

    private final FeedService feedService;
    private final FeedDao feedDao;

    private final PreferenceUtils preferenceUtils;

    public FeedRepositoryImpl(final FeedService feedService, final FeedDao feedDao, final PreferenceUtils preferenceUtils) {
        this.feedService = feedService;
        this.feedDao = feedDao;
        this.preferenceUtils = preferenceUtils;
    }

    @Override
    public Single<List<Feed>> getUserFeeds() {
        return Single.defer(feedDao::getAllFeeds)
                     .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<FeedItem>> getFeedItems(final int feedId) {
        return Single.defer(() -> feedDao.getFeedItemsForFeed(feedId))
                     .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Boolean> feedExists(final String feedUrl) {
        return Single.defer(() -> feedDao.doesFeedExist(feedUrl))
                     .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable createNewFeed(final String feedUrl) {
        return Completable.defer(() -> feedService.fetchFeed(feedUrl)
                                                  .flatMap(apiFeed -> feedDao.insertFeed(apiFeed).toSingleDefault(apiFeed))
                                                  .toObservable()
                                                  .toCompletable())
                          .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteFeed(final int feedId) {
        return Completable.defer(() -> feedDao.deleteFeed(feedId))
                          .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable updateFeedItems(final Feed feed) {
        return Completable.defer(() -> feedService.fetchFeed(feed.url)
                                                  .flatMap(apiFeed -> feedDao.updateFeed(feed.id, apiFeed.items).toSingleDefault(true))
                                                  .toObservable()
                                                  .toCompletable())
                          .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable markFeedItemAsRead(final int feedItemId) {
        return Completable.defer(() -> feedDao.markFeedItemAsRead(feedItemId));
    }

    @Override
    public Completable favouriteFeedItem(final int feedItemId) {
        return Completable.defer(() -> feedDao.favouriteFeedItem(feedItemId));
    }

    @Override
    public Completable unFavouriteFeedItem(final int feedItemId) {
        return Completable.defer(() -> feedDao.unFavouriteFeedItem(feedItemId));
    }

    @Override
    public Single<Long> getUnreadFeedItemsCount() {
        return Single.defer(feedDao::getUnreadFeedItemsCount);
    }

    @Override
    public Single<List<FeedItem>> getFavouriteFeedItems() {
        return Single.defer(feedDao::getFavouriteFeedItems);
    }

    @Override
    public Single<Boolean> shouldUpdateFeedsInBackground() {
        return Single.fromCallable(preferenceUtils::shouldUpdateFeedsInBackground);
    }

    @Override
    public Completable setShouldUpdateFeedsInBackground(final boolean shouldUpdate) {
        return Completable.fromAction(() -> preferenceUtils.setShouldUpdateFeedsInBackground(shouldUpdate));
    }
}
