package com.dechcaudron.xtreaming.player;

import com.dechcaudron.xtreaming.model.Song;

import java.net.URI;

class PlayableSong
{
    private final Song song;
    private final URI songUri;

    public PlayableSong(Song song, URI songUri)
    {
        this.song = song;
        this.songUri = songUri;
    }

    /*
        GETTERS
     */

    public Song getSong()
    {
        return song;
    }

    public URI getSongUri()
    {
        return songUri;
    }
}
