package com.dechcaudron.xtreaming.view.presenter;

import com.dechcaudron.xtreaming.controller.DataController;
import com.dechcaudron.xtreaming.model.Artist;
import com.dechcaudron.xtreaming.view.fragment.artistsFragment.IArtistsViewPresenter;

import java.util.List;

public class ArtistsViewPresenter implements IArtistsViewPresenter, DataController.OnArtistsAvailableListener
{
    private final IArtistsView view;

    public ArtistsViewPresenter(IArtistsView view)
    {
        this.view = view;
    }

    @Override
    public void fetchArtists()
    {
        DataController dataController = new DataController();

        dataController.fetchAllArtists(this);
    }

    @Override
    public void onArtistsAvailable(List<Artist> artists)
    {
        view.displayArtists(artists);
    }

    @Override
    public void onArtistsUnavailable(int errorResId)
    {
        view.displayError(errorResId);
    }
}
