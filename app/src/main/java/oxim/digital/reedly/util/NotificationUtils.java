package oxim.digital.reedly.util;

import android.app.Notification;

public interface NotificationUtils {

    void showNotification(int notificationId, Notification notification);

    void updateNotification(int notificationId, Notification notification);

    void hideNotification(int notificationId);

    void hideNotifications();
}
