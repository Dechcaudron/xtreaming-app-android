package com.dechcaudron.xtreaming.presenter.interfaces;

import com.dechcaudron.xtreaming.model.Album;

import java.util.List;

public interface IAlbumsView
{
    void displayAlbums(List<Album> albums);

    void displayError(int errorResId);
}
