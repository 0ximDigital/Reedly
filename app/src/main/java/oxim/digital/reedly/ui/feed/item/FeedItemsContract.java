package oxim.digital.reedly.ui.feed.item;

import java.util.List;

import oxim.digital.reedly.base.BaseView;
import oxim.digital.reedly.base.ScopedPresenter;
import oxim.digital.reedly.ui.feed.model.FeedItemViewModel;

public interface FeedItemsContract {

    interface View extends BaseView {

        void showFeedItems(List<FeedItemViewModel> feedItems);
    }

    interface Presenter extends ScopedPresenter {

        void fetchFeedItems(int feedId);

        void fetchFavouriteFeedItems();

        void showItemContent(FeedItemViewModel feedItemViewModel);

        void markFeedItemAsRead(int feedItemId);

        void toggleFeedItemFavourite(FeedItemViewModel feedItemViewModel);
    }
}
