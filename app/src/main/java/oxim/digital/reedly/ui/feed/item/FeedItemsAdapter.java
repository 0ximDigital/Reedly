package oxim.digital.reedly.ui.feed.item;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import oxim.digital.reedly.R;
import oxim.digital.reedly.ui.feed.model.FeedItemViewModel;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

public final class FeedItemsAdapter extends RecyclerView.Adapter<FeedItemsAdapter.FeedItemViewHolder> {

    private static final long CLICK_THROTTLE_WINDOW_MILLIS = 300L;

    private List<FeedItemViewModel> feedItemViewModels = new ArrayList<>();

    private final Subject<FeedItemViewModel, FeedItemViewModel> onItemClickSubject = BehaviorSubject.create();

    @Override
    public FeedItemViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                                            .inflate(R.layout.feed_item_list_item, parent, false);
        return new FeedItemViewHolder(itemView, onItemClickSubject);
    }

    @Override
    public void onBindViewHolder(final FeedItemViewHolder holder, final int position) {
        holder.setItem(feedItemViewModels.get(position));
    }

    @Override
    public int getItemCount() {
        return feedItemViewModels.size();
    }

    public void onFeedsUpdate(final List<FeedItemViewModel> feedItemViewModels) {
        // TODO - diff utils
        this.feedItemViewModels = feedItemViewModels;
        notifyDataSetChanged();
    }

    public Observable<FeedItemViewModel> onItemClick() {
        return onItemClickSubject.throttleFirst(CLICK_THROTTLE_WINDOW_MILLIS, TimeUnit.MILLISECONDS);
    }

    static final class FeedItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.feed_item_title)
        TextView feedItemTitle;

        @Bind(R.id.feed_item_date)
        TextView feedItemPublicationDate;

        private final Subject<FeedItemViewModel, FeedItemViewModel> clickSubject;

        private FeedItemViewModel feedItemViewModel;

        public FeedItemViewHolder(final View itemView, final Subject<FeedItemViewModel, FeedItemViewModel> clickSubject) {
            super(itemView);
            this.clickSubject = clickSubject;
            ButterKnife.bind(this, itemView);
        }

        public void setItem(final FeedItemViewModel feedItemViewModel) {
            this.feedItemViewModel = feedItemViewModel;
            feedItemTitle.setText(feedItemViewModel.title);
            feedItemPublicationDate.setText(feedItemViewModel.publicationDate);
        }

        @OnClick(R.id.feed_item_content_container)
        void onFeedItemClick() {
            clickSubject.onNext(feedItemViewModel);
        }
    }
}
