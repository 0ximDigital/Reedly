package oxim.digital.reedly.dagger.fragment;

import oxim.digital.reedly.feed.subscription.UserSubscriptionsFragment;
import oxim.digital.reedly.feed.subscription.UserSubscriptionsPresenter;

public interface FragmentComponentInjects {

    void inject(UserSubscriptionsFragment userSubscriptionsFragment);
    void inject(UserSubscriptionsPresenter userSubscriptionsPresenter);


}
