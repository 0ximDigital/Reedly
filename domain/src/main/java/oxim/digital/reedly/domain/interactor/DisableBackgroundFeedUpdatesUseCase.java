package oxim.digital.reedly.domain.interactor;

import oxim.digital.reedly.domain.interactor.type.CompletableUseCase;
import oxim.digital.reedly.domain.repository.FeedsUpdateScheduler;
import rx.Completable;

public final class DisableBackgroundFeedUpdatesUseCase implements CompletableUseCase {

    private final SetShouldUpdateFeedsInBackground setShouldUpdateFeedsInBackground;
    private final FeedsUpdateScheduler feedsUpdateScheduler;

    public DisableBackgroundFeedUpdatesUseCase(final SetShouldUpdateFeedsInBackground setShouldUpdateFeedsInBackground,
                                               final FeedsUpdateScheduler feedsUpdateScheduler) {
        this.setShouldUpdateFeedsInBackground = setShouldUpdateFeedsInBackground;
        this.feedsUpdateScheduler = feedsUpdateScheduler;
    }

    @Override
    public Completable execute() {
        return setShouldUpdateFeedsInBackground.execute(false)
                                               .concatWith(Completable.fromAction(feedsUpdateScheduler::cancelBackgroundFeedUpdates));
    }
}
