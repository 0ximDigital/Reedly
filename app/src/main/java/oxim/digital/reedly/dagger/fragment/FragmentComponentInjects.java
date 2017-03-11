package oxim.digital.reedly.dagger.fragment;

import oxim.digital.reedly.ui.feed.article.FeedItemContentFragment;
import oxim.digital.reedly.ui.feed.article.FeedItemContentPresenter;
import oxim.digital.reedly.ui.feed.create.NewFeedSubscriptionFragment;
import oxim.digital.reedly.ui.feed.create.NewFeedSubscriptionPresenter;
import oxim.digital.reedly.ui.feed.item.FeedItemsFragment;
import oxim.digital.reedly.ui.feed.item.FeedItemsPresenter;
import oxim.digital.reedly.ui.feed.subscription.UserSubscriptionsFragment;
import oxim.digital.reedly.ui.feed.subscription.UserSubscriptionsPresenter;

public interface FragmentComponentInjects {

    void inject(UserSubscriptionsFragment userSubscriptionsFragment);
    void inject(UserSubscriptionsPresenter userSubscriptionsPresenter);

    void inject(FeedItemsFragment feedItemsFragment);
    void inject(FeedItemsPresenter feedItemsPresenter);

    void inject(FeedItemContentFragment feedItemContentFragment);
    void inject(FeedItemContentPresenter feedItemContentPresenter);

    void inject(NewFeedSubscriptionFragment newFeedSubscriptionFragment);
    void inject(NewFeedSubscriptionPresenter newFeedSubscriptionPresenter);
}
