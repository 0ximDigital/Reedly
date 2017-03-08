package oxim.digital.reedly.dagger.application.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oxim.digital.reedly.domain.util.CollectionUtils;
import oxim.digital.reedly.domain.util.CollectionUtilsImpl;
import oxim.digital.reedly.util.ActivityUtils;
import oxim.digital.reedly.util.ActivityUtilsImpl;

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

    public interface Exposes {

        CollectionUtils collectionUtils();

        ActivityUtils activityUtils();
    }
}
