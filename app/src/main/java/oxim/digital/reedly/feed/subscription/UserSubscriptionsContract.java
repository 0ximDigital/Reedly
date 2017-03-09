package oxim.digital.reedly.feed.subscription;

import java.util.List;

import oxim.digital.reedly.base.BaseView;
import oxim.digital.reedly.base.ScopedPresenter;
import oxim.digital.reedly.feed.model.FeedViewModel;

public interface UserSubscriptionsContract {

    interface View extends BaseView {

        void showFeedSubscriptions(List<FeedViewModel> feedSubscriptions);
    }

    interface Presenter extends ScopedPresenter {

        void fetchUserSubscriptions();

    }
}
