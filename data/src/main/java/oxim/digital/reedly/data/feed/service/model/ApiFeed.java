package oxim.digital.reedly.data.feed.service.model;

import java.util.List;

public final class ApiFeed {

    public final String title;
    public final String imageUrl;
    public final String pageLink;
    public final String description;
    public final String url;

    public final List<ApiFeedItem> items;

    public ApiFeed(final String title, final String imageUrl, final String pageLink, final String description, final String url, final List<ApiFeedItem> items) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.pageLink = pageLink;
        this.description = description;
        this.url = url;
        this.items = items;
    }
}
