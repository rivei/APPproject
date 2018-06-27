package it.polimi.two.weiava.services;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import it.polimi.two.weiava.R;
import it.polimi.two.weiava.activities.WalkingActivity;

public class DailyNotificationReceiver extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "notification-id";
    private static final String CHANNEL_ID = "channel_01";
    private static final String CHANNEL_NAME = "my_channel";
    //public static String NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        //Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);

        //Active walking time
        Intent repeatintent = new Intent(context, WalkingActivity.class);
        repeatintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //TODO: cancel the alarm?

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, repeatintent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); //Set default notification ringtone
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setContentTitle("A Medical App")//Title of the app
                .setContentText("It is time to measure walking time.") //TODO: change to String constant
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            //notificationManager = getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
            //builder.setChannelId(CHANNEL_ID); // Channel ID
        }else {
            assert notificationManager != null;
            notificationManager.notify(id, builder.build());
        }
        Log.e("AlarmReciever","broadcast Receive.");

    }

}