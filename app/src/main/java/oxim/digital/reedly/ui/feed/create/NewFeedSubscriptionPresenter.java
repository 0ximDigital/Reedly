package oxim.digital.reedly.ui.feed.create;

import javax.inject.Inject;

import oxim.digital.reedly.base.BasePresenter;
import oxim.digital.reedly.domain.interactor.AddNewFeedUseCase;
import oxim.digital.reedly.ui.router.Router;

public final class NewFeedSubscriptionPresenter extends BasePresenter<NewFeedSubscriptionContract.View> implements NewFeedSubscriptionContract.Presenter {

    @Inject
    AddNewFeedUseCase addNewFeedUseCase;

    @Inject
    Router router;

    public NewFeedSubscriptionPresenter(final NewFeedSubscriptionContract.View view) {
        super(view);
    }

    @Override
    public void addNewFeed(final String feedUrl) {
        // TODO - check internet connection
        // TODO - check for duplicate entry - in the use case -> specified error
        doIfViewNotNull(view -> view.showIsLoading(true));
        viewActionQueue.subscribeTo(addNewFeedUseCase.execute(feedUrl), this::onAddNewFeedCompletion, this::onAddNewFeedError);
    }

    private void onAddNewFeedCompletion(final NewFeedSubscriptionContract.View view) {
        view.showIsLoading(false);
        router.goBack();
        router.showUserSubscriptionsScreen();
    }

    private void onAddNewFeedError(final Throwable throwable) {
        logError(throwable);
        doIfViewNotNull(view -> {
            view.showIsLoading(false);
            view.showMessage("Incorrect feed URL, please check and try again");
        });
    }
}
