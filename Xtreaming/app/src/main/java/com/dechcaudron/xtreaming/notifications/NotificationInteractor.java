package com.dechcaudron.xtreaming.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.controller.MediaPlayController;

public class NotificationInteractor extends BroadcastReceiver
{
    private static final String TAG = LogController.makeTag(NotificationInteractor.class);

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();

        if (action.equals(MediaNotificationIntents.PAUSE.getIntentString()))
        {
            MediaPlayController.pauseMedia();
        } else if (action.equals(MediaNotificationIntents.PLAY.getIntentString()))
        {
            MediaPlayController.playMedia();
        } else if (action.equals(MediaNotificationIntents.STOP.getIntentString()))
        {
            MediaPlayController.stopMedia();
        }
    }
}
