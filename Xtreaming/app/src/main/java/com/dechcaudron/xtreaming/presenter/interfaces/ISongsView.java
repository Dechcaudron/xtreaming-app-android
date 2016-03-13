package com.dechcaudron.xtreaming.presenter.interfaces;

import com.dechcaudron.xtreaming.model.Song;

import java.util.List;

public interface ISongsView
{
    void displaySongs(List<Song> songs);

    void displayError(int errorResId);
}
