package oxim.digital.reedly.dagger.application.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oxim.digital.reedly.configuration.ViewActionQueueProvider;
import oxim.digital.reedly.configuration.ViewIdGenerator;
import oxim.digital.reedly.dagger.application.ForApplication;
import oxim.digital.reedly.dagger.application.ReedlyApplication;

@Module
public final class ApplicationModule {

    private final ReedlyApplication reedlyApplication;

    public ApplicationModule(final ReedlyApplication reedlyApplication) {
        this.reedlyApplication = reedlyApplication;
    }

    @Provides
    @Singleton
    ReedlyApplication provideReedlyApplication() {
        return reedlyApplication;
    }

    @Provides
    @Singleton
    @ForApplication
    Context provideContext() {
        return reedlyApplication;
    }

    @Provides
    @Singleton
    ViewIdGenerator provideViewIdGenerator() {
        return new ViewIdGenerator();
    }

    @Provides
    @Singleton
    ViewActionQueueProvider provideViewActionQueueProvider() {
        return new ViewActionQueueProvider();
    }

    public interface Exposes {

        ReedlyApplication reedlyApplication();

        @ForApplication
        Context context();

        ViewIdGenerator viewIdGenerator();

        ViewActionQueueProvider viewActionQueueProvider();
    }
}
