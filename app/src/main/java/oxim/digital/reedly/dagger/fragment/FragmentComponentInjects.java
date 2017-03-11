package oxim.digital.reedly.dagger.fragment;

import oxim.digital.reedly.ui.feed.item.FeedItemsFragment;
import oxim.digital.reedly.ui.feed.item.FeedItemsPresenter;
import oxim.digital.reedly.ui.feed.subscription.UserSubscriptionsFragment;
import oxim.digital.reedly.ui.feed.subscription.UserSubscriptionsPresenter;

public interface FragmentComponentInjects {

    void inject(UserSubscriptionsFragment userSubscriptionsFragment);
    void inject(UserSubscriptionsPresenter userSubscriptionsPresenter);

    void inject(FeedItemsFragment feedItemsFragment);
    void inject(FeedItemsPresenter feedItemsPresenter);
}
