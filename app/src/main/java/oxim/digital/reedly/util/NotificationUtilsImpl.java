package oxim.digital.reedly.util;

import android.app.Notification;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public final class NotificationUtilsImpl implements NotificationUtils {

    private final NotificationManagerCompat notificationManagerCompat;

    public NotificationUtilsImpl(@NonNull final Context context) {
        this.notificationManagerCompat = NotificationManagerCompat.from(context);
    }

    @Override
    public void showNotification(final int notificationId, final Notification notification) {
        notificationManagerCompat.notify(notificationId, notification);
    }

    @Override
    public void updateNotification(final int notificationId, final Notification notification) {
        notificationManagerCompat.notify(notificationId, notification);
    }

    @Override
    public void hideNotification(final int notificationId) {
        notificationManagerCompat.cancel(notificationId);
    }

    @Override
    public void hideNotifications() {
        notificationManagerCompat.cancelAll();
    }
}
