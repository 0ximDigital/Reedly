package oxim.digital.reedly.ui.feed.article;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import oxim.digital.reedly.R;
import oxim.digital.reedly.base.BaseFragment;
import oxim.digital.reedly.base.ScopedPresenter;
import oxim.digital.reedly.dagger.fragment.FragmentComponent;

public final class FeedItemContentFragment extends BaseFragment implements FeedItemContentContract.View {

    public static final String TAG = FeedItemContentFragment.class.getSimpleName();

    private static final String KEY_FEED_ITEM_CONTENT_URL = "key_feed_item_content_url";

    @Inject
    FeedItemContentContract.Presenter presenter;

    @Bind(R.id.feed_item_content_web_view)
    WebView feedItemContentWebView;

    public static FeedItemContentFragment newInstance(final String feedItemContentUrl) {
        final FeedItemContentFragment fragment = new FeedItemContentFragment();
        final Bundle arguments = new Bundle();
        arguments.putString(KEY_FEED_ITEM_CONTENT_URL, feedItemContentUrl);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_feed_item_content, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        extractArguments(getArguments());
    }

    private void extractArguments(final Bundle arguments) {
        setContentUrl(arguments.getString(KEY_FEED_ITEM_CONTENT_URL));
    }

    private void setupWebView(final String url) {
        feedItemContentWebView.setWebViewClient(new FeedItemClient(url));
        feedItemContentWebView.getSettings().setJavaScriptEnabled(true);
        feedItemContentWebView.loadUrl(url);
    }

    public void setContentUrl(final String url) {
        setupWebView(url);
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
