package com.dechcaudron.xtreaming.dataSources;

import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.exception.DataSourceException;
import com.dechcaudron.xtreaming.exception.ServerDataSourceException;
import com.dechcaudron.xtreaming.model.Artist;
import com.dechcaudron.xtreaming.model.Repository;
import com.dechcaudron.xtreaming.repositoryInterface.RepoTypes;
import com.dechcaudron.xtreaming.repositoryInterface.RepositoryInterface;
import com.dechcaudron.xtreaming.repositoryInterface.RepositoryInterfaceFactory;

import java.util.ArrayList;
import java.util.List;

public class ArtistsDataSource
{
    private static final String TAG = LogController.makeTag(ArtistsDataSource.class);

    public List<Artist> getArtists(List<Repository> repositories) throws DataSourceException
    {
        List<Artist> artists = new ArrayList<>();

        for (Repository repository : repositories)
        {
            artists.addAll(getArtists(repository));
        }

        return artists;
    }

    public List<Artist> getArtists(Repository repository) throws DataSourceException
    {
        LogController.LOGI(TAG, "Fetching artists from " + repository.getDomainURL());
        return getArtistsFromInternet(repository);
    }

    private List<Artist> getArtistsFromInternet(Repository repository) throws ServerDataSourceException
    {
        try
        {
            RepositoryInterface repositoryInterface = RepositoryInterfaceFactory.getInterface(RepoTypes.getRepoType(repository.getRepoTypeCode()));
            return repositoryInterface.getArtists(repository);

        } catch (Exception e)
        {
            LogController.LOGE(TAG, "Could not get artists from server", e);
            throw new ServerDataSourceException();
        }
    }
}
