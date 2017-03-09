package oxim.digital.reedly.data.feed.model;

import java.util.List;

public final class ApiFeed {

    public final String title;
    public final String imageUrl;
    public final String link;
    public final String description;

    public final List<ApiFeedItem> items;

    public ApiFeed(final String title, final String imageUrl, final String link, final String description, final List<ApiFeedItem> items) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.link = link;
        this.description = description;
        this.items = items;
    }
}
