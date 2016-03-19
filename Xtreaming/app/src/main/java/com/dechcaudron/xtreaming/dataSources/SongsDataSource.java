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

    public List<Song> getSongs(Repository repository) throws DataSourceException
    {
        RepositoryInterface repoInterface = getRepositoryInterface(repository);
        try
        {
            return repoInterface.getSongs(repository);
        } catch (Exception e)
        {
            LogController.LOGE(TAG, "Could not get songs from repository " + repository, e);
            throw new DataSourceException();
        }
    }

    public List<Song> getSongs(Repository repository, String artistName, String albumName) throws DataSourceException
    {
        RepositoryInterface repoInterface = getRepositoryInterface(repository);
        try
        {
            return repoInterface.getSongs(repository, artistName, albumName);
        } catch (Exception e)
        {
            LogController.LOGE(TAG, "", e);
            throw new ServerDataSourceException();
        }
    }

    public Song getSong(Repository repository, String songId) throws DataSourceException
    {
        List<Song> songs = getSongs(repository);

        for (Song song : songs)
        {
            if (song.getId().equals(songId))
                return song;
        }

        throw new DataSourceException();
    }

    private RepositoryInterface getRepositoryInterface(Repository repository)
    {
        return RepositoryInterfaceFactory.getInterface(RepoTypes.getRepoType(repository.getRepoTypeCode()));
    }
}
