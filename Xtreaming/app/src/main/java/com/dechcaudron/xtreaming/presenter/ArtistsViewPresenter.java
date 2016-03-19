package com.dechcaudron.xtreaming.presenter;

import android.app.Activity;
import android.content.Intent;

import com.dechcaudron.xtreaming.controller.data.DataController;
import com.dechcaudron.xtreaming.model.Artist;
import com.dechcaudron.xtreaming.presenter.interfaces.IArtistsView;
import com.dechcaudron.xtreaming.utils.IntentUtils;
import com.dechcaudron.xtreaming.view.activity.AlbumsActivity;
import com.dechcaudron.xtreaming.view.interfaces.IArtistsViewPresenter;

import java.util.List;

public class ArtistsViewPresenter implements IArtistsViewPresenter, DataController.OnArtistsAvailableListener
{
    private final IArtistsView view;
    private final Activity activity;

    public ArtistsViewPresenter(IArtistsView view, Activity activity)
    {
        this.view = view;
        this.activity = activity;
    }

    @Override
    public void fetchArtists()
    {
        DataController.getSingleton().fetchAllArtists(this);
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

    @Override
    public void openArtist(Artist artist)
    {
        Intent intent = new Intent(activity, AlbumsActivity.class);
        IntentUtils.specifyArtist(intent, artist.getName());
        IntentUtils.specifyRepoLocalId(intent, artist.getRepoLocalId());

        activity.startActivity(intent);
    }
}
