package oxim.digital.reedly.dagger.fragment.module;

import dagger.Module;
import dagger.Provides;
import oxim.digital.reedly.dagger.fragment.DaggerFragment;
import oxim.digital.reedly.dagger.fragment.FragmentComponent;
import oxim.digital.reedly.dagger.fragment.FragmentScope;
import oxim.digital.reedly.ui.feed.item.FeedItemsContract;
import oxim.digital.reedly.ui.feed.item.FeedItemsPresenter;
import oxim.digital.reedly.ui.feed.subscription.UserSubscriptionsContract;
import oxim.digital.reedly.ui.feed.subscription.UserSubscriptionsPresenter;

@Module
public final class FragmentPresenterModule {

    private final DaggerFragment daggerFragment;

    public FragmentPresenterModule(final DaggerFragment daggerFragment) {
        this.daggerFragment = daggerFragment;
    }

    private FragmentComponent getFragmentComponent() {
        return daggerFragment.getFragmentComponent();
    }

    @Provides
    @FragmentScope
    public UserSubscriptionsContract.Presenter provideUserSubscriptionsPresenter() {
        final UserSubscriptionsPresenter userSubscriptionsPresenter = new UserSubscriptionsPresenter((UserSubscriptionsContract.View) daggerFragment);
        getFragmentComponent().inject(userSubscriptionsPresenter);
        return userSubscriptionsPresenter;
    }

    @Provides
    @FragmentScope
    public FeedItemsContract.Presenter provideFeedItemsPresenter() {
        final FeedItemsPresenter feedItemsPresenter = new FeedItemsPresenter((FeedItemsContract.View) daggerFragment);
        getFragmentComponent().inject(feedItemsPresenter);
        return feedItemsPresenter;
    }
}
