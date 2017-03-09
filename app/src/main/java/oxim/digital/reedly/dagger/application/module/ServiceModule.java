package oxim.digital.reedly.dagger.application.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oxim.digital.reedly.data.feed.converter.FeedModelConverter;
import oxim.digital.reedly.data.feed.parser.FeedParser;
import oxim.digital.reedly.data.feed.service.FeedServiceImpl;
import oxim.digital.reedly.domain.service.FeedService;

@Module
public final class ServiceModule {

    @Provides
    @Singleton
    FeedService provideFeedService(final FeedParser feedParser, final FeedModelConverter feedModelConverter) {
        return new FeedServiceImpl(feedParser, feedModelConverter);
    }

    public interface Exposes {

        FeedService feedService();
    }
}
