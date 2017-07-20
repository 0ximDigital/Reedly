package oxim.digital.reedly.ui.feed.subscription;

import com.annimon.stream.Stream;

import java.util.List;

import javax.inject.Inject;

import oxim.digital.reedly.base.BasePresenter;
import oxim.digital.reedly.domain.interactor.feed.DeleteFeedUseCase;
import oxim.digital.reedly.domain.interactor.feed.update.DisableBackgroundFeedUpdatesUseCase;
import oxim.digital.reedly.domain.interactor.feed.update.EnableBackgroundFeedUpdatesUseCase;
import oxim.digital.reedly.domain.interactor.feed.GetUserFeedsUseCase;
import oxim.digital.reedly.domain.interactor.feed.update.ShouldUpdateFeedsInBackgroundUseCase;
import oxim.digital.reedly.domain.interactor.feed.update.UpdateFeedUseCase;
import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.ui.feed.mapper.FeedViewModeMapper;
import oxim.digital.reedly.ui.feed.model.FeedViewModel;
import rx.functions.Action1;

public final class UserSubscriptionsPresenter extends BasePresenter<UserSubscriptionsContract.View> implements UserSubscriptionsContract.Presenter {

    @Inject
    GetUserFeedsUseCase getUserFeedsUseCase;

    @Inject
    DeleteFeedUseCase deleteFeedUseCase;

    @Inject
    UpdateFeedUseCase updateFeedUseCase;

    @Inject
    ShouldUpdateFeedsInBackgroundUseCase shouldUpdateFeedsInBackgroundUseCase;

    @Inject
    EnableBackgroundFeedUpdatesUseCase enableBackgroundFeedUpdatesUseCase;

    @Inject
    DisableBackgroundFeedUpdatesUseCase disableBackgroundFeedUpdatesUseCase;

    @Inject
    FeedViewModeMapper feedViewModeMapper;

    public UserSubscriptionsPresenter(final UserSubscriptionsContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();
        checkBackgroundFeedUpdates();
    }

    @Override
    public void activate() {
        super.activate();
        updateUserSubscriptions();
    }

    @Override
    public void showFeedItems(final FeedViewModel feedViewModel) {
        router.showFeedItemsScreen(feedViewModel.id, feedViewModel.title);
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

    @Override
    public void showFavouriteFeedItems() {
        router.showFavouriteFeedItemsScreen();
    }

    @Override
    public void enableBackgroundFeedUpdates() {
        viewActionQueue.subscribeTo(enableBackgroundFeedUpdatesUseCase.execute(),
                                    view -> view.setIsBackgroundFeedUpdateEnabled(true),
                                    Throwable::printStackTrace);
    }

    @Override
    public void disableBackgroundFeedUpdates() {
        viewActionQueue.subscribeTo(disableBackgroundFeedUpdatesUseCase.execute(),
                                    view -> view.setIsBackgroundFeedUpdateEnabled(false),
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
              .forEach(completable -> addSubscription(completable.subscribe(() -> { }, this::onFeedUpdateError)));
    }

    private void onFeedUpdateError(final Throwable throwable) {
        logError(throwable);
    }

    private void checkBackgroundFeedUpdates() {
        viewActionQueue.subscribeTo(shouldUpdateFeedsInBackgroundUseCase.execute()
                                                                        .map(shouldUpdate -> (Action1<UserSubscriptionsContract.View>) view -> view
                                                                                .setIsBackgroundFeedUpdateEnabled(shouldUpdate)),
                                    Throwable::printStackTrace);
    }
}
