package com.dechcaudron.xtreaming.player;

import com.dechcaudron.xtreaming.model.Song;

public interface IMusicPlayer
{
    boolean queueSong(Song song);

    boolean clearQueue();

    boolean pause();

    boolean play();

    boolean stop();

    Song getCurrentSong();
}
