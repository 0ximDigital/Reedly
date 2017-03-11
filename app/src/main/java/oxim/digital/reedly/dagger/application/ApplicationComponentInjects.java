package oxim.digital.reedly.dagger.application;

import oxim.digital.reedly.ui.feed.background.BackgroundFeedsUpdateService;

public interface ApplicationComponentInjects {

    void inject(ReedlyApplication reedlyApplication);

    void inject(BackgroundFeedsUpdateService backgroundFeedsUpdateService);
}
