package oxim.digital.reedly.ui.feed.background;

import android.app.job.JobParameters;
import android.app.job.JobService;

import javax.inject.Inject;

import oxim.digital.reedly.dagger.application.ReedlyApplication;
import oxim.digital.reedly.domain.interactor.GetUserFeedsUseCase;
import oxim.digital.reedly.domain.interactor.UpdateFeedUseCase;
import rx.Observable;
import rx.schedulers.Schedulers;

public final class BackgroundFeedsUpdateService extends JobService {

    @Inject
    GetUserFeedsUseCase getUserFeedsUseCase;

    @Inject
    UpdateFeedUseCase updateFeedUseCase;

    @Override
    public void onCreate() {
        super.onCreate();
        ((ReedlyApplication) getApplication().getApplicationContext()).getApplicationComponent().inject(this);
    }

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        getUserFeedsUseCase.execute()
                           .flatMapObservable(Observable::from)
                           .flatMap(feed -> updateFeedUseCase.execute(feed).toObservable())
                           .toCompletable()
                           .doOnTerminate(() -> jobFinished(jobParameters, false))
                           .subscribeOn(Schedulers.io())
                           .subscribe(this::onFeedUpdateError, () -> { });
        return true;
    }

    private void onFeedUpdateError(final Throwable throwable) {
        // TODO - Crashlytics log
        throwable.printStackTrace();
    }

    @Override
    public boolean onStopJob(final JobParameters jobParameters) {
        return false;
    }
}
