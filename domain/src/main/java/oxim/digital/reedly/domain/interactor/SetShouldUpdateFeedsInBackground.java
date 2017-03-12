package oxim.digital.reedly.domain.interactor;

import oxim.digital.reedly.domain.interactor.type.CompletableUseCaseWithParameter;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;

public final class SetShouldUpdateFeedsInBackground implements CompletableUseCaseWithParameter<Boolean> {

    private final FeedRepository feedRepository;

    public SetShouldUpdateFeedsInBackground(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Completable execute(final Boolean shouldUpdate) {
        return feedRepository.setShouldUpdateFeedsInBackground(shouldUpdate);
    }
}
