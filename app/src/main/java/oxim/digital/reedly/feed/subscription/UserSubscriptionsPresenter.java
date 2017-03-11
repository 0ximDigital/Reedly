package oxim.digital.reedly.feed.subscription;

import android.util.Log;

import javax.inject.Inject;

import oxim.digital.reedly.base.BasePresenter;
import oxim.digital.reedly.domain.interactor.AddNewFeedUseCase;
import oxim.digital.reedly.domain.interactor.DeleteFeedUseCase;
import oxim.digital.reedly.domain.interactor.GetFeedItemsUseCase;
import oxim.digital.reedly.domain.interactor.GetUserFeedsUseCase;
import oxim.digital.reedly.domain.interactor.IsUserSubscribedToFeedUseCase;
import oxim.digital.reedly.domain.model.Feed;
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

    private static final String TEST_FEED_URL = "https://xkcd.com/rss.xml";
    private static final String ANOTHER_TEST_FEED_URL = "https://feeds.feedburner.com/Android_Arsenal";

    public UserSubscriptionsPresenter(final UserSubscriptionsContract.View view) {
        super(view);
    }

    @Override
    public void fetchUserSubscriptions() {
        checkUserSubscription(TEST_FEED_URL);
    }

    private void fetchUserFeeds() {
        viewActionQueue.subscribeTo(getUserFeedsUseCase.execute()
                                                       .map(feeds -> (Action1<UserSubscriptionsContract.View>) view -> Log.w("VIEW", "Got feeds -> " + String.valueOf(feeds))),
                                    Throwable::printStackTrace);
    }

    private void addNewFeed(final String feedUrl) {
        viewActionQueue.subscribeTo(addNewFeedUseCase.execute(feedUrl),
                                    view -> Log.w("VIEW", "Add new feed"),
                                    Throwable::printStackTrace);
    }

    private void fetchFeedItems() {
        viewActionQueue.subscribeTo(getFeedItemsUseCase.execute(1)
                                                       .map(feedItems -> (Action1<UserSubscriptionsContract.View>) view -> Log.w("VIEW", "Got feed items -> " + String.valueOf(feedItems))),
                                    Throwable::printStackTrace);
    }

    private void deleteFeed() {
        viewActionQueue.subscribeTo(deleteFeedUseCase.execute(new Feed(3, "", "", "", "", "")),
                                    view -> Log.w("VIEW", "Feed deleted"),
                                    Throwable::printStackTrace);
    }

    private void checkUserSubscription(final String feedUrl) {
        viewActionQueue.subscribeTo(isUserSubscribedToFeedUseCase.execute(feedUrl)
                                                                 .map(isSubscribed -> (Action1<UserSubscriptionsContract.View>) view -> Log.w("VIEW", "Is user subscribed -> " + String.valueOf(isSubscribed))),
                                    Throwable::printStackTrace);
    }
}
