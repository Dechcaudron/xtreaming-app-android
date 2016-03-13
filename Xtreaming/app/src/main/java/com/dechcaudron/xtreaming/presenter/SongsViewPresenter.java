package com.dechcaudron.xtreaming.presenter;

import android.os.Bundle;

import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.controller.data.DataController;
import com.dechcaudron.xtreaming.model.Song;
import com.dechcaudron.xtreaming.presenter.interfaces.ISongsView;
import com.dechcaudron.xtreaming.utils.IntentUtils;
import com.dechcaudron.xtreaming.view.interfaces.ISongsViewPresenter;

import java.util.List;

public class SongsViewPresenter implements ISongsViewPresenter, DataController.OnSongsAvailableListener
{
    private static final String TAG = LogController.makeTag(SongsViewPresenter.class);

    private final ISongsView view;

    public SongsViewPresenter(ISongsView view, Bundle startingBundle)
    {
        this.view = view;

        checkBundleContent(startingBundle);
    }

    private void checkBundleContent(Bundle startingBundle)
    {
        int repoLocalId = IntentUtils.getSpecifiedRepoLocalId(startingBundle);
        String artistName = IntentUtils.getSpecifiedArtist(startingBundle);
        String albumName = IntentUtils.getSpecifiedAlbum(startingBundle);

        if (artistName != null)
        {
            if (albumName != null)
                fetchSongs(repoLocalId, artistName, albumName);
            else
                fetchSongs(repoLocalId, artistName);
        } else
        {
            if (albumName != null)
                LogController.LOGE(TAG, "Specified album name (" + albumName + ") but did not specify artist. Ignoring both.");

            fetchSongs(repoLocalId);
        }

    }

    private void fetchSongs(int repoLocalId, String artistName, String albumName)
    {
        new DataController().fetchSongs(repoLocalId, artistName, albumName, this);
    }

    private void fetchSongs(int repoLocalId, String artistName)
    {
        throw new RuntimeException("Not yet implemented");
    }

    private void fetchSongs(int repoLocalId)
    {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public void onSongsAvailable(List<Song> songs)
    {
        LogController.LOGD(TAG, "Displaying " + songs.size() + " songs");
        view.displaySongs(songs);
    }

    @Override
    public void onSongsUnavailable(int errorResId)
    {
        view.displayError(errorResId);
    }
}
