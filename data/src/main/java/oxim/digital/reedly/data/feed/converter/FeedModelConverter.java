package oxim.digital.reedly.data.feed.converter;

import oxim.digital.reedly.data.feed.model.ApiFeed;
import oxim.digital.reedly.data.feed.model.ApiFeedItem;
import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.model.FeedItem;

public interface FeedModelConverter {

    Feed apiToDomain(ApiFeed apiFeed);

    FeedItem apiToDomain(ApiFeedItem apiFeedItem);
}
