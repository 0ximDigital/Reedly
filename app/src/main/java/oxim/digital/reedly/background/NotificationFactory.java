package oxim.digital.reedly.background;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.NotificationCompat;

import oxim.digital.reedly.R;

public final class NotificationFactory {

    private final Resources resources;

    public NotificationFactory(final Resources resources) {
        this.resources = resources;
    }

    public Notification createFeedUpdateNotification(final Context context, final PendingIntent contentIntent) {
        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        return notificationBuilder.setAutoCancel(true)
                                  .setColor(resources.getColor(R.color.colorPrimary))
                                  .setSmallIcon(R.drawable.notification_icon)
                                  .setContentTitle("New articles!")
                                  .setContentText("There have been some new articles posted in your feeds")
                                  .setContentIntent(contentIntent)
                                  .build();
    }
}
