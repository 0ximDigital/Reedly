package oxim.digital.reedly.ui.feed.item;

import javax.inject.Inject;

import oxim.digital.reedly.base.BasePresenter;
import oxim.digital.reedly.domain.interactor.GetFeedItemsUseCase;
import oxim.digital.reedly.ui.feed.mapper.FeedViewModeMapper;
import oxim.digital.reedly.ui.feed.model.FeedItemViewModel;
import oxim.digital.reedly.ui.router.Router;
import rx.functions.Action1;

public final class FeedItemsPresenter extends BasePresenter<FeedItemsContract.View> implements FeedItemsContract.Presenter {

    @Inject
    GetFeedItemsUseCase getFeedItemsUseCase;

    @Inject
    FeedViewModeMapper feedViewModeMapper;

    @Inject
    Router router;

    public FeedItemsPresenter(final FeedItemsContract.View view) {
        super(view);
    }

    @Override
    public void fetchFeedItems(final int feedId) {
        viewActionQueue.subscribeTo(getFeedItemsUseCase.execute(feedId)
                                                       .map(feedViewModeMapper::mapFeedItemsToViewModels)
                                                       .map(feedItems -> (Action1<FeedItemsContract.View>) view -> view.showFeedItems(feedItems)),
                                    Throwable::printStackTrace);
    }

    @Override
    public void showItemContent(final FeedItemViewModel feedItemViewModel) {
        router.showFeedItemContentScreen(feedItemViewModel.link);
    }
}
