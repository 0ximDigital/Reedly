package oxim.digital.reedly.ui.feed.model;

public final class FeedItemViewModel {

    public final int id;
    public final String title;
    public final String link;
    public final String publicationDate;

    public final boolean isNew;
    public final boolean isFavourite;

    public FeedItemViewModel(final int id, final String title, final String link, final String publicationDate, final boolean isNew, final boolean isFavourite) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.publicationDate = publicationDate;
        this.isNew = isNew;
        this.isFavourite = isFavourite;
    }

    public FeedItemViewModel(final boolean isNew, final FeedItemViewModel model) {
        this(model.id, model.title, model.link, model.publicationDate, isNew, model.isFavourite);
    }

    public FeedItemViewModel(final FeedItemViewModel model, final boolean isFavourite) {
        this(model.id, model.title, model.link, model.publicationDate, model.isNew, isFavourite);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final FeedItemViewModel that = (FeedItemViewModel) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "FeedItemViewModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", publicationDate='" + publicationDate + '\'' +
                ", isNew=" + isNew +
                ", isFavourite=" + isFavourite +
                '}';
    }
}
