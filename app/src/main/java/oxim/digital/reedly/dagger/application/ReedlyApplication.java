package oxim.digital.reedly.dagger.application;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import oxim.digital.reedly.dagger.ComponentFactory;

public final class ReedlyApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        applicationComponent = ComponentFactory.createApplicationComponent(this);
        applicationComponent.inject(this);

        FlowManager.init(new FlowConfig.Builder(this).build());
        Stetho.initializeWithDefaults(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
