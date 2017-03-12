package oxim.digital.reedly.ui.feed.item;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import oxim.digital.reedly.R;
import oxim.digital.reedly.base.BaseFragment;
import oxim.digital.reedly.base.ScopedPresenter;
import oxim.digital.reedly.dagger.fragment.FragmentComponent;
import oxim.digital.reedly.ui.feed.model.FeedItemViewModel;

public final class FeedItemsFragment extends BaseFragment implements FeedItemsContract.View {

    public static final String TAG = FeedItemsFragment.class.getSimpleName();

    private static final String KEY_ITEMS_FEED_ID = "key_items_feed_id";
    private static final String KEY_FEED_TITLE = "key_feed_title";
    private static final String KEY_FAVOURITE_ITEMS = "key_favourite_items";

    @Inject
    FeedItemsContract.Presenter presenter;

    @Inject
    Resources resources;

    @Bind(R.id.feedTitle)
    TextView feedTitle;

    @Bind(R.id.feed_items_recycler_view)
    RecyclerView feedItemsRecyclerView;

    private RecyclerView.LayoutManager feedItemsLayoutManager;
    private FeedItemsAdapter feedItemsAdapter;

    private boolean areFavouriteItems;

    public static FeedItemsFragment newInstance(final int feedId, final String feedTitle) {
        final FeedItemsFragment fragment = new FeedItemsFragment();
        final Bundle arguments = new Bundle();
        arguments.putInt(KEY_ITEMS_FEED_ID, feedId);
        arguments.putString(KEY_FEED_TITLE, feedTitle);
        fragment.setArguments(arguments);
        return fragment;
    }

    public static FeedItemsFragment newInstance() {
        final FeedItemsFragment fragment = new FeedItemsFragment();
        final Bundle arguments = new Bundle();
        arguments.putBoolean(KEY_FAVOURITE_ITEMS, true);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_feed_items, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        extractArguments(getArguments());
        initializeRecyclerView();
    }

    private void extractArguments(final Bundle arguments) {
        if (arguments.getBoolean(KEY_FAVOURITE_ITEMS, false)) {
            setFavouriteItems();
        } else {
            updateFeed(arguments.getInt(KEY_ITEMS_FEED_ID), arguments.getString(KEY_FEED_TITLE, resources.getString(R.string.app_name)));
        }
    }

    public void updateFeed(final int feedId, final String feedTitle) {
        areFavouriteItems = false;
        updateTitle(feedTitle);
        presenter.fetchFeedItems(feedId);
    }

    private void updateTitle(final String feedTitle) {
        this.feedTitle.setText(feedTitle);
    }

    public void setFavouriteItems() {
        areFavouriteItems = true;
        presenter.fetchFavouriteFeedItems();
    }

    @Override
    public void showFeedItems(final List<FeedItemViewModel> feedItems) {
        feedItemsAdapter.onFeedsUpdate(feedItems);
    }

    private void initializeRecyclerView() {
        if (feedItemsRecyclerView.getAdapter() == null) {
            feedItemsAdapter = new FeedItemsAdapter();
            feedItemsRecyclerView.setAdapter(feedItemsAdapter);
        } else {
            feedItemsAdapter = (FeedItemsAdapter) feedItemsRecyclerView.getAdapter();
        }
        feedItemsAdapter.onItemClick()
                        .subscribe(this::onFeedItemSelected);
        feedItemsAdapter.onFavouriteItemClick()
                        .subscribe(this::onFeedItemFavouriteChanged);
        feedItemsLayoutManager = new LinearLayoutManager(null);
        feedItemsRecyclerView.setLayoutManager(feedItemsLayoutManager);
    }

    private void onFeedItemSelected(final FeedItemViewModel feedItemViewModel) {
        presenter.markFeedItemAsRead(feedItemViewModel.id);
        presenter.showItemContent(feedItemViewModel);
    }

    private void onFeedItemFavouriteChanged(final FeedItemViewModel feedItemViewModel) {
        presenter.toggleFeedItemFavourite(feedItemViewModel);
    }

    @Override
    public ScopedPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    protected void inject(final FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }
}
