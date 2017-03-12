package oxim.digital.reedly.data.feed.db;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.raizlabs.android.dbflow.sql.language.Method;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

import oxim.digital.reedly.data.feed.converter.FeedModelConverter;
import oxim.digital.reedly.data.feed.db.model.FeedItemModel;
import oxim.digital.reedly.data.feed.db.model.FeedItemModel_Table;
import oxim.digital.reedly.data.feed.db.model.FeedModel;
import oxim.digital.reedly.data.feed.db.model.FeedModel_Table;
import oxim.digital.reedly.data.feed.service.model.ApiFeed;
import oxim.digital.reedly.data.feed.service.model.ApiFeedItem;
import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.model.FeedItem;
import rx.Completable;
import rx.Single;

import static com.raizlabs.android.dbflow.sql.language.SQLite.select;

public class FeedDaoImpl implements FeedDao {

    private final FeedModelConverter feedModelConverter;

    public FeedDaoImpl(final FeedModelConverter feedModelConverter) {
        this.feedModelConverter = feedModelConverter;
    }

    @Override
    public Completable insertFeed(final ApiFeed apiFeed) {
        return Completable.fromAction(() -> innerInsertFeed(apiFeed));
    }

    // TODO - use transactions for both feed and feed items
    private void innerInsertFeed(final ApiFeed apiFeed) {
        final FeedModel feedModel = feedModelConverter.apiToModel(apiFeed);
        feedModel.save();

        Stream.of(apiFeed.items)
              .map(apiFeedItem -> feedModelConverter.apiToModel(apiFeedItem, feedModel.getId()))
              .forEach(BaseModel::save);
    }

    @Override
    public Single<List<Feed>> getAllFeeds() {
        return Single.just(Stream.of(SQLite.select()
                                           .from(FeedModel.class)
                                           .queryList())
                                 .map(feedModelConverter::modelToDomain)
                                 .collect(Collectors.toList()));
    }

    @Override
    public Completable updateFeed(final int feedId, List<ApiFeedItem> apiFeedItems) {
        return Completable.fromAction(() -> innerUpdateFeed(feedId, apiFeedItems));
    }

    private void innerUpdateFeed(final int feedId, List<ApiFeedItem> apiFeedItems) {
        Stream.of(apiFeedItems)
              .map(apiFeedItem -> feedModelConverter.apiToModel(apiFeedItem, feedId))
              .forEach(BaseModel::save);
    }

    @Override
    public Single<List<FeedItem>> getFeedItemsForFeed(final int feedId) {
        return Single.defer(() -> Single.just(innerGetFeedItemsForFeed(feedId)));
    }

    private List<FeedItem> innerGetFeedItemsForFeed(final int feedId) {
        return Stream.of(select().from(FeedItemModel.class)
                                 .where(FeedItemModel_Table.feedId.eq(feedId))
                                 .orderBy(FeedItemModel_Table.publicationDate, false)
                                 .queryList())
                     .map(feedModelConverter::modelToDomain)
                     .collect(Collectors.toList());
    }

    @Override
    public Single<Boolean> doesFeedExist(final String feedUrl) {
        return Single.defer(() -> Single.just(innerDoesFeedExist(feedUrl)));
    }

    private boolean innerDoesFeedExist(final String feedUrl) {
        return !SQLite.select()
                      .from(FeedModel.class)
                      .where(FeedModel_Table.url.eq(feedUrl))
                      .queryList()
                      .isEmpty();
    }

    @Override
    public Completable deleteFeed(final int feedId) {
        return Completable.fromAction(() -> innerDeleteFeed(feedId));
    }

    @Override
    public Completable markFeedItemAsRead(final int feedItemId) {
        return Completable.fromAction(() -> SQLite.update(FeedItemModel.class)
                                                  .set(FeedItemModel_Table.isNew.eq(false))
                                                  .where(FeedItemModel_Table.id.eq(feedItemId))
                                                  .execute());
    }

    @Override
    public Completable favouriteFeedItem(final int feedItemId) {
        return Completable.fromAction(() -> setFavouriteToFeedItem(true, feedItemId));
    }

    @Override
    public Completable unFavouriteFeedItem(final int feedItemId) {
        return Completable.fromAction(() -> setFavouriteToFeedItem(false, feedItemId));
    }

    @Override
    public Single<Long> getUnreadFeedItemsCount() {
        return Single.fromCallable(() -> SQLite.select(Method.count())
                                               .from(FeedItemModel.class)
                                               .where(FeedItemModel_Table.isNew.eq(true))
                                               .count());
    }

    @Override
    public Single<List<FeedItem>> getFavouriteFeedItems() {
        return Single.fromCallable(this::innerGetFavouriteFeedItems);
    }

    private List<FeedItem> innerGetFavouriteFeedItems() {
        return Stream.of(SQLite.select()
                               .from(FeedItemModel.class)
                               .where(FeedItemModel_Table.isFavourite.eq(true))
                               .queryList())
                     .map(feedModelConverter::modelToDomain)
                     .collect(Collectors.toList());
    }

    private void setFavouriteToFeedItem(final boolean isFavourite, final int feedItemId) {
        SQLite.update(FeedItemModel.class)
              .set(FeedItemModel_Table.isFavourite.eq(isFavourite))
              .where(FeedItemModel_Table.id.eq(feedItemId))
              .execute();
    }

    private void innerDeleteFeed(final int feedId) {
        deleteFeedItemsForFeed(feedId);
        SQLite.delete(FeedModel.class)
              .where(FeedModel_Table.id.eq(feedId))
              .execute();
    }

    private void deleteFeedItemsForFeed(final int feedId) {
        SQLite.delete(FeedItemModel.class)
              .where(FeedItemModel_Table.feedId.eq(feedId))
              .execute();
    }
}
