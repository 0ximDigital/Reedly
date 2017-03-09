package oxim.digital.reedly.domain.interactor;

import oxim.digital.reedly.domain.interactor.type.SingleUseCaseWithParameter;
import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Single;

public final class GetFeedUseCase implements SingleUseCaseWithParameter<String, Feed> {

    private final FeedRepository feedRepository;

    public GetFeedUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Single<Feed> execute(final String url) {
        return feedRepository.getFeed(url);
    }
}
