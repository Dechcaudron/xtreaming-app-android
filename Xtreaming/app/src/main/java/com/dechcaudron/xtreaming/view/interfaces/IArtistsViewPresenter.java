package com.dechcaudron.xtreaming.view.interfaces;

import com.dechcaudron.xtreaming.model.Artist;

public interface IArtistsViewPresenter
{
    void fetchArtists();

    void openArtist(Artist artist);
}
