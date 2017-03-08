package oxim.digital.reedly.base;

import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.ref.WeakReference;

import javax.inject.Inject;
import javax.inject.Named;

import oxim.digital.reedly.configuration.ViewActionQueue;
import oxim.digital.reedly.configuration.ViewActionQueueProvider;
import oxim.digital.reedly.dagger.application.module.ThreadingModule;
import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

public abstract class BasePresenter<View extends BaseView> implements ScopedPresenter {

    @Inject
    protected ViewActionQueueProvider viewActionQueueProvider;

    @Inject
    @Named(ThreadingModule.MAIN_SCHEDULER)
    Scheduler mainThreadScheduler;

    private WeakReference<View> viewReference = new WeakReference<>(null);
    private Subscription viewActionsSubscription;

    protected String viewId;
    protected ViewActionQueue<View> viewActionQueue;

    public BasePresenter(final View view) {
        viewReference = new WeakReference<>(view);
        Log.i("PRES", "Created presenter for -> " + view);
    }

    @Override
    public void start() {
        viewId = getIfViewNotNull(BaseView::getViewId, "");
        viewActionQueue = viewActionQueueProvider.queueFor(viewId);
        Log.i("PRES", "Started presenter for -> " + viewId);
    }

    @Override
    public void activate() {
        Log.i("PRES", "Activating");
        viewActionsSubscription = viewActionQueue.viewActionsObservable()
                                                 .observeOn(mainThreadScheduler)
                                                 .subscribe(this::onViewAction);
        viewActionQueue.resume();
    }

    protected void onViewAction(final Action1<View> viewAction) {
        doIfViewNotNull(viewAction::call);
    }

    @Override
    public void deactivate() {
        Log.i("PRES", "Deactivate");
        viewActionQueue.pause();
        viewActionsSubscription.unsubscribe();
    }

    @Override
    public void destroy() {
        Log.i("PRES", "Destroy");
        viewActionQueue.destroy();
        viewActionQueueProvider.dispose(viewId);
    }

    protected void doIfViewNotNull(final Action1<View> whenViewNotNull) {
        final View view = getNullableView();
        if (view != null) {
            whenViewNotNull.call(view);
        }
    }

    protected <R> R getIfViewNotNull(final Func1<View, R> whenViewNotNull, final R defaultValue) {
        final View view = getNullableView();
        if (view != null) {
            return whenViewNotNull.call(view);
        }
        return defaultValue;
    }

    @Nullable
    protected View getNullableView() {
        return viewReference.get();
    }
}
