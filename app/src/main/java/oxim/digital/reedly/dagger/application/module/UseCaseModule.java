package oxim.digital.reedly.dagger.application.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oxim.digital.reedly.domain.interactor.AddNewFeedUseCase;
import oxim.digital.reedly.domain.interactor.DeleteFeedUseCase;
import oxim.digital.reedly.domain.interactor.DisableBackgroundFeedUpdatesUseCase;
import oxim.digital.reedly.domain.interactor.EnableBackgroundFeedUpdatesUseCase;
import oxim.digital.reedly.domain.interactor.FavouriteFeedItemUseCase;
import oxim.digital.reedly.domain.interactor.GetFavouriteFeedItemsUseCase;
import oxim.digital.reedly.domain.interactor.GetFeedItemsUseCase;
import oxim.digital.reedly.domain.interactor.GetUnreadFeedItemsCountUseCase;
import oxim.digital.reedly.domain.interactor.GetUserFeedsUseCase;
import oxim.digital.reedly.domain.interactor.IsUserSubscribedToFeedUseCase;
import oxim.digital.reedly.domain.interactor.MarkFeedItemAsReadUseCase;
import oxim.digital.reedly.domain.interactor.SetShouldUpdateFeedsInBackground;
import oxim.digital.reedly.domain.interactor.ShouldUpdateFeedsInBackgroundUseCase;
import oxim.digital.reedly.domain.interactor.UnFavouriteFeedItemUseCase;
import oxim.digital.reedly.domain.interactor.UpdateFeedUseCase;
import oxim.digital.reedly.domain.repository.FeedRepository;
import oxim.digital.reedly.domain.repository.FeedsUpdateScheduler;

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
    IsUserSubscribedToFeedUseCase provideIsUserSubscribedToFeedUseCase(final FeedRepository feedRepository) {
        return new IsUserSubscribedToFeedUseCase(feedRepository);
    }

    @Provides
    @Singleton
    UpdateFeedUseCase provideUpdateFeedUseCase(final FeedRepository feedRepository) {
        return new UpdateFeedUseCase(feedRepository);
    }

    @Provides
    @Singleton
    MarkFeedItemAsReadUseCase provideMarkFeedItemAsReadUseCase(final FeedRepository feedRepository) {
        return new MarkFeedItemAsReadUseCase(feedRepository);
    }

    @Provides
    @Singleton
    FavouriteFeedItemUseCase provideFavouriteFeedItemUseCase(final FeedRepository feedRepository) {
        return new FavouriteFeedItemUseCase(feedRepository);
    }

    @Provides
    @Singleton
    UnFavouriteFeedItemUseCase provideUnFavouriteFeedItemUseCase(final FeedRepository feedRepository) {
        return new UnFavouriteFeedItemUseCase(feedRepository);
    }

    @Provides
    @Singleton
    GetUnreadFeedItemsCountUseCase provideGetUnreadFeedItemsCountUseCase(final FeedRepository feedRepository) {
        return new GetUnreadFeedItemsCountUseCase(feedRepository);
    }

    @Provides
    @Singleton
    GetFavouriteFeedItemsUseCase provideGetFavouriteFeedItemsUseCase(final FeedRepository feedRepository) {
        return new GetFavouriteFeedItemsUseCase(feedRepository);
    }

    @Provides
    @Singleton
    ShouldUpdateFeedsInBackgroundUseCase provideShouldUpdateFeedsInBackgroundUseCase(final FeedRepository feedRepository) {
        return new ShouldUpdateFeedsInBackgroundUseCase(feedRepository);
    }

    @Provides
    @Singleton
    SetShouldUpdateFeedsInBackground provideSetShouldUpdateFeedsInBackground(final FeedRepository feedRepository) {
        return new SetShouldUpdateFeedsInBackground(feedRepository);
    }

    @Provides
    @Singleton
    EnableBackgroundFeedUpdatesUseCase provideEnableBackgroundFeedUpdatesUseCase(final SetShouldUpdateFeedsInBackground setShouldUpdateFeedsInBackground,
                                                                                 final FeedsUpdateScheduler feedsUpdateScheduler) {
        return new EnableBackgroundFeedUpdatesUseCase(setShouldUpdateFeedsInBackground, feedsUpdateScheduler);
    }

    @Provides
    @Singleton
    DisableBackgroundFeedUpdatesUseCase provideDisableBackgroundFeedUpdatesUseCase(final SetShouldUpdateFeedsInBackground setShouldUpdateFeedsInBackground,
                                                                                   final FeedsUpdateScheduler feedsUpdateScheduler) {
        return new DisableBackgroundFeedUpdatesUseCase(setShouldUpdateFeedsInBackground, feedsUpdateScheduler);
    }

    public interface Exposes {

        GetUserFeedsUseCase getUserSubscribedFeedsUseCase();

        AddNewFeedUseCase addNewFeedUseCase();

        GetFeedItemsUseCase getFeedItemsUseCase();

        DeleteFeedUseCase deleteFeedUseCase();

        IsUserSubscribedToFeedUseCase isUserSubscribedToFeedUseCase();

        UpdateFeedUseCase updateFeedUseCase();

        MarkFeedItemAsReadUseCase markFeedItemAsReadUseCase();

        FavouriteFeedItemUseCase favouriteFeedItemUseCase();

        UnFavouriteFeedItemUseCase unFavouriteFeedItemUseCase();

        GetUnreadFeedItemsCountUseCase getUnreadFeedItemsCountUseCase();

        GetFavouriteFeedItemsUseCase getFavouriteFeedItemsUseCase();

        ShouldUpdateFeedsInBackgroundUseCase shouldUpdateFeedsInBackgroundUseCase();

        SetShouldUpdateFeedsInBackground setShouldUpdateFeedsInBackground();

        EnableBackgroundFeedUpdatesUseCase enableBackgroundFeedUpdatesUseCase();

        DisableBackgroundFeedUpdatesUseCase disableBackgroundFeedUpdatesUseCase();
    }
}
