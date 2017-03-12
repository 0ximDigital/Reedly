package oxim.digital.reedly.domain.interactor;

import oxim.digital.reedly.domain.interactor.type.CompletableUseCaseWithParameter;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;

public final class MarkFeedItemAsReadUseCase implements CompletableUseCaseWithParameter<Integer> {

    private final FeedRepository feedRepository;

    public MarkFeedItemAsReadUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Completable execute(final Integer feedItemId) {
        return feedRepository.markFeedItemAsRead(feedItemId);
    }
}
