package oxim.digital.reedly.feed.mapper;

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
        return new FeedViewModel(feed.title, feed.imageUrl, feed.pageLink, feed.description);
    }

    @Override
    public FeedItemViewModel mapFeedItemToViewModel(final FeedItem feedItem) {
        return new FeedItemViewModel(feedItem.title, feedItem.link, dateUtils.format(feedItem.publicationDate));
    }
}
