package oxim.digital.reedly.data.feed.db;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
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
        // TODO - ordering
        return Single.defer(() -> Single.just(innerGetFeedItemsForFeed(feedId)));
    }

    private List<FeedItem> innerGetFeedItemsForFeed(final int feedId) {
        return Stream.of(select().from(FeedItemModel.class)
                                 .where(FeedItemModel_Table.feedId.eq(feedId))
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
    public Completable deleteFeed(final Feed feed) {
        return Completable.fromAction(() -> innerDeleteFeed(feed));
    }

    private void innerDeleteFeed(final Feed feed) {
        deleteFeedItemsForFeed(feed);
        SQLite.delete(FeedModel.class)
              .where(FeedModel_Table.id.eq(feed.id))
              .execute();
    }

    private void deleteFeedItemsForFeed(final Feed feed) {
        SQLite.delete(FeedItemModel.class)
              .where(FeedItemModel_Table.feedId.eq(feed.id))
              .execute();
    }
}
