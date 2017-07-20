package oxim.digital.reedly.dagger.application;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import javax.inject.Inject;

import oxim.digital.reedly.dagger.ComponentFactory;
import oxim.digital.reedly.domain.interactor.feed.update.EnableBackgroundFeedUpdatesUseCase;
import oxim.digital.reedly.domain.interactor.feed.update.ShouldUpdateFeedsInBackgroundUseCase;
import rx.Completable;

public final class ReedlyApplication extends Application {

    @Inject
    ShouldUpdateFeedsInBackgroundUseCase shouldUpdateFeedsInBackgroundUseCase;

    @Inject
    EnableBackgroundFeedUpdatesUseCase enableBackgroundFeedUpdatesUseCase;

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        applicationComponent = ComponentFactory.createApplicationComponent(this);
        applicationComponent.inject(this);

        FlowManager.init(new FlowConfig.Builder(this).build());
        checkForBackgroundUpdate();

//        if (BuildConfig.DEBUG) {
//            Stetho.initializeWithDefaults(this);
//        }
    }

    private void checkForBackgroundUpdate() {
        shouldUpdateFeedsInBackgroundUseCase.execute()
                                            .flatMap(shouldUpdate -> ((shouldUpdate) ? enableBackgroundFeedUpdatesUseCase.execute()
                                                                                     : Completable.complete())
                                                    .toSingleDefault(true))
                                            .subscribe();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
