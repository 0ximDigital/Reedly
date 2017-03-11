package oxim.digital.reedly.feed.model;

public final class FeedViewModel {

    public final String title;
    public final String imageUrl;
    public final String link;
    public final String description;

    public FeedViewModel(final String title, final String imageUrl, final String link, final String description) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.link = link;
        this.description = description;
    }

    @Override
    public String toString() {
        return "FeedViewModel{" +
                "title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
