package oxim.digital.reedly.ui.feed.subscription;

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
import butterknife.OnClick;
import oxim.digital.reedly.R;
import oxim.digital.reedly.base.BaseFragment;
import oxim.digital.reedly.base.ScopedPresenter;
import oxim.digital.reedly.dagger.fragment.FragmentComponent;
import oxim.digital.reedly.ui.feed.model.FeedViewModel;

public final class UserSubscriptionsFragment extends BaseFragment implements UserSubscriptionsContract.View {

    public static final String TAG = UserSubscriptionsFragment.class.getSimpleName();

    @Inject
    UserSubscriptionsContract.Presenter presenter;

    @Bind(R.id.user_feeds_recycler_view)
    RecyclerView userFeedsRecyclerView;

    @Bind(R.id.empty_state_view)
    TextView emptyStateView;

    private RecyclerView.LayoutManager feedsLayoutManager;
    private FeedAdapter feedAdapter;

    public static UserSubscriptionsFragment newInstance() {
        return new UserSubscriptionsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_user_subscriptions, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        if (userFeedsRecyclerView.getAdapter() == null) {
            feedAdapter = new FeedAdapter();
            userFeedsRecyclerView.setAdapter(feedAdapter);
        } else {
            feedAdapter = (FeedAdapter) userFeedsRecyclerView.getAdapter();
        }
        feedAdapter.onItemClick()
                   .subscribe(this::onFeedSelected);
        feedsLayoutManager = new LinearLayoutManager(null);             // TODO - inject this
        userFeedsRecyclerView.setLayoutManager(feedsLayoutManager);
    }

    private void onFeedSelected(final FeedViewModel feedViewModel) {
        presenter.showFeedItems(feedViewModel);
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
    public void showFeedSubscriptions(final List<FeedViewModel> feedSubscriptions) {
        feedAdapter.onFeedsUpdate(feedSubscriptions);
    }

    @Override
    protected void inject(final FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @OnClick(R.id.add_new_feed_button)
    public void onSubscriptionsButtonClick() {
        presenter.fetchUserSubscriptions();
//        Toast.makeText(getContext(), "TODO", Toast.LENGTH_SHORT).show();
    }
}
