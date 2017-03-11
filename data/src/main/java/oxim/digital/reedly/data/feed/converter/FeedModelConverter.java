package oxim.digital.reedly.data.feed.converter;

import oxim.digital.reedly.data.feed.db.model.FeedItemModel;
import oxim.digital.reedly.data.feed.db.model.FeedModel;
import oxim.digital.reedly.data.feed.service.model.ApiFeed;
import oxim.digital.reedly.data.feed.service.model.ApiFeedItem;
import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.model.FeedItem;

public interface FeedModelConverter {

    Feed modelToDomain(FeedModel feedModel);

    FeedItem modelToDomain(FeedItemModel feedItemModel);

    FeedModel apiToModel(ApiFeed apiFeed);

    FeedItemModel apiToModel(ApiFeedItem apiFeedItem, int feedId);
}
