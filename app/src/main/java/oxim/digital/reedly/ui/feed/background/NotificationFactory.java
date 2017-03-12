package oxim.digital.reedly.ui.feed.background;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

public final class NotificationFactory {

    private NotificationFactory() {
    }

    public static Notification createFeedUpdateNotification(final Context context, final PendingIntent pendingIntent) {
        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        return notificationBuilder.setAutoCancel(true)
//                                  .setColor(getResources().getColor(R.color.notification_background))
                                  .setContentTitle("New articles!")
                                  .setContentText("There have been some new articles posted in your feeds")
                                  .setContentIntent(pendingIntent)
                                  .build();
    }
}
