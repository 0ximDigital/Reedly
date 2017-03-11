package oxim.digital.reedly.ui.feed.subscription;

import android.util.Log;

import com.annimon.stream.Stream;

import java.util.List;

import javax.inject.Inject;

import oxim.digital.reedly.base.BasePresenter;
import oxim.digital.reedly.domain.interactor.AddNewFeedUseCase;
import oxim.digital.reedly.domain.interactor.DeleteFeedUseCase;
import oxim.digital.reedly.domain.interactor.GetFeedItemsUseCase;
import oxim.digital.reedly.domain.interactor.GetUserFeedsUseCase;
import oxim.digital.reedly.domain.interactor.IsUserSubscribedToFeedUseCase;
import oxim.digital.reedly.domain.interactor.UpdateFeedUseCase;
import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.ui.feed.mapper.FeedViewModeMapper;
import oxim.digital.reedly.ui.feed.model.FeedViewModel;
import rx.functions.Action1;

public final class UserSubscriptionsPresenter extends BasePresenter<UserSubscriptionsContract.View> implements UserSubscriptionsContract.Presenter {

    @Inject
    GetUserFeedsUseCase getUserFeedsUseCase;

    @Inject
    AddNewFeedUseCase addNewFeedUseCase;

    @Inject
    GetFeedItemsUseCase getFeedItemsUseCase;

    @Inject
    DeleteFeedUseCase deleteFeedUseCase;

    @Inject
    IsUserSubscribedToFeedUseCase isUserSubscribedToFeedUseCase;

    @Inject
    UpdateFeedUseCase updateFeedUseCase;

    @Inject
    FeedViewModeMapper feedViewModeMapper;

    private static final String TEST_FEED_URL = "https://xkcd.com/rss.xml";
    private static final String ANOTHER_TEST_FEED_URL = "https://feeds.feedburner.com/Android_Arsenal";

    public UserSubscriptionsPresenter(final UserSubscriptionsContract.View view) {
        super(view);
    }

    @Override
    public void activate() {
        super.activate();
        updateUserSubscriptions();
    }

    @Override
    public void showFeedItems(final FeedViewModel feedViewModel) {
        router.showFeedItemsScreen(feedViewModel.id);
    }

    @Override
    public void showAddNewFeed() {
        router.showAddNewFeedScreen();
    }

    @Override
    public void updateUserSubscriptions() {
        fetchUserFeeds();
    }

    @Override
    public void unsubscribeFromFeed(final FeedViewModel selectedFeedModel) {
        viewActionQueue.subscribeTo(deleteFeedUseCase.execute(selectedFeedModel.id)
                                                     .toSingleDefault(true)
                                                     .flatMap(b -> getUserFeedsUseCase.execute())
                                                     .map(feedViewModeMapper::mapFeedsToViewModels)
                                                     .map(feeds -> (Action1<UserSubscriptionsContract.View>) view -> view.showFeedSubscriptions(feeds)),
                                    Throwable::printStackTrace);
    }

    private void fetchUserFeeds() {
        viewActionQueue.subscribeTo(getUserFeedsUseCase.execute()
                                                       .doOnSuccess(this::updateUserFeeds)
                                                       .map(feedViewModeMapper::mapFeedsToViewModels)
                                                       .map(feeds -> (Action1<UserSubscriptionsContract.View>) view -> view.showFeedSubscriptions(feeds)),
                                    Throwable::printStackTrace);
    }

    private void updateUserFeeds(final List<Feed> feeds) {
        Stream.of(feeds)
              .map(feed -> updateFeedUseCase.execute(feed))
              .forEach(completable -> addSubscription(completable.subscribe(this::onFeedUpdateError, () -> {
              })));
    }

    private void onFeedUpdateError(final Throwable throwable) {
        logError(throwable);
    }

    private void fetchFeedItems() {
        viewActionQueue.subscribeTo(getFeedItemsUseCase.execute(1)
                                                       .map(feedItems -> (Action1<UserSubscriptionsContract.View>) view -> Log
                                                               .w("VIEW", "Got feed items -> " + String.valueOf(feedItems))),
                                    Throwable::printStackTrace);
    }

    private void deleteFeed() {
        viewActionQueue.subscribeTo(deleteFeedUseCase.execute(3),
                                    view -> Log.w("VIEW", "Feed deleted"),
                                    Throwable::printStackTrace);
    }

    private void checkUserSubscription(final String feedUrl) {
        viewActionQueue.subscribeTo(isUserSubscribedToFeedUseCase.execute(feedUrl)
                                                                 .map(isSubscribed -> (Action1<UserSubscriptionsContract.View>) view -> Log
                                                                         .w("VIEW", "Is user subscribed -> " + String.valueOf(isSubscribed))),
                                    Throwable::printStackTrace);
    }
}
