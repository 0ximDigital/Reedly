package oxim.digital.reedly.ui.feed.article;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public final class FeedItemClient extends WebViewClient {

    private final String itemUrl;

    public FeedItemClient(final String itemUrl) {
        this.itemUrl = itemUrl;
    }

    @Override
    public boolean shouldOverrideUrlLoading(final WebView view, final WebResourceRequest request) {
        if (request.getUrl().toString().equals(itemUrl)) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
        return super.shouldOverrideUrlLoading(view, request);
    }
}
