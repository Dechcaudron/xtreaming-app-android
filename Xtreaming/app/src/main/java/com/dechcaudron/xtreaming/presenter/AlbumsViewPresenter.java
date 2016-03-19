package com.dechcaudron.xtreaming.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.controller.data.DataController;
import com.dechcaudron.xtreaming.model.Album;
import com.dechcaudron.xtreaming.presenter.interfaces.IAlbumsView;
import com.dechcaudron.xtreaming.utils.IntentUtils;
import com.dechcaudron.xtreaming.view.activity.SongsActivity;
import com.dechcaudron.xtreaming.view.interfaces.IAlbumsViewPresenter;

import java.util.List;

public class AlbumsViewPresenter implements IAlbumsViewPresenter, DataController.OnAlbumsAvailableListener
{
    private static final String TAG = LogController.makeTag(AlbumsViewPresenter.class);

    private final IAlbumsView view;
    private final DataController dataController;
    private final Activity parentActivity;

    public AlbumsViewPresenter(IAlbumsView view, Bundle startingBundle, Activity parentActivity)
    {
        this.view = view;

        dataController = DataController.getSingleton();
        this.parentActivity = parentActivity;

        checkBundleContents(startingBundle);
    }

    protected void checkBundleContents(Bundle bundle)
    {
        LogController.LOGD(TAG, "Checking bundle contents");

        String specifiedArtist = IntentUtils.getSpecifiedArtist(bundle);

        if (specifiedArtist != null)
        {
            fetchAlbums(IntentUtils.getSpecifiedRepoLocalId(bundle), specifiedArtist);
        } else
        {
            fetchAlbums(IntentUtils.getSpecifiedRepoLocalId(bundle));
        }
    }

    protected void fetchAlbums(int repoLocalId, String artistName)
    {
        dataController.fetchAlbums(repoLocalId, artistName, this);
    }

    protected void fetchAlbums(int repoLocalId)
    {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public void onAlbumsAvailable(List<Album> albums)
    {
        view.displayAlbums(albums);
    }

    @Override
    public void onAlbumsUnavailable(int errorResId)
    {
        view.displayError(errorResId);
    }

    @Override
    public void openAlbum(Album album)
    {
        Intent intent = new Intent(parentActivity, SongsActivity.class);
        IntentUtils.specifyArtist(intent, album.getArtistName());
        IntentUtils.specifyRepoLocalId(intent, album.getRepoLocalId());
        IntentUtils.specifyAlbum(intent, album.getName());

        parentActivity.startActivity(intent);
    }
}
