package com.dechcaudron.xtreaming.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.controller.data.DataController;
import com.dechcaudron.xtreaming.exception.DataSourceException;
import com.dechcaudron.xtreaming.model.Song;
import com.dechcaudron.xtreaming.notifications.NotificationController;
import com.dechcaudron.xtreaming.player.AndroidMediaPlayerWrapper;
import com.dechcaudron.xtreaming.player.IMusicPlayer;
import com.dechcaudron.xtreaming.utils.IntentUtils;

public class MediaPlayService extends Service
{
    private static final String TAG = LogController.makeTag(MediaPlayService.class);

    private IMusicPlayer musicPlayer;

    public final static String ACTION_IK = "ACTION_IK";

    public enum Actions
    {
        QUEUE("QUEUE"),
        PAUSE("PAUSE"),
        PLAY("PLAY"),
        STOP("STOP");

        private final String action;

        Actions(String action)
        {
            this.action = action;
        }

        public String getAction()
        {
            return action;
        }
    }

    private enum MediaStatus
    {
        IDLE,
        PAUSED,
        PLAYING,
        STOPPED
    }

    private MediaStatus currentMediaStatus = MediaStatus.IDLE;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        processCommand(intent);

        return START_NOT_STICKY;
    }

    protected void processCommand(final Intent intent)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                String action = intent.getStringExtra(ACTION_IK);

                if (action == null)
                {
                    LogController.LOGW(TAG, "No action specified in intent for the service. Ignoring...");
                    return;
                }

                if (action.equals(Actions.QUEUE.getAction()))
                {

                    Bundle extras = intent.getExtras();

                    int repoLocalId = IntentUtils.getSpecifiedRepoLocalId(extras);
                    String songId = IntentUtils.getSpecifiedSongId(extras);

                    if (songId != null)
                    {
                        queueSong(repoLocalId, songId);

                        if (currentMediaStatus == MediaStatus.IDLE)
                            playMedia();
                    }
                } else if (action.equals(Actions.PAUSE.getAction()))
                {
                    pauseMedia();
                } else if (action.equals(Actions.PLAY.getAction()))
                {
                    playMedia();
                } else if (action.equals(Actions.STOP.getAction()))
                {
                    stopMedia();
                    stopSelf();
                } else
                {
                    LogController.LOGW(TAG, "Received an unhandled action: " + action);
                }
            }
        }).start();
    }

    private void queueSong(int repoLocalId, String songId)
    {
        try
        {
            Song song = DataController.getSingleton().getSong(repoLocalId, songId);
            getMusicPlayer().queueSong(song);

        } catch (DataSourceException e)
        {
            LogController.LOGE(TAG, "Could not get song info");
        }
    }

    private void pauseMedia()
    {
        if (currentMediaStatus == MediaStatus.PAUSED)
        {
            LogController.LOGD(TAG, "called pauseMedia but currentMediaStatus was PAUSED already. Ignoring call...");
            return;
        }

        if (getMusicPlayer().pause())
            setMediaStatus(MediaStatus.PAUSED);
    }

    private void playMedia()
    {
        if (currentMediaStatus == MediaStatus.PLAYING)
        {
            LogController.LOGD(TAG, "called playMedia but currentMediaStatus was PLAYING already. Ignoring call...");
            return;
        }

        if (getMusicPlayer().play())
            setMediaStatus(MediaStatus.PLAYING);
    }

    private void stopMedia()
    {
        LogController.LOGD(TAG, "Stopping media");
        if (currentMediaStatus == MediaStatus.STOPPED)
        {
            LogController.LOGD(TAG, "called stopMedia but currentMediaStatus was STOPPED already. Ignoring call...");
            return;
        }

        if (getMusicPlayer().stop())
            setMediaStatus(MediaStatus.STOPPED);
    }

    private IMusicPlayer getMusicPlayer()
    {
        if (musicPlayer == null)
            musicPlayer = new AndroidMediaPlayerWrapper(this);

        return musicPlayer;
    }

    private void setMediaStatus(MediaStatus status)
    {
        currentMediaStatus = status;

        switch (status)
        {
            case PAUSED:
                NotificationController.displayMusicPlayNotification(this, getMusicPlayer().getCurrentSong(), true, false);
                break;

            case PLAYING:
                NotificationController.displayMusicPlayNotification(this, getMusicPlayer().getCurrentSong(), false, false);
                break;

            case STOPPED:
                NotificationController.removeMusicPlayNotification(this);
                break;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
