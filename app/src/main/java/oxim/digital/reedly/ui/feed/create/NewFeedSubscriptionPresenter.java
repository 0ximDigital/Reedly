package oxim.digital.reedly.ui.feed.create;

import android.database.sqlite.SQLiteConstraintException;

import javax.inject.Inject;

import oxim.digital.reedly.base.BasePresenter;
import oxim.digital.reedly.domain.interactor.AddNewFeedUseCase;

public final class NewFeedSubscriptionPresenter extends BasePresenter<NewFeedSubscriptionContract.View> implements NewFeedSubscriptionContract.Presenter {

    @Inject
    AddNewFeedUseCase addNewFeedUseCase;

    public NewFeedSubscriptionPresenter(final NewFeedSubscriptionContract.View view) {
        super(view);
    }

    @Override
    public void addNewFeed(final String feedUrl) {
        doIfConnectedToInternet(() -> initiateNewFeedAddition(feedUrl), this::showNoInternetConnection);
    }

    private void initiateNewFeedAddition(final String feedUrl) {
        doIfViewNotNull(view -> view.showIsLoading(true));
        viewActionQueue.subscribeTo(addNewFeedUseCase.execute(feedUrl), this::onAddNewFeedCompletion, this::onAddNewFeedError);
    }

    private void onAddNewFeedCompletion(final NewFeedSubscriptionContract.View view) {
        view.showIsLoading(false);
        back();
        router.showUserSubscriptionsScreen();
    }

    private void onAddNewFeedError(final Throwable throwable) {
        logError(throwable);
        doIfViewNotNull(view -> {
            view.showIsLoading(false);
            view.showMessage((throwable instanceof SQLiteConstraintException) ? "You are already subscribed to this feed dummy :)"
                                                                              : "Incorrect feed URL, please check and try again");
        });
    }

    private void showNoInternetConnection() {
        doIfViewNotNull(view -> view.showMessage("Please check internet connection"));
    }

    @Override
    public void back() {
        destroy();
        router.hideAddNewFeedScreen();
    }
}

