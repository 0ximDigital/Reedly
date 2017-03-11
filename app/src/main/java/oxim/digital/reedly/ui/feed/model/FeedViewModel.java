package oxim.digital.reedly.ui.feed.model;

public final class FeedViewModel {

    public static final FeedViewModel EMPTY = new FeedViewModel(0, "", "", "", "");

    public final int id;
    public final String title;
    public final String imageUrl;
    public final String link;
    public final String description;

    public FeedViewModel(final int id, final String title, final String imageUrl, final String link, final String description) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.link = link;
        this.description = description;
    }

    @Override
    public String toString() {
        return "FeedViewModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
