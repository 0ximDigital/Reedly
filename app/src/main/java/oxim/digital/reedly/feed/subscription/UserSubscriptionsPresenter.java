package oxim.digital.reedly.feed.subscription;

import java.util.Arrays;

import javax.inject.Inject;

import oxim.digital.reedly.base.BasePresenter;
import oxim.digital.reedly.domain.interactor.GetFeedUseCase;
import oxim.digital.reedly.domain.interactor.GetUserSubscribedFeedsUseCase;
import oxim.digital.reedly.feed.mapper.FeedViewModeMapper;
import rx.functions.Action1;

public final class UserSubscriptionsPresenter extends BasePresenter<UserSubscriptionsContract.View> implements UserSubscriptionsContract.Presenter {

    @Inject
    GetUserSubscribedFeedsUseCase getUserSubscribedFeedsUseCase;

    @Inject
    GetFeedUseCase getFeedUseCase;

    @Inject
    FeedViewModeMapper feedViewModeMapper;

    private static final String TEST_FEED_URL = "https://xkcd.com/rss.xml";

    public UserSubscriptionsPresenter(final UserSubscriptionsContract.View view) {
        super(view);
    }

    @Override
    public void fetchUserSubscriptions() {
        viewActionQueue.subscribeTo(getFeedUseCase.execute(TEST_FEED_URL)
                                                  .map(feedViewModeMapper::mapFeedToViewModel)
                                                  .map(feedViewModel -> (Action1<UserSubscriptionsContract.View>) view -> view
                                                          .showFeedSubscriptions(Arrays.asList(feedViewModel))),
                                    Throwable::printStackTrace);
    }
}
