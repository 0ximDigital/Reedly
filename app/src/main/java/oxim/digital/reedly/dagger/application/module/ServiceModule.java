package oxim.digital.reedly.dagger.application.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oxim.digital.reedly.data.feed.service.FeedService;
import oxim.digital.reedly.data.feed.service.FeedServiceImpl;
import oxim.digital.reedly.data.feed.service.parser.FeedParser;
import oxim.digital.reedly.data.util.CurrentTimeProvider;

@Module
public final class ServiceModule {

    @Provides
    @Singleton
    FeedService provideFeedService(final FeedParser feedParser, final CurrentTimeProvider currentTimeProvider) {
        return new FeedServiceImpl(feedParser, currentTimeProvider);
    }

    public interface Exposes {

        FeedService feedService();
    }
}
