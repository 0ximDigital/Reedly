package oxim.digital.reedly.ui.feed.background;

import android.app.job.JobParameters;
import android.app.job.JobService;

import javax.inject.Inject;

import oxim.digital.reedly.dagger.application.ReedlyApplication;
import oxim.digital.reedly.domain.interactor.GetUnreadFeedItemsCountUseCase;
import oxim.digital.reedly.domain.interactor.GetUserFeedsUseCase;
import oxim.digital.reedly.domain.interactor.UpdateFeedUseCase;
import oxim.digital.reedly.util.NotificationUtils;
import rx.Observable;
import rx.schedulers.Schedulers;

public final class BackgroundFeedsUpdateService extends JobService {

    private static final int NEW_FEED_ITEMS_NOTIFICATION_ID = 1832;

    @Inject
    GetUserFeedsUseCase getUserFeedsUseCase;

    @Inject
    UpdateFeedUseCase updateFeedUseCase;

    @Inject
    GetUnreadFeedItemsCountUseCase getUnreadFeedItemsCountUseCase;

    @Inject
    NotificationUtils notificationUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        ((ReedlyApplication) getApplication().getApplicationContext()).getApplicationComponent().inject(this);
    }

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        getUnreadFeedItemsCountUseCase.execute()
                                      .flatMapObservable(unreadItemsCount -> getUserFeedsUseCase.execute()
                                                                                                .flatMapObservable(Observable::from)
                                                                                                .flatMap(feed -> updateFeedUseCase.execute(feed).toObservable())
                                                                                                .flatMap(o -> getUnreadFeedItemsCountUseCase.execute().toObservable())
                                                                                                .doOnNext(newUnreadCount -> {
                                                                                                    if (newUnreadCount > unreadItemsCount) {
                                                                                                        showNotification();
                                                                                                    }
                                                                                                }))
                                      .doOnTerminate(() -> jobFinished(jobParameters, false))
                                      .toCompletable()
                                      .subscribeOn(Schedulers.io())
                                      .subscribe(this::onFeedUpdateError, () -> {
                                      });

        return true;
    }

    private void showNotification() {
        notificationUtils.showNotification(NEW_FEED_ITEMS_NOTIFICATION_ID, NotificationFactory.createFeedUpdateNotification(getApplicationContext(), null));
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
