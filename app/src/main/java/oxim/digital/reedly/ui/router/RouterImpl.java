package oxim.digital.reedly.ui.router;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import oxim.digital.reedly.R;
import oxim.digital.reedly.dagger.activity.ActivityScope;
import oxim.digital.reedly.ui.feed.article.FeedItemContentFragment;
import oxim.digital.reedly.ui.feed.create.NewFeedSubscriptionFragment;
import oxim.digital.reedly.ui.feed.item.FeedItemsFragment;
import oxim.digital.reedly.ui.feed.subscription.UserSubscriptionsFragment;
import rx.functions.Action1;
import rx.functions.Func0;

@ActivityScope
public final class RouterImpl implements Router {

    private static final int LAST_FRAGMENT = 0;

    private final Activity activity;
    private final FragmentManager fragmentManager;

    public RouterImpl(final Activity activity, final FragmentManager fragmentManager) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void closeScreen() {
        activity.finish();
    }

    @Override
    public void goBack() {
        if (fragmentManager.getBackStackEntryCount() == LAST_FRAGMENT) {
            closeScreen();
        } else {
            fragmentManager.popBackStack();
        }
    }

    @Override
    public void showUserSubscriptionsScreen() {
        final UserSubscriptionsFragment fragment = (UserSubscriptionsFragment) fragmentManager.findFragmentByTag(UserSubscriptionsFragment.TAG);
        if (fragment != null) {
            fragment.refreshUserSubscriptions();
        }
    }

    @Override
    public void showFavouriteFeedItemsScreen() {
        advanceToFragment(FeedItemsFragment.TAG, UserSubscriptionsFragment.TAG,
                          FeedItemsFragment::newInstance,
                          FeedItemsFragment::setFavouriteItems);
    }

    @Override
    public void showFeedItemsScreen(final int feedId, final String feedTitle) {
        advanceToFragment(FeedItemsFragment.TAG, UserSubscriptionsFragment.TAG,
                          () -> FeedItemsFragment.newInstance(feedId, feedTitle),
                          feedItemsFragment -> feedItemsFragment.updateFeed(feedId, feedTitle));
    }

    @Override
    public void showFeedItemContentScreen(final String contentUrl) {
        advanceToFragment(FeedItemContentFragment.TAG, FeedItemsFragment.TAG,
                          () -> FeedItemContentFragment.newInstance(contentUrl),
                          feedItemContentFragment -> feedItemContentFragment.setContentUrl(contentUrl));
    }

    private <T extends Fragment> void advanceToFragment(final String destinationFragmentTag, final String sourceFragmentTag, final Func0<T> destinationFragmentFactory,
                                                        final Action1<T> destinationFragmentExistsAction) {
        T destinationFragment = (T) fragmentManager.findFragmentByTag(destinationFragmentTag);
        final Fragment sourceFragment = fragmentManager.findFragmentByTag(sourceFragmentTag);

        if (destinationFragment == null) {
            destinationFragment = destinationFragmentFactory.call();
            fragmentManager.beginTransaction()
                           .setCustomAnimations(R.anim.fragment_left_enter, R.anim.fragment_right_exit, R.anim.fragment_right_enter, R.anim.fragment_left_exit)
                           .addToBackStack(null)
                           .hide(sourceFragment)
                           .add(R.id.activity_container, destinationFragment, FeedItemsFragment.TAG)
                           .commit();
        } else {
            destinationFragmentExistsAction.call(destinationFragment);
            fragmentManager.beginTransaction()
                           .addToBackStack(null)
                           .hide(sourceFragment)
                           .show(destinationFragment)
                           .commit();
        }
    }

    @Override
    public void showAddNewFeedScreen() {
        final Fragment fragment = NewFeedSubscriptionFragment.newInstance();
        fragmentManager.beginTransaction()
//                       .addToBackStack(null)
                       .add(R.id.activity_container, fragment, NewFeedSubscriptionFragment.TAG)
                       .commit();
    }

    @Override
    public void hideAddNewFeedScreen() {
        final Fragment fragment = fragmentManager.findFragmentByTag(NewFeedSubscriptionFragment.TAG);
        if (fragment != null) {
            fragmentManager.beginTransaction()
                           .remove(fragment)
                           .commit();
        }
    }
}
