package com.virendra.mpd.meshcom;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.virendra.mpd.meshcom.Activities.MainActivity;

/**
 * Created by Virendra on 24-03-2017.
 */

public class Notification {

    private static NotificationCompat.Builder myBuilder;
    private static NotificationManager myNotificationManager;
    public static final int ID_NOTIFICATION = 123;

    public static void makeNotification(Context context, String notification)
    {
        myBuilder = new NotificationCompat.Builder(context);
        myBuilder.setSmallIcon(R.mipmap.ic_launcher);
        myBuilder.setContentTitle("New Message");
        myBuilder.setContentText(notification);

        Intent resultIntent = new Intent(context, MainActivity.class);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0
                , PendingIntent.FLAG_UPDATE_CURRENT);
        myBuilder.setContentIntent(resultPendingIntent);

        myNotificationManager = (NotificationManager)context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        myNotificationManager.notify(ID_NOTIFICATION, myBuilder.build());
    }
}
