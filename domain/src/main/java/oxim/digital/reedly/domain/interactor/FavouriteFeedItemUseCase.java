package oxim.digital.reedly.domain.interactor;

import oxim.digital.reedly.domain.interactor.type.CompletableUseCaseWithParameter;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;

public final class FavouriteFeedItemUseCase implements CompletableUseCaseWithParameter<Integer> {

    private final FeedRepository feedRepository;

    public FavouriteFeedItemUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Completable execute(final Integer feedItemId) {
        System.out.println("Favouriting feed item -> " + feedItemId);
        return feedRepository.favouriteFeedItem(feedItemId);
    }
}
