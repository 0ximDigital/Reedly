package oxim.digital.reedly.domain.model;

public final class FeedItem {

    public final int id;
    public final int feedId;
    public final String title;
    public final String link;
    public final long publicationDate;

    public FeedItem(final int id, final int feedId, final String title, final String link, final long publicationDate) {
        this.id = id;
        this.feedId = feedId;
        this.title = title;
        this.link = link;
        this.publicationDate = publicationDate;
    }

    @Override
    public String toString() {
        return "FeedItem{" +
                "id=" + id +
                ", feedId=" + feedId +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", publicationDate=" + publicationDate +
                '}';
    }
}
