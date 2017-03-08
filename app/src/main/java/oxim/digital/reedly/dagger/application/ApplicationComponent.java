package oxim.digital.reedly.dagger.application;

import javax.inject.Singleton;

import dagger.Component;
import oxim.digital.reedly.dagger.application.module.ApplicationModule;
import oxim.digital.reedly.dagger.application.module.ThreadingModule;
import oxim.digital.reedly.dagger.application.module.UseCaseModule;
import oxim.digital.reedly.dagger.application.module.UtilsModule;

@Singleton
@Component(
        modules = {
                ApplicationModule.class,
                ThreadingModule.class,
                UtilsModule.class,
                UseCaseModule.class
        }
)

public interface ApplicationComponent extends ApplicationComponentInjects, ApplicationComponentExposes {

    final class Initializer {

        static public ApplicationComponent init(final ReedlyApplication reedlyApplication) {
            return DaggerApplicationComponent.builder()
                                             .applicationModule(new ApplicationModule(reedlyApplication))
                                             .threadingModule(new ThreadingModule())
                                             .utilsModule(new UtilsModule())
                                             .useCaseModule(new UseCaseModule())
                                             .build();
        }

        private Initializer() {
        }
    }
}
