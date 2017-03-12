package oxim.digital.reedly.ui.feed.item;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import butterknife.OnClick;
import oxim.digital.reedly.R;
import oxim.digital.reedly.ui.feed.model.FeedItemViewModel;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

public final class FeedItemsAdapter extends RecyclerView.Adapter<FeedItemsAdapter.FeedItemViewHolder> {

    private static final long CLICK_THROTTLE_WINDOW_MILLIS = 300L;

    private List<FeedItemViewModel> feedItemViewModels = new LinkedList<>();

    private final Subject<FeedItemViewModel, FeedItemViewModel> onItemClickSubject = BehaviorSubject.create();
    private final Subject<FeedItemViewModel, FeedItemViewModel> onItemFavouriteClickSubject = BehaviorSubject.create();

    @Override
    public FeedItemViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                                            .inflate(R.layout.feed_item_list_item, parent, false);
        return new FeedItemViewHolder(itemView, onItemClickSubject, onItemFavouriteClickSubject);
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
        return onItemClickSubject.throttleFirst(CLICK_THROTTLE_WINDOW_MILLIS, TimeUnit.MILLISECONDS)
                                 .doOnNext(this::markFeedItemAsRead);
    }

    private void markFeedItemAsRead(final FeedItemViewModel readItem) {
        final int position = feedItemViewModels.indexOf(readItem);
        final FeedItemViewModel newItem = new FeedItemViewModel(false, readItem);
        feedItemViewModels.set(position, newItem);
        notifyItemChanged(position);
    }

    public Observable<FeedItemViewModel> onFavouriteItemClick() {
        return onItemFavouriteClickSubject.throttleFirst(CLICK_THROTTLE_WINDOW_MILLIS, TimeUnit.MILLISECONDS)
                                          .doOnNext(this::toggleFeedItemFavouriteStatus);
    }

    private void toggleFeedItemFavouriteStatus(final FeedItemViewModel changedItem) {
        final int position = feedItemViewModels.indexOf(changedItem);
        final FeedItemViewModel newItem = new FeedItemViewModel(changedItem, !changedItem.isFavourite);
        feedItemViewModels.set(position, newItem);
        notifyItemChanged(position);
    }

    static final class FeedItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.feed_item_title)
        TextView title;

        @Bind(R.id.feed_item_date)
        TextView publicationDate;

        @Bind(R.id.feed_item_favourite_indicator)
        View favouriteIndicator;

        @Bind(R.id.feed_item_new_indicator)
        View newIndicator;

        @BindColor(R.color.favouritePink)
        int favouriteIndicatorColor;

        @BindColor(R.color.unFavouriteGray)
        int unFavouriteIndicatorColor;

        private final Subject<FeedItemViewModel, FeedItemViewModel> clickSubject;
        private final Subject<FeedItemViewModel, FeedItemViewModel> favouriteClickSubject;

        private FeedItemViewModel feedItemViewModel;

        public FeedItemViewHolder(final View itemView, final Subject<FeedItemViewModel, FeedItemViewModel> clickSubject,
                                  final Subject<FeedItemViewModel, FeedItemViewModel> favouriteClickSubject) {
            super(itemView);
            this.clickSubject = clickSubject;
            this.favouriteClickSubject = favouriteClickSubject;
            ButterKnife.bind(this, itemView);
        }

        public void setItem(final FeedItemViewModel feedItemViewModel) {
            this.feedItemViewModel = feedItemViewModel;
            title.setText(feedItemViewModel.title);
            publicationDate.setText(feedItemViewModel.publicationDate);
            favouriteIndicator.setBackgroundColor(feedItemViewModel.isFavourite ? favouriteIndicatorColor : unFavouriteIndicatorColor);
            newIndicator.setVisibility(feedItemViewModel.isNew ? View.VISIBLE : View.GONE);
        }

        @OnClick(R.id.feed_item_content_container)
        void onFeedItemClick() {
            clickSubject.onNext(feedItemViewModel);
        }

        @OnClick(R.id.feed_item_favourite_indicator)
        void onFeedFavouriteItemClick() {
            favouriteClickSubject.onNext(feedItemViewModel);
        }
    }
}
