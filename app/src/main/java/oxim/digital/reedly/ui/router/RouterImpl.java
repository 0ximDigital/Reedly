package oxim.digital.reedly.ui.router;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import oxim.digital.reedly.R;
import oxim.digital.reedly.dagger.activity.ActivityScope;
import oxim.digital.reedly.ui.feed.item.FeedItemsFragment;
import oxim.digital.reedly.ui.feed.subscription.UserSubscriptionsFragment;

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
    public void showFeedItemsScreen(final int feedId) {
        FeedItemsFragment feedItemsFragment = (FeedItemsFragment) fragmentManager.findFragmentByTag(FeedItemsFragment.TAG);
        final Fragment feedSubscriptionsFragment = fragmentManager.findFragmentByTag(UserSubscriptionsFragment.TAG);

        if (feedItemsFragment == null) {
            feedItemsFragment = FeedItemsFragment.newInstance(feedId);
            fragmentManager.beginTransaction()
                           .setCustomAnimations(R.anim.fragment_fade_in, R.anim.fragment_fade_out, R.anim.fragment_fade_in, R.anim.fragment_fade_out)
                           .addToBackStack(null)
                           .hide(feedSubscriptionsFragment)
                           .add(R.id.activity_container, feedItemsFragment, FeedItemsFragment.TAG)
                           .commit();
        } else if (feedItemsFragment.isHidden()) {
            feedItemsFragment.updateFeedId(feedId);
            fragmentManager.beginTransaction()
                           .addToBackStack(null)
                           .hide(feedSubscriptionsFragment)
                           .show(feedItemsFragment)
                           .commit();
        }
    }

    @Override
    public void showFeedItemContentScreen(final String contentUrl) {

    }

    @Override
    public void showAddNewFeedScreen() {

    }
}
