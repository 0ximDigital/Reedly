package oxim.digital.reedly.feed.subscription;

import java.util.List;

import oxim.digital.reedly.base.BaseView;
import oxim.digital.reedly.base.ScopedPresenter;

public interface UserSubscriptionsContract {

    interface View extends BaseView {

        void showFeedSubscriptions(List<String> subscriptions);
    }

    interface Presenter extends ScopedPresenter {

        void fetchUserSubscriptions();

    }
}
