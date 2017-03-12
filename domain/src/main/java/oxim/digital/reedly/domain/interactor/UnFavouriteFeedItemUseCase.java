package oxim.digital.reedly.domain.interactor;

import oxim.digital.reedly.domain.interactor.type.CompletableUseCaseWithParameter;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;

public final class UnFavouriteFeedItemUseCase implements CompletableUseCaseWithParameter<Integer> {

    private final FeedRepository feedRepository;

    public UnFavouriteFeedItemUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Completable execute(final Integer feedItemId) {
        System.out.println("Unfavouriting feed item -> " + feedItemId);
        return feedRepository.unFavouriteFeedItem(feedItemId);
    }
}
