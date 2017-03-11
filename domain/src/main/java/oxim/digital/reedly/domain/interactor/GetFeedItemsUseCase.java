package oxim.digital.reedly.domain.interactor;

import java.util.List;

import oxim.digital.reedly.domain.interactor.type.SingleUseCaseWithParameter;
import oxim.digital.reedly.domain.model.FeedItem;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Single;

public final class GetFeedItemsUseCase implements SingleUseCaseWithParameter<Integer, List<FeedItem>> {

    private final FeedRepository feedRepository;

    public GetFeedItemsUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Single<List<FeedItem>> execute(final Integer feedId) {
        return feedRepository.getFeedItems(feedId);
    }
}
