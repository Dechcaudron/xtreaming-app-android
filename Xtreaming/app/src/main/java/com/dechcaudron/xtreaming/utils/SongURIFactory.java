package com.dechcaudron.xtreaming.utils;

import android.support.annotation.Nullable;

import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.controller.data.DataController;
import com.dechcaudron.xtreaming.model.Repository;
import com.dechcaudron.xtreaming.model.Song;
import com.dechcaudron.xtreaming.repositoryInterface.RepoTypes;
import com.dechcaudron.xtreaming.repositoryInterface.RepositoryInterface;
import com.dechcaudron.xtreaming.repositoryInterface.RepositoryInterfaceFactory;

import java.net.URI;

public abstract class SongURIFactory
{
    private static final String TAG = LogController.makeTag(SongURIFactory.class);

    @Nullable
    public static URI getSongURI(Song song)
    {
        Repository songRepository = DataController.getSingleton().getLinkedRepository(song.getRepoLocalId());
        RepositoryInterface repositoryInterface = RepositoryInterfaceFactory.getInterface(RepoTypes.getRepoType(songRepository.getRepoTypeCode()));

        try
        {
            return repositoryInterface.getSongURI(songRepository, song);
        } catch (Exception e)
        {
            LogController.LOGE(TAG, "Could not get song URI", e);
            return null;
        }
    }
}
