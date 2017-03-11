package oxim.digital.reedly.dagger.application.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oxim.digital.reedly.domain.interactor.AddNewFeedUseCase;
import oxim.digital.reedly.domain.interactor.DeleteFeedUseCase;
import oxim.digital.reedly.domain.interactor.GetFeedItemsUseCase;
import oxim.digital.reedly.domain.interactor.GetUserFeedsUseCase;
import oxim.digital.reedly.domain.interactor.IsUserSubscribedToFeedUseCase;
import oxim.digital.reedly.domain.repository.FeedRepository;

@Module
public final class UseCaseModule {

    @Provides
    @Singleton
    GetUserFeedsUseCase provideGetUserSubscribedFeedsUseCase(final FeedRepository feedRepository) {
        return new GetUserFeedsUseCase(feedRepository);
    }

    @Provides
    @Singleton
    AddNewFeedUseCase provideAddNewFeedUseCase(final FeedRepository feedRepository) {
        return new AddNewFeedUseCase(feedRepository);
    }

    @Provides
    @Singleton
    GetFeedItemsUseCase provideGetFeedItemsUseCase(final FeedRepository feedRepository) {
        return new GetFeedItemsUseCase(feedRepository);
    }

    @Provides
    @Singleton
    DeleteFeedUseCase provideDeleteFeedUseCase(final FeedRepository feedRepository) {
        return new DeleteFeedUseCase(feedRepository);
    }

    @Provides
    @Singleton
    IsUserSubscribedToFeedUseCase isUserSubscribedToFeedUseCase(final FeedRepository feedRepository) {
        return new IsUserSubscribedToFeedUseCase(feedRepository);
    }

    public interface Exposes {

        GetUserFeedsUseCase getUserSubscribedFeedsUseCase();

        AddNewFeedUseCase addNewFeedUseCase();

        GetFeedItemsUseCase getFeedItemsUseCase();

        DeleteFeedUseCase deleteFeedUseCase();

        IsUserSubscribedToFeedUseCase isUserSubscribedToFeedUseCase();
    }
}
