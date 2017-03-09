package oxim.digital.reedly.data.feed.converter;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import oxim.digital.reedly.data.feed.service.model.ApiFeed;
import oxim.digital.reedly.data.feed.service.model.ApiFeedItem;
import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.model.FeedItem;

public final class FeedModelConverterImpl implements FeedModelConverter {

    @Override
    public Feed apiToDomain(final ApiFeed apiFeed) {
        return new Feed(apiFeed.title, apiFeed.imageUrl, apiFeed.link, apiFeed.description, apiToDomain(apiFeed.items));
    }

    @Override
    public FeedItem apiToDomain(final ApiFeedItem apiFeedItem) {
        return new FeedItem(apiFeedItem.title, apiFeedItem.link, apiFeedItem.description, apiFeedItem.publicationDate);
    }

    private List<FeedItem> apiToDomain(final List<ApiFeedItem> apiFeedItems) {
        return Stream.of(apiFeedItems)
                     .map(this::apiToDomain)
                     .collect(Collectors.toList());
    }
}
