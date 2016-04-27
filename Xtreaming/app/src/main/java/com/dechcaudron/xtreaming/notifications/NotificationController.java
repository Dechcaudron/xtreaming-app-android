package com.dechcaudron.xtreaming.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.dechcaudron.xtreaming.R;
import com.dechcaudron.xtreaming.model.Song;

public abstract class NotificationController
{
    private static final int MUSIC_NOTIFICATION_ID = 1;

    public static void displayMusicPlayNotification(Context context, Song currentSong, boolean musicPaused, boolean nextSongAvailable)
    {
        NotificationManager notificationManager = getNotificationManager(context);

        Intent pausePlayIntent = new Intent(musicPaused ? MediaNotificationIntents.PLAY.getIntentString() : MediaNotificationIntents.PAUSE.getIntentString());
        PendingIntent pausePlayPendingIntent = PendingIntent.getBroadcast(context, 100, pausePlayIntent, 0);

        Intent stopIntent = new Intent(MediaNotificationIntents.STOP.getIntentString());
        PendingIntent stopPendingIntent = PendingIntent.getBroadcast(context, 100, stopIntent, 0);

        NotificationCompat.Builder notificationBuilder = new android.support.v7.app.NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_play_arrow_white_24px)
                .setContentTitle(currentSong.getName())
                .setContentText(currentSong.getArtistName() + " - " + currentSong.getAlbumName())
                .setPriority(Notification.PRIORITY_HIGH)
                .setStyle(new android.support.v7.app.NotificationCompat.MediaStyle());
        //.setOngoing(true);


        if (Build.VERSION.SDK_INT < 23)
        {
            notificationBuilder.addAction(musicPaused ? R.drawable.ic_play_arrow_white_24px : R.drawable.ic_pause_white_24px, musicPaused ? "Play" : "Pause", pausePlayPendingIntent);
            notificationBuilder.addAction(R.drawable.ic_stop_white_24px, "Stop", stopPendingIntent);
        } else
        {
            notificationBuilder.addAction(new NotificationCompat.Action.Builder(musicPaused ? R.drawable.ic_play_arrow_white_24px : R.drawable.ic_pause_white_24px, musicPaused ? "Play" : "Pause", pausePlayPendingIntent).build());
            notificationBuilder.addAction(new NotificationCompat.Action.Builder(R.drawable.ic_stop_white_24px, "Stop", stopPendingIntent).build());
        }

        notificationManager.notify(MUSIC_NOTIFICATION_ID, notificationBuilder.build());
    }

    public static void removeMusicPlayNotification(Context context)
    {
        getNotificationManager(context).cancel(MUSIC_NOTIFICATION_ID);
    }

    public static void removeAllNotifications(Context context)
    {
        getNotificationManager(context).cancelAll();
    }

    private static NotificationManager getNotificationManager(Context context)
    {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
