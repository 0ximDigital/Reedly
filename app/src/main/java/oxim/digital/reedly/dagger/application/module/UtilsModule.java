package oxim.digital.reedly.dagger.application.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oxim.digital.reedly.data.util.CurrentTimeProvider;
import oxim.digital.reedly.data.util.CurrentTimeProviderImpl;
import oxim.digital.reedly.domain.util.CollectionUtils;
import oxim.digital.reedly.domain.util.CollectionUtilsImpl;
import oxim.digital.reedly.util.ActivityUtils;
import oxim.digital.reedly.util.ActivityUtilsImpl;
import oxim.digital.reedly.util.DateUtils;
import oxim.digital.reedly.util.DateUtilsImpl;

@Module
public final class UtilsModule {

    @Provides
    @Singleton
    CollectionUtils provideCollectionUtils() {
        return new CollectionUtilsImpl();
    }

    @Provides
    @Singleton
    ActivityUtils provideActivityUtils(final CollectionUtils collectionUtils) {
        return new ActivityUtilsImpl(collectionUtils);
    }

    @Provides
    @Singleton
    CurrentTimeProvider provideCurrentTimeProvider() {
        return new CurrentTimeProviderImpl();
    }

    @Provides
    @Singleton
    DateUtils provideDateUtils() {
        return new DateUtilsImpl();
    }

    public interface Exposes {

        CollectionUtils collectionUtils();

        ActivityUtils activityUtils();

        CurrentTimeProvider currentTimeProvider();

        DateUtils dateUtils();
    }
}
