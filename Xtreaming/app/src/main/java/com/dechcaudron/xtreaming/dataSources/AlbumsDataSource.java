package com.dechcaudron.xtreaming.dataSources;

import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.exception.DataSourceException;
import com.dechcaudron.xtreaming.exception.ServerDataSourceException;
import com.dechcaudron.xtreaming.model.Album;
import com.dechcaudron.xtreaming.model.Repository;
import com.dechcaudron.xtreaming.repositoryInterface.RepoTypes;
import com.dechcaudron.xtreaming.repositoryInterface.RepositoryInterface;
import com.dechcaudron.xtreaming.repositoryInterface.RepositoryInterfaceFactory;

import java.util.ArrayList;
import java.util.List;

public class AlbumsDataSource
{
    private static final String TAG = LogController.makeTag(AlbumsDataSource.class);

    public List<Album> getAlbums(Repository repository) throws DataSourceException
    {
        RepositoryInterface repoInterface = RepositoryInterfaceFactory.getInterface(RepoTypes.getRepoType(repository.getRepoTypeCode()));

        try
        {
            return repoInterface.getAlbums(repository);
        } catch (Exception e)
        {
            LogController.LOGE(TAG, "Could not get albums from " + repository.getDomainURL());
            throw new ServerDataSourceException();
        }
    }

    public List<Album> getAlbums(Repository repository, String artistName) throws DataSourceException
    {
        RepositoryInterface repoInterface = RepositoryInterfaceFactory.getInterface(RepoTypes.getRepoType(repository.getRepoTypeCode()));

        try
        {
            return repoInterface.getAlbums(repository, artistName);
        } catch (Exception e)
        {
            LogController.LOGE(TAG, "Could not get albums for " + artistName + " from " + repository.getDomainURL());
            throw new ServerDataSourceException();
        }

    }
}
