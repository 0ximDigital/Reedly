package oxim.digital.reedly.data.feed;

import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.repository.FeedRepository;
import oxim.digital.reedly.domain.service.FeedService;
import rx.Single;
import rx.schedulers.Schedulers;

public final class FeedRepositoryImpl implements FeedRepository {

    private final FeedService feedService;

    public FeedRepositoryImpl(final FeedService feedService) {
        this.feedService = feedService;
    }

    @Override
    public Single<Feed> getFeed(final String url) {
        return Single.defer(() -> feedService.fetchFeed(url))
                     .subscribeOn(Schedulers.io());
    }
}
