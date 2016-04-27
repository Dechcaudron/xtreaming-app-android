package com.dechcaudron.xtreaming.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.model.Song;
import com.dechcaudron.xtreaming.utils.SongURIFactory;

import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;
import java.util.Queue;

public class AndroidMediaPlayerWrapper implements IMusicPlayer
{
    private final static String TAG = LogController.makeTag(AndroidMediaPlayerWrapper.class);

    private final Context context;

    private final Queue<PlayableSong> playableSongQueue;
    private PlayableSong currentPlayableSong;

    private MediaPlayer currentMediaPlayer;
    private MediaPlayer nextMediaPlayer;

    private Status currentStatus;

    private enum Status
    {
        IDLE,
        PLAYING,
        PAUSED
    }

    public AndroidMediaPlayerWrapper(Context context)
    {
        this.context = context;
        playableSongQueue = new LinkedList<>();
        currentStatus = Status.IDLE;
    }

    @Override
    public boolean queueSong(Song song)
    {
        URI songURI = SongURIFactory.getSongURI(song);
        PlayableSong playableSong = new PlayableSong(song, songURI);

        synchronized (playableSongQueue)
        {
            playableSongQueue.add(playableSong);
        }

        return true;
    }

    @Override
    public boolean pause()
    {
        if (currentStatus == Status.PLAYING)
        {
            currentMediaPlayer.pause();
            currentStatus = Status.PAUSED;
            return true;
        } else
            LogController.LOGW(TAG, "Called pause but it was not playing. Ignoring.");

        return false;
    }

    @Override
    public boolean play()
    {

        if (currentStatus == Status.PLAYING)
        {
            LogController.LOGW(TAG, "play() was called but already playing. Ignoring.");

        } else if (currentStatus == Status.PAUSED)
        {
            currentMediaPlayer.start();
            currentStatus = Status.PLAYING;
            return true;
        } else if (currentStatus == Status.IDLE)
        {
            try
            {
                playNextSong();
                currentStatus = Status.PLAYING;
                return true;
            } catch (IOException e)
            {
                LogController.LOGE(TAG, "Could not play next song", e);
            }
        }

        return false;
    }

    @Override
    public boolean clearQueue()
    {
        return true;
    }

    @Override
    public boolean stop()
    {
        currentMediaPlayer.stop();
        releaseCurrentMediaPlayer();
        return true;
    }

    @Override
    public Song getCurrentSong()
    {
        return currentPlayableSong.getSong();
    }

    private void playNextSong() throws IOException
    {
        PlayableSong nextSong;

        synchronized (playableSongQueue)
        {
            nextSong = playableSongQueue.poll();
        }

        if (nextSong != null)
        {
            currentMediaPlayer = getNextMediaPlayer();
            currentMediaPlayer.setDataSource(context, Uri.parse(nextSong.getSongUri().toString()));
            currentMediaPlayer.prepare();
            currentMediaPlayer.start();
            currentPlayableSong = nextSong;

        } else
        {
            LogController.LOGD(TAG, "Song queue is now empty, stopping " + this);
            stop();
        }
    }

    private void releaseCurrentMediaPlayer()
    {
        currentMediaPlayer.release();
    }

    private MediaPlayer getNextMediaPlayer()
    {
        if (nextMediaPlayer == null)
        {
            return new MediaPlayer();
        } else
        {
            MediaPlayer temp = nextMediaPlayer;
            nextMediaPlayer = null;
            return temp;
        }
    }
}
