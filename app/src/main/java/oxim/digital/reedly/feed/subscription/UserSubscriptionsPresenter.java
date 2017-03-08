package oxim.digital.reedly.feed.subscription;

import javax.inject.Inject;

import oxim.digital.reedly.base.BasePresenter;
import oxim.digital.reedly.domain.interactor.GetUserSubscribedFeedsUseCase;
import rx.functions.Action1;

public final class UserSubscriptionsPresenter extends BasePresenter<UserSubscriptionsContract.View> implements UserSubscriptionsContract.Presenter {

    @Inject
    GetUserSubscribedFeedsUseCase getUserSubscribedFeedsUseCase;

    public UserSubscriptionsPresenter(final UserSubscriptionsContract.View view) {
        super(view);
    }

    @Override
    public void fetchUserSubscriptions() {
        viewActionQueue.subscribeTo(getUserSubscribedFeedsUseCase.execute()
                                                                 .map(subscribedFeeds -> (Action1<UserSubscriptionsContract.View>) view -> view
                                                                         .showFeedSubscriptions(subscribedFeeds)),
                                    Throwable::printStackTrace);
    }
}
