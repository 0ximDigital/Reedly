package oxim.digital.reedly.ui.feed.model;

public final class FeedItemViewModel {

    public final String title;
    public final String link;
    public final String publicationDate;

    public FeedItemViewModel(final String title, final String link, final String publicationDate) {
        this.title = title;
        this.link = link;
        this.publicationDate = publicationDate;
    }

    @Override
    public String toString() {
        return "FeedItemViewModel{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", publicationDate='" + publicationDate + '\'' +
                '}';
    }
}
