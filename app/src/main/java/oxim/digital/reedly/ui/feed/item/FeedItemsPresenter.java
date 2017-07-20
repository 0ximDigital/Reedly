package oxim.digital.reedly.ui.feed.item;

import javax.inject.Inject;

import oxim.digital.reedly.base.BasePresenter;
import oxim.digital.reedly.domain.interactor.article.favourite.FavouriteArticleUseCase;
import oxim.digital.reedly.domain.interactor.article.GetArticlesUseCase;
import oxim.digital.reedly.domain.interactor.article.favourite.GetFavouriteArticlesUseCase;
import oxim.digital.reedly.domain.interactor.article.MarkArticleAsReadUseCase;
import oxim.digital.reedly.domain.interactor.article.favourite.UnFavouriteArticleUseCase;
import oxim.digital.reedly.ui.feed.mapper.FeedViewModeMapper;
import oxim.digital.reedly.ui.feed.model.FeedItemViewModel;
import rx.functions.Action1;

public final class FeedItemsPresenter extends BasePresenter<FeedItemsContract.View> implements FeedItemsContract.Presenter {

    @Inject
    GetArticlesUseCase getArticlesUseCase;

    @Inject
    MarkArticleAsReadUseCase markArticleAsReadUseCase;

    @Inject
    FavouriteArticleUseCase favouriteArticleUseCase;

    @Inject
    UnFavouriteArticleUseCase unFavouriteArticleUseCase;

    @Inject
    GetFavouriteArticlesUseCase getFavouriteArticlesUseCase;

    @Inject
    FeedViewModeMapper feedViewModeMapper;

    public FeedItemsPresenter(final FeedItemsContract.View view) {
        super(view);
    }

    @Override
    public void fetchFeedItems(final int feedId) {
        viewActionQueue.subscribeTo(getArticlesUseCase.execute(feedId)
                                                      .map(feedViewModeMapper::mapFeedItemsToViewModels)
                                                      .map(feedItems -> (Action1<FeedItemsContract.View>) view -> view.showFeedItems(feedItems)),
                                    Throwable::printStackTrace);
    }

    @Override
    public void fetchFavouriteFeedItems() {
        viewActionQueue.subscribeTo(getFavouriteArticlesUseCase.execute()
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
        viewActionQueue.subscribeTo(markArticleAsReadUseCase.execute(feedItemId),
                                    view -> { },
                                    Throwable::printStackTrace);
    }

    @Override
    public void toggleFeedItemFavourite(final FeedItemViewModel feedItemViewModel) {
        viewActionQueue.subscribeTo(feedItemViewModel.isFavourite ? unFavouriteArticleUseCase.execute(feedItemViewModel.id)
                                                                  : favouriteArticleUseCase.execute(feedItemViewModel.id),
                                    view -> { },
                                    Throwable::printStackTrace);
    }
}
