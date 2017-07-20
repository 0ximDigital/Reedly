package oxim.digital.reedly.ui.feed.mapper;

import java.util.List;

import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.model.Article;
import oxim.digital.reedly.ui.feed.model.FeedItemViewModel;
import oxim.digital.reedly.ui.feed.model.FeedViewModel;

public interface FeedViewModeMapper {

    FeedViewModel mapFeedToViewModel(Feed feed);

    List<FeedViewModel> mapFeedsToViewModels(List<Feed> feeds);

    FeedItemViewModel mapFeedItemToViewModel(Article article);

    List<FeedItemViewModel> mapFeedItemsToViewModels(List<Article> articles);
}
