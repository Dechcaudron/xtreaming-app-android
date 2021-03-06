package com.dechcaudron.xtreaming.presenter.interfaces;

import com.dechcaudron.xtreaming.model.Artist;

import java.util.List;

public interface IArtistsView
{
    void displayArtists(List<Artist> artists);

    void displayError(int errorResId);
}
