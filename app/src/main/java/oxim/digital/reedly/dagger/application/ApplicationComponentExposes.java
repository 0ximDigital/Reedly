package oxim.digital.reedly.dagger.application;

import oxim.digital.reedly.dagger.application.module.ApplicationModule;
import oxim.digital.reedly.dagger.application.module.ThreadingModule;
import oxim.digital.reedly.dagger.application.module.UseCaseModule;
import oxim.digital.reedly.dagger.application.module.UtilsModule;

public interface ApplicationComponentExposes extends ApplicationModule.Exposes,
                                                     ThreadingModule.Exposes,
                                                     UtilsModule.Exposes,
                                                     UseCaseModule.Exposes {

}
