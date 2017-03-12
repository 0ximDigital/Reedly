package oxim.digital.reedly.ui.feed.item;

import javax.inject.Inject;

import oxim.digital.reedly.base.BasePresenter;
import oxim.digital.reedly.domain.interactor.FavouriteFeedItemUseCase;
import oxim.digital.reedly.domain.interactor.GetFavouriteFeedItemsUseCase;
import oxim.digital.reedly.domain.interactor.GetFeedItemsUseCase;
import oxim.digital.reedly.domain.interactor.MarkFeedItemAsReadUseCase;
import oxim.digital.reedly.domain.interactor.UnFavouriteFeedItemUseCase;
import oxim.digital.reedly.ui.feed.mapper.FeedViewModeMapper;
import oxim.digital.reedly.ui.feed.model.FeedItemViewModel;
import rx.functions.Action1;

public final class FeedItemsPresenter extends BasePresenter<FeedItemsContract.View> implements FeedItemsContract.Presenter {

    @Inject
    GetFeedItemsUseCase getFeedItemsUseCase;

    @Inject
    MarkFeedItemAsReadUseCase markFeedItemAsReadUseCase;

    @Inject
    FavouriteFeedItemUseCase favouriteFeedItemUseCase;

    @Inject
    UnFavouriteFeedItemUseCase unFavouriteFeedItemUseCase;

    @Inject
    GetFavouriteFeedItemsUseCase getFavouriteFeedItemsUseCase;

    @Inject
    FeedViewModeMapper feedViewModeMapper;

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
    public void fetchFavouriteFeedItems() {
        viewActionQueue.subscribeTo(getFavouriteFeedItemsUseCase.execute()
                                                                .map(feedViewModeMapper::mapFeedItemsToViewModels)
                                                                .map(feedItems -> (Action1<FeedItemsContract.View>) view -> view.showFeedItems(feedItems)),
                                    Throwable::printStackTrace);
    }

    @Override
    public void showItemContent(final FeedItemViewModel feedItemViewModel) {
        router.showFeedItemContentScreen(feedItemViewModel.link);
    }

    @Override
    public void markFeedItemAsRead(final int feedItemId) {
        viewActionQueue.subscribeTo(markFeedItemAsReadUseCase.execute(feedItemId),
                                    view -> { },
                                    Throwable::printStackTrace);
    }

    @Override
    public void toggleFeedItemFavourite(final FeedItemViewModel feedItemViewModel) {
        viewActionQueue.subscribeTo(feedItemViewModel.isFavourite ? unFavouriteFeedItemUseCase.execute(feedItemViewModel.id)
                                                                  : favouriteFeedItemUseCase.execute(feedItemViewModel.id),
                                    view -> { },
                                    Throwable::printStackTrace);
    }
}
