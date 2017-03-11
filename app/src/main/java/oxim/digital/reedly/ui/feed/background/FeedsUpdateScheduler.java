package oxim.digital.reedly.ui.feed.background;

public interface FeedsUpdateScheduler {

    void scheduleBackgroundFeedUpdates();

    void cancelBackgroundFeedUpdates();
}
