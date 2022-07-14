package com.example.mp3app.activity;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mp3app.R;
import com.example.mp3app.model.BaiHat;
import com.example.mp3app.service.NotificationActionService;

public class CreateNotification {

    public static final String CHANNEL_ID = "channel1";

    public static final String ACTION_PREVIUOS = "actionprevious";
    public static final String ACTION_PLAY = "actionplay";
    public static final String ACTION_NEXT = "actionnext";

    public static Notification notification;

    //    public static ArrayList<BaiHat> baiHatArrayList = new ArrayList<>();
    public static void createNotification(Context context, BaiHat track, int playbutton, int pos, int size) {
//        baiHatArrayList = PlayNhacActivity.listSong;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");

            Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.bgapps);

            PendingIntent pendingIntentPrevious;
            int drw_previous;
            if (pos == 0) {
                pendingIntentPrevious = null;
                drw_previous = 0;
            } else {
                Intent intentPrevious = new Intent(context, NotificationActionService.class)
                        .setAction(ACTION_PREVIUOS);
                pendingIntentPrevious = PendingIntent.getBroadcast(context, 0,
                        intentPrevious, PendingIntent.FLAG_UPDATE_CURRENT);
                drw_previous = R.drawable.ic_skip_previous_black_24dp;
            }

            Intent intentPlay = new Intent(context, NotificationActionService.class)
                    .setAction(ACTION_PLAY);
            PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context, 0,
                    intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);

            PendingIntent pendingIntentNext;
            int drw_next;
            if (pos == size) {
                pendingIntentNext = null;
                drw_next = 0;
            } else {
                Intent intentNext = new Intent(context, NotificationActionService.class)
                        .setAction(ACTION_NEXT);
                pendingIntentNext = PendingIntent.getBroadcast(context, 0,
                        intentNext, PendingIntent.FLAG_UPDATE_CURRENT);
                drw_next = R.drawable.ic_skip_next_black_24dp;
            }

            //create notification
            notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_music_note)
                    .setContentTitle(track.getTenBaiHat())
                    .setContentText(track.getCaSi())
                    .setLargeIcon(icon)
                    .setOnlyAlertOnce(true)//show notification for only first time
                    .setShowWhen(true)
                    .addAction(drw_previous, "Previous", pendingIntentPrevious)
                    .addAction(playbutton, "Play", pendingIntentPlay)
                    .addAction(drw_next, "Next", pendingIntentNext)
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(0, 1, 2)
                            .setMediaSession(mediaSessionCompat.getSessionToken()))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build();
//            Intent intent = new Intent(context, PlayNhacActivity.class);
//            intent.putExtra("listsong", PlayNhacActivity.listSong);
//            PendingIntent i=PendingIntent.getActivity(context, 0,
//                   intent,
//                    0);
//            intent.setFlags(Intent.F);
//            Intent notifyIntent = new Intent(context, PlayNhacActivity.class);
//// Set the Activity to start in a new, empty task
//            notifyIntent.putExtra("listsong", baiHatArrayList);
////            notifyIntent.putExtra("vitri", );
//            notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//// Create the PendingIntent
//            PendingIntent notifyPendingIntent = PendingIntent.getActivity(
//                    context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
//            );
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
//            builder.setContentIntent(notifyPendingIntent);
//            notification.contentIntent = notifyPendingIntent;
            notificationManagerCompat.notify(1, notification);
        }
    }


}
