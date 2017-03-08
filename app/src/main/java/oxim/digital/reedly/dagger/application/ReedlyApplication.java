package oxim.digital.reedly.dagger.application;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import oxim.digital.reedly.dagger.ComponentFactory;

public final class ReedlyApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        applicationComponent = ComponentFactory.createApplicationComponent(this);
        applicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
