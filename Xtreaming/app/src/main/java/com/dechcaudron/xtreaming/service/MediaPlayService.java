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
import com.dechcaudron.xtreaming.player.AndroidMediaPlayerWrapper;
import com.dechcaudron.xtreaming.player.IMusicPlayer;
import com.dechcaudron.xtreaming.utils.IntentUtils;
import com.dechcaudron.xtreaming.utils.SongURIFactory;

public class MediaPlayService extends Service
{
    private static final String TAG = LogController.makeTag(MediaPlayService.class);

    private IMusicPlayer musicPlayer;

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
                Bundle extras = intent.getExtras();

                int repoLocalId = IntentUtils.getSpecifiedRepoLocalId(extras);
                String songId = IntentUtils.getSpecifiedSongId(extras);

                if (songId != null)
                {
                    queueSong(repoLocalId, songId);
                }
            }
        }).start();
    }

    private void queueSong(int repoLocalId, String songId)
    {
        try
        {
            Song song = DataController.getSingleton().getSong(repoLocalId, songId);
            getMusicPlayer().queueSong(SongURIFactory.getSongURI(song));

        } catch (DataSourceException e)
        {
            LogController.LOGE(TAG, "Could not get song info");
        }
    }

    private IMusicPlayer getMusicPlayer()
    {
        if (musicPlayer == null)
            musicPlayer = new AndroidMediaPlayerWrapper(this);

        return musicPlayer;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
