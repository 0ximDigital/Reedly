package oxim.digital.reedly.dagger.application.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oxim.digital.reedly.data.feed.FeedRepositoryImpl;
import oxim.digital.reedly.data.feed.converter.FeedModelConverter;
import oxim.digital.reedly.data.feed.converter.FeedModelConverterImpl;
import oxim.digital.reedly.data.feed.service.parser.FeedParser;
import oxim.digital.reedly.data.feed.service.parser.FeedParserImpl;
import oxim.digital.reedly.data.util.CurrentTimeProvider;
import oxim.digital.reedly.domain.repository.FeedRepository;
import oxim.digital.reedly.domain.service.FeedService;

@Module
public final class DataModule {

    @Provides
    @Singleton
    FeedRepository provideFeedRepository(final FeedService feedService) {
        return new FeedRepositoryImpl(feedService);
    }

    @Provides
    @Singleton
    FeedModelConverter provideFeedModelConverter() {
        return new FeedModelConverterImpl();
    }

    @Provides
    @Singleton
    FeedParser provideFeedParser(final CurrentTimeProvider currentTimeProvider) {
        return new FeedParserImpl(currentTimeProvider);
    }

    public interface Exposes {

        FeedRepository feedRepository();

        FeedModelConverter feedModelConverter();

        FeedParser feedParser();
    }
}
