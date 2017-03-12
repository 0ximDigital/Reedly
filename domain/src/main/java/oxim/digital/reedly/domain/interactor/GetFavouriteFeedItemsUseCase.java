package oxim.digital.reedly.domain.interactor;

import java.util.List;

import oxim.digital.reedly.domain.interactor.type.SingleUseCase;
import oxim.digital.reedly.domain.model.FeedItem;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Single;

public final class GetFavouriteFeedItemsUseCase implements SingleUseCase<List<FeedItem>>{

    private final FeedRepository feedRepository;

    public GetFavouriteFeedItemsUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Single<List<FeedItem>> execute() {
        return feedRepository.getFavouriteFeedItems();
    }
}
