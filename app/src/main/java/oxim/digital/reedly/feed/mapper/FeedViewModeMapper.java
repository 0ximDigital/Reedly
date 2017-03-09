package oxim.digital.reedly.feed.mapper;

import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.model.FeedItem;
import oxim.digital.reedly.feed.model.FeedItemViewModel;
import oxim.digital.reedly.feed.model.FeedViewModel;

public interface FeedViewModeMapper {

    FeedViewModel mapFeedToViewModel(Feed feed);

    FeedItemViewModel mapFeedItemToViewModel(FeedItem feedItem);
}
