package oxim.digital.reedly.domain.model;

import java.util.List;

public final class Feed {

    public final String title;
    public final String imageUrl;
    public final String link;
    public final String description;

    public final List<FeedItem> items;

    public Feed(final String title, final String imageUrl, final String link, final String description, final List<FeedItem> items) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.link = link;
        this.description = description;
        this.items = items;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", items=" + items +
                '}';
    }
}
