package oxim.digital.reedly.feed.model;

public final class FeedItemViewModel {

    public final String title;
    public final String link;
    public final String description;
    public final String publicationDate;

    public FeedItemViewModel(final String title, final String link, final String description, final String publicationDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.publicationDate = publicationDate;
    }

    @Override
    public String toString() {
        return "FeedItemViewModel{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", publicationDate='" + publicationDate + '\'' +
                '}';
    }
}
