package it.polimi.two.weiava.services;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import android.util.Log;

import it.polimi.two.weiava.R;
import it.polimi.two.weiava.activities.WalkingActivity;

public class DailyNotificationReceiver extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "notification-id";
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
        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setContentTitle("A Medical App")//Title of the app
                .setContentText("It is time to measure walking time.") //TODO: change to String constant
                .setSound(defaultSoundUri)
                .setAutoCancel(true);
        notificationManager.notify(id, builder.build());
        Log.e("AlarmReciever","broadcast Receive.");

    }

}