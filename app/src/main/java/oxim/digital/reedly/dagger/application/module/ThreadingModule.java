package oxim.digital.reedly.dagger.application.module;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

@Module
public final class ThreadingModule {

    public static final String MAIN_SCHEDULER = "main_scheduler";

    @Provides
    @Singleton
    @Named(ThreadingModule.MAIN_SCHEDULER)
    public Scheduler provideMainScheduler() {
        return AndroidSchedulers.mainThread();
    }

    public interface Exposes {

        @Named(ThreadingModule.MAIN_SCHEDULER)
        Scheduler mainScheduler();
    }
}
