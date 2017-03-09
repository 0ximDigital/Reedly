package oxim.digital.reedly.feed.subscription;

import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import oxim.digital.reedly.base.BasePresenter;
import oxim.digital.reedly.data.feed.db.model.FeedItemModel;
import oxim.digital.reedly.data.feed.db.model.FeedModel;
import oxim.digital.reedly.data.util.CurrentTimeProvider;
import oxim.digital.reedly.domain.interactor.GetFeedUseCase;
import oxim.digital.reedly.domain.interactor.GetUserSubscribedFeedsUseCase;
import oxim.digital.reedly.feed.mapper.FeedViewModeMapper;

public final class UserSubscriptionsPresenter extends BasePresenter<UserSubscriptionsContract.View> implements UserSubscriptionsContract.Presenter {

    @Inject
    GetUserSubscribedFeedsUseCase getUserSubscribedFeedsUseCase;

    @Inject
    GetFeedUseCase getFeedUseCase;

    @Inject
    FeedViewModeMapper feedViewModeMapper;

    @Inject
    CurrentTimeProvider currentTimeProvider;

    private static final String TEST_FEED_URL = "https://xkcd.com/rss.xml";

    public UserSubscriptionsPresenter(final UserSubscriptionsContract.View view) {
        super(view);
    }

    @Override
    public void fetchUserSubscriptions() {
//        viewActionQueue.subscribeTo(getFeedUseCase.execute(TEST_FEED_URL)
//                                                  .map(feedViewModeMapper::mapFeedToViewModel)
//                                                  .map(feedViewModel -> (Action1<UserSubscriptionsContract.View>) view -> view
//                                                          .showFeedSubscriptions(Arrays.asList(feedViewModel))),
//                                    Throwable::printStackTrace);

        final FeedItemModel feedItemModelOne = new FeedItemModel("TitleOne", "LinkOne", "DescriptionOne", currentTimeProvider.getCurrentTime());
        final FeedItemModel feedItemModelTwo = new FeedItemModel("TitleTwo", "LinkTwo", "DescriptionTwo", currentTimeProvider.getCurrentTime());

        final FeedModel feedModel = new FeedModel("FeedTitle", "ImageUrl", "FeedLink", "Feed decription");

        feedModel.save();

        feedModel.setFeedItemModels(Arrays.asList(feedItemModelOne, feedItemModelTwo));

        Log.i("TAG", "Set items");

        feedModel.save();

        final List<FeedModel> feedModels = SQLite.select()
                                                 .from(FeedModel.class)
                                                 .queryList();

        Log.i("MODELS", String.valueOf(feedModels));
    }
}
