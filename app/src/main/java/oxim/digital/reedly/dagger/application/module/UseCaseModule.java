package oxim.digital.reedly.dagger.application.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oxim.digital.reedly.domain.interactor.GetFeedUseCase;
import oxim.digital.reedly.domain.interactor.GetUserSubscribedFeedsUseCase;
import oxim.digital.reedly.domain.repository.FeedRepository;

@Module
public final class UseCaseModule {

    @Provides
    @Singleton
    GetUserSubscribedFeedsUseCase provideGetUserSubscribedFeedsUseCase() {
        return new GetUserSubscribedFeedsUseCase();
    }

    @Provides
    @Singleton
    GetFeedUseCase provideGetFeedUseCase(final FeedRepository feedRepository) {
        return new GetFeedUseCase(feedRepository);
    }

    public interface Exposes {

        GetUserSubscribedFeedsUseCase getUserSubscribedFeedsUseCase();

        GetFeedUseCase getFeedUseCase();
    }
}
