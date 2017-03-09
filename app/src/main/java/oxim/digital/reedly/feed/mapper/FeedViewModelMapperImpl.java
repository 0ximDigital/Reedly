package oxim.digital.reedly.feed.mapper;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.model.FeedItem;
import oxim.digital.reedly.feed.model.FeedItemViewModel;
import oxim.digital.reedly.feed.model.FeedViewModel;
import oxim.digital.reedly.util.DateUtils;

public final class FeedViewModelMapperImpl implements FeedViewModeMapper {

    private final DateUtils dateUtils;

    public FeedViewModelMapperImpl(final DateUtils dateUtils) {
        this.dateUtils = dateUtils;
    }

    @Override
    public FeedViewModel mapFeedToViewModel(final Feed feed) {
        return new FeedViewModel(feed.title, feed.imageUrl, feed.link, feed.description, mapFeedItemsToViewModels(feed.items));
    }

    @Override
    public FeedItemViewModel mapFeedItemToViewModel(final FeedItem feedItem) {
        return new FeedItemViewModel(feedItem.title, feedItem.link, feedItem.description, dateUtils.format(feedItem.publicationDate));
    }

    private List<FeedItemViewModel> mapFeedItemsToViewModels(final List<FeedItem> feedItems) {
        return Stream.of(feedItems)
                     .map(this::mapFeedItemToViewModel)
                     .collect(Collectors.toList());
    }
}
