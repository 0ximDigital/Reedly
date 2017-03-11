package oxim.digital.reedly.data.feed.service.model;

public final class ApiFeedItem {

    public final String title;
    public final String link;
    public final long publicationDate;

    public ApiFeedItem(final String title, final String link, final long publicationDate) {
        this.title = title;
        this.link = link;
        this.publicationDate = publicationDate;
    }
}
