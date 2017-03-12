package oxim.digital.reedly.dagger.application;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import javax.inject.Inject;

import oxim.digital.reedly.dagger.ComponentFactory;
import oxim.digital.reedly.ui.feed.background.FeedsUpdateScheduler;

public final class ReedlyApplication extends Application {

    @Inject
    FeedsUpdateScheduler feedsUpdateScheduler;

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        applicationComponent = ComponentFactory.createApplicationComponent(this);
        applicationComponent.inject(this);

        FlowManager.init(new FlowConfig.Builder(this).build());
        Stetho.initializeWithDefaults(this);

        feedsUpdateScheduler.scheduleBackgroundFeedUpdates();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
