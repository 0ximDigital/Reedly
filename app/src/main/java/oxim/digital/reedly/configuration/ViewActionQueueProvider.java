package oxim.digital.reedly.configuration;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public final class ViewActionQueueProvider {

    private final Map<String, ViewActionQueue> viewActionQueueMap = new HashMap<>();

    public ViewActionQueue queueFor(final String queueId) {
        final ViewActionQueue viewActionQueue = viewActionQueueMap.get(queueId);
        if (viewActionQueue != null) {
            return viewActionQueue;
        }

        final ViewActionQueue newQueue = new ViewActionQueue();
        viewActionQueueMap.put(queueId, newQueue);
        return newQueue;
    }

    public void dispose(final String queueId) {
        viewActionQueueMap.remove(queueId);
    }
}
