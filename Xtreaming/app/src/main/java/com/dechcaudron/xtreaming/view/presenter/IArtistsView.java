package com.dechcaudron.xtreaming.view.presenter;

import com.dechcaudron.xtreaming.model.Artist;

import java.util.List;

public interface IArtistsView
{
    void displayArtists(List<Artist> artists);

    void displayError(int errorResId);
}
