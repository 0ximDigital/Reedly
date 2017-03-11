package oxim.digital.reedly.ui.feed.create;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import oxim.digital.reedly.R;
import oxim.digital.reedly.base.BaseFragment;
import oxim.digital.reedly.base.ScopedPresenter;
import oxim.digital.reedly.dagger.fragment.FragmentComponent;
import oxim.digital.reedly.domain.util.ActionRouter;

public final class NewFeedSubscriptionFragment extends BaseFragment implements NewFeedSubscriptionContract.View {

    public static final String TAG = NewFeedSubscriptionFragment.class.getSimpleName();

    @Inject
    NewFeedSubscriptionContract.Presenter presenter;

    @Inject
    ActionRouter actionRouter;

    @Bind(R.id.new_feed_subscription_content_container)
    CardView newFeedSubscriptionContentContainer;

    @Bind(R.id.dialog_background)
    View dialogBackground;

    @Bind(R.id.feed_url_input)
    TextInputEditText feedUrlInput;

    @Bind(R.id.message_text_view)
    TextView messageTextView;

    @Bind(R.id.loading_indicator)
    View loadingIndicator;

    @Bind(R.id.add_feed_button)
    Button addNewFeedButton;

    public static NewFeedSubscriptionFragment newInstance() {
        return new NewFeedSubscriptionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_new_feed_subscription, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        feedUrlInput.setText("http://android-developers.blogspot.com/feeds/posts/default?alt=rss");
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
    public void showMessage(final String message) {
        messageTextView.setText(message);
    }

    @Override
    public void clearMessage() {
        messageTextView.setText("");
    }

    @Override
    public void showIsLoading(final boolean isLoading) {
        messageTextView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        loadingIndicator.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        addNewFeedButton.setEnabled(!isLoading);

        if (isLoading) {
            actionRouter.blockActions();
        } else {
            actionRouter.unblockActions();
        }
    }

    @Override
    protected void inject(final FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @OnClick(R.id.add_feed_button)
    public void onAddFeedButtonClick() {
        presenter.addNewFeed(feedUrlInput.getText().toString());
    }
}
