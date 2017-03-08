package oxim.digital.reedly.dagger.application.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oxim.digital.reedly.domain.interactor.GetUserSubscribedFeedsUseCase;

@Module
public final class UseCaseModule {

    @Provides
    @Singleton
    GetUserSubscribedFeedsUseCase provideGetUserSubscribedFeedsUseCase() {
        return new GetUserSubscribedFeedsUseCase();
    }

    public interface Exposes {

        GetUserSubscribedFeedsUseCase getUserSubscribedFeedsUseCase();
    }
}
