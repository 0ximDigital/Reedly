package oxim.digital.reedly.domain.interactor;

import oxim.digital.reedly.domain.interactor.type.SingleUseCase;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Single;

public final class GetUnreadFeedItemsCountUseCase implements SingleUseCase<Long> {

    private final FeedRepository feedRepository;

    public GetUnreadFeedItemsCountUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Single<Long> execute() {
        return feedRepository.getUnreadFeedItemsCount();
    }
}
