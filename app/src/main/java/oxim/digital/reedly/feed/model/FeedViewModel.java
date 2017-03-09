package oxim.digital.reedly.feed.model;

import java.util.List;

public final class FeedViewModel {

    public final String title;
    public final String imageUrl;
    public final String link;
    public final String description;

    public final List<FeedItemViewModel> items;

    public FeedViewModel(final String title, final String imageUrl, final String link, final String description, final List<FeedItemViewModel> items) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.link = link;
        this.description = description;
        this.items = items;
    }

    @Override
    public String toString() {
        return "FeedViewModel{" +
                "title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", items=" + items +
                '}';
    }
}
