package oxim.digital.reedly.data.feed.db.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

import oxim.digital.reedly.data.feed.db.definition.FeedDatabase;

@Table(database = FeedDatabase.class)
public final class FeedModel extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    String title;

    @Column
    String imageUrl;

    @Column
    String link;

    @Column
    String description;

    List<FeedItemModel> feedItemModels;

    public FeedModel() {
    }

    public FeedModel(final String title, final String imageUrl, final String link, final String description) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.link = link;
        this.description = description;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "feedItemModels")
    public List<FeedItemModel> getFeedItemModels() {
        if (feedItemModels == null) {
            feedItemModels = SQLite.select()
                                   .from(FeedItemModel.class)
                                   .where(FeedItemModel_Table.feed_id.eq(id))
                                   .queryList();
        }
        return feedItemModels;
    }

    public void setFeedItemModels(final List<FeedItemModel> feedItemModels) {
        this.feedItemModels = feedItemModels;
    }
}
