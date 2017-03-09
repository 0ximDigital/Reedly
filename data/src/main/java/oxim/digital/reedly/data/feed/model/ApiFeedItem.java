package oxim.digital.reedly.data.feed.model;

public final class ApiFeedItem {

    public final String title;
    public final String link;
    public final String description;
    public final long publicationDate;

    public ApiFeedItem(final String title, final String link, final String description, final long publicationDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.publicationDate = publicationDate;
    }
}
