package oxim.digital.reedly.data.feed.db.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyAction;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import oxim.digital.reedly.data.feed.db.definition.FeedDatabase;

@Table(database = FeedDatabase.class)
public final class FeedItemModel extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @ForeignKey(onDelete = ForeignKeyAction.CASCADE)
    FeedModel feed;

    @Column
    String title;

    @Column
    String link;

    @Column
    String description;

    @Column
    long publicationDate;

    public FeedItemModel() {
    }

    public FeedItemModel(final String title, final String link, final String description, final long publicationDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.publicationDate = publicationDate;
    }
}
