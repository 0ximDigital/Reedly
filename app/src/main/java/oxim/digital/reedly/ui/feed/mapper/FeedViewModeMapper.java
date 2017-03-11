package oxim.digital.reedly.ui.feed.mapper;

import java.util.List;

import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.model.FeedItem;
import oxim.digital.reedly.ui.feed.model.FeedItemViewModel;
import oxim.digital.reedly.ui.feed.model.FeedViewModel;

public interface FeedViewModeMapper {

    FeedViewModel mapFeedToViewModel(Feed feed);

    List<FeedViewModel> mapFeedsToViewModels(List<Feed> feeds);

    FeedItemViewModel mapFeedItemToViewModel(FeedItem feedItem);

    List<FeedItemViewModel> mapFeedItemsToViewModels(List<FeedItem> feedItems);
}
