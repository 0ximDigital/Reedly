package oxim.digital.reedly.configuration;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public final class ViewActionQueueProvider {

    private final Map<String, ViewActionQueue> viewActionQueueMap = new HashMap<>();

    public ViewActionQueue queueFor(final String queueId) {
        final ViewActionQueue viewActionQueue = viewActionQueueMap.get(queueId);
        if (viewActionQueue != null) {
            Log.i("VAQP", "Returning existing queue for -> " + queueId);
            return viewActionQueue;
        }

        final ViewActionQueue newQueue = new ViewActionQueue();
        viewActionQueueMap.put(queueId, newQueue);
        Log.i("VAQP", "Returning NEW queue for -> " + queueId);
        return newQueue;
    }

    public void dispose(final String queueId) {
        Log.i("VAQP", "Disposing view action queue for -> " + queueId);
        viewActionQueueMap.remove(queueId);
    }
}
