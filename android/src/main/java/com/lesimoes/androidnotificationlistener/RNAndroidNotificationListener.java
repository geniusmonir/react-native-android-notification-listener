 
package com.lesimoes.androidnotificationlistener;

import android.content.Intent;
import android.content.Context;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.app.Notification;
import com.google.gson.Gson;

import com.facebook.react.HeadlessJsTaskService;

public class RNAndroidNotificationListener extends NotificationListenerService {
    private static final String TAG = "RNAndroidNotificationListener";

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {       
        Notification statusBarNotification = sbn.getNotification();

        Log.d(TAG, "Notification Posted Event Fired"); 

        if (statusBarNotification == null || statusBarNotification.extras == null) {
            Log.d(TAG, "The notification received has no data");
            return;
        }

        Context context = getApplicationContext();

        Intent serviceIntent = new Intent(context, RNAndroidNotificationListenerHeadlessJsTaskService.class);

        RNNotification notification = new RNNotification(context, sbn,"posted");

        Gson gson = new Gson();
        String serializedNotification = gson.toJson(notification);

        serviceIntent.putExtra("notification", serializedNotification);

        HeadlessJsTaskService.acquireWakeLockNow(context);

        context.startService(serviceIntent);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Notification statusBarNotification = sbn.getNotification();
        
        Log.d(TAG, "Notification Removed Event Fired"); 

        if (statusBarNotification == null || statusBarNotification.extras == null) {
            Log.d(TAG, "The notification received has no data");
            return;
        }

        Context context = getApplicationContext();

        Intent serviceIntent = new Intent(context, RNAndroidNotificationListenerHeadlessJsTaskService.class);

        RNNotification notification = new RNNotification(context, sbn, "removed");

        Gson gson = new Gson();
        String serializedNotification = gson.toJson(notification);

        serviceIntent.putExtra("notification", serializedNotification);

        HeadlessJsTaskService.acquireWakeLockNow(context);

        context.startService(serviceIntent);
    }
}
