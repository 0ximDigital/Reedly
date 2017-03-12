package oxim.digital.reedly.data.feed.converter;

import oxim.digital.reedly.data.feed.db.model.FeedItemModel;
import oxim.digital.reedly.data.feed.db.model.FeedModel;
import oxim.digital.reedly.data.feed.service.model.ApiFeed;
import oxim.digital.reedly.data.feed.service.model.ApiFeedItem;
import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.model.FeedItem;

public final class FeedModelConverterImpl implements FeedModelConverter {

    @Override
    public Feed modelToDomain(final FeedModel model) {
        return new Feed(model.getId(), model.getTitle(), model.getImageUrl(), model.getPageLink(), model.getDescription(), model.getUrl());
    }

    @Override
    public FeedItem modelToDomain(final FeedItemModel model) {
        return new FeedItem(model.getId(), model.getFeedId(), model.getTitle(), model.getLink(), model.getPublicationDate(), model.isNew(), model.isFavourite());
    }

    @Override
    public FeedModel apiToModel(final ApiFeed apiFeed) {
        return new FeedModel(apiFeed.title, apiFeed.imageUrl, apiFeed.pageLink, apiFeed.description, apiFeed.url);
    }

    @Override
    public FeedItemModel apiToModel(final ApiFeedItem apiFeedItem, final int feedId) {
        return new FeedItemModel(feedId, apiFeedItem.title, apiFeedItem.link, apiFeedItem.publicationDate);
    }
}
