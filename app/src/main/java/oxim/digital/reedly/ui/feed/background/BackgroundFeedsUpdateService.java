package oxim.digital.reedly.ui.feed.background;

import android.app.PendingIntent;
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

    @Inject
    NotificationFactory notificationFactory;

    @Inject
    PendingIntent notificationPendingIntent;

    @Override
    public void onCreate() {
        super.onCreate();
        ((ReedlyApplication) getApplication().getApplicationContext()).getApplicationComponent().inject(this);
    }

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        getUnreadFeedItemsCountUseCase.execute()
                                      .subscribeOn(Schedulers.io())
                                      .subscribe(unreadCount -> onUnreadItemsCount(unreadCount, jobParameters),
                                                 throwable -> handleError(throwable, jobParameters));
        return true;
    }

    private void onUnreadItemsCount(final long unreadItemsCount, final JobParameters jobParameters) {
        getUserFeedsUseCase.execute()
                           .flatMapObservable(Observable::from)
                           .flatMap(feed -> updateFeedUseCase.execute(feed).toObservable())
                           .toCompletable()
                           .subscribeOn(Schedulers.io())
                           .subscribe(throwable -> handleError(throwable, jobParameters),
                                      () -> onUpdatedFeeds(unreadItemsCount, jobParameters));
    }

    private void onUpdatedFeeds(final long unreadItemsCount, final JobParameters jobParameters) {
        getUnreadFeedItemsCountUseCase.execute()
                                      .subscribeOn(Schedulers.io())
                                      .doOnSuccess(c -> jobFinished(jobParameters, false))
                                      .subscribe(newUnreadCount -> onNewUnreadCount(unreadItemsCount, newUnreadCount),
                                                 throwable -> handleError(throwable, jobParameters));
    }

    private void onNewUnreadCount(final long oldCount, final long newCount) {
        if (newCount > oldCount) {
            showNotification();
        }
    }

    private void handleError(final Throwable throwable, final JobParameters jobParameters) {
        // TODO - Crashlytics
        throwable.printStackTrace();
        jobFinished(jobParameters, false);
    }

    private void showNotification() {
        notificationUtils.showNotification(NEW_FEED_ITEMS_NOTIFICATION_ID,
                                           notificationFactory.createFeedUpdateNotification(getApplicationContext(), notificationPendingIntent));
    }

    @Override
    public boolean onStopJob(final JobParameters jobParameters) {
        return false;
    }
}
