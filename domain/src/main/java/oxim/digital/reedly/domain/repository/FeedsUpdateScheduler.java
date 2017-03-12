package oxim.digital.reedly.domain.repository;

public interface FeedsUpdateScheduler {

    void scheduleBackgroundFeedUpdates();

    void cancelBackgroundFeedUpdates();
}
