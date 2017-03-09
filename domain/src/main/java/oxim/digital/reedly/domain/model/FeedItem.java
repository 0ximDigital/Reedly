package oxim.digital.reedly.domain.model;

public final class FeedItem {

    public final String title;
    public final String link;
    public final String description;
    public final long publicationDate;

    public FeedItem(final String title, final String link, final String description, final long publicationDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.publicationDate = publicationDate;
    }

    @Override
    public String toString() {
        return "FeedItem{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", publicationDate=" + publicationDate +
                '}';
    }
}
