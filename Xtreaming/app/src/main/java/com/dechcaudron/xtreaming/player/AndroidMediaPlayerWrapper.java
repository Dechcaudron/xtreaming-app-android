package com.dechcaudron.xtreaming.player;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.dechcaudron.xtreaming.controller.LogController;

import java.net.URI;

public class AndroidMediaPlayerWrapper implements IMusicPlayer
{
    private final static String TAG = LogController.makeTag(AndroidMediaPlayerWrapper.class);

    private final MediaPlayer mediaPlayer;

    public AndroidMediaPlayerWrapper(Context context)
    {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void queueSong(final URI songURI)
    {
        LogController.LOGI(TAG, "Queueing song from URI " + songURI.toString());
        try
        {
            mediaPlayer.setDataSource(songURI.toString());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e)
        {
            LogController.LOGE(TAG, "Could not queue song", e);
        }
    }
}
