package com.dechcaudron.xtreaming.dataSources;

import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.exception.DataSourceException;
import com.dechcaudron.xtreaming.exception.ServerDataSourceException;
import com.dechcaudron.xtreaming.model.Repository;
import com.dechcaudron.xtreaming.model.Song;
import com.dechcaudron.xtreaming.repositoryInterface.RepoTypes;
import com.dechcaudron.xtreaming.repositoryInterface.RepositoryInterface;
import com.dechcaudron.xtreaming.repositoryInterface.RepositoryInterfaceFactory;

import java.util.List;

public class SongsDataSource
{
    private static final String TAG = LogController.makeTag(SongsDataSource.class);

    public SongsDataSource()
    {

    }

    public List<Song> getSongs(Repository repository, String artistName, String albumName) throws DataSourceException
    {
        RepositoryInterface repoInterface = RepositoryInterfaceFactory.getInterface(RepoTypes.getRepoType(repository.getRepoTypeCode()));
        try
        {
            return repoInterface.getSongs(repository, artistName, albumName);
        } catch (Exception e)
        {
            LogController.LOGE(TAG, "", e);
            throw new ServerDataSourceException();
        }
    }
}
