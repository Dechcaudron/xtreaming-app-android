package com.dechcaudron.xtreaming.controller;

import com.dechcaudron.xtreaming.R;
import com.dechcaudron.xtreaming.dataSources.ArtistsDataSource;
import com.dechcaudron.xtreaming.dataSources.RepositoriesDataSource;
import com.dechcaudron.xtreaming.exception.DataSourceException;
import com.dechcaudron.xtreaming.exception.ServerDataSourceException;
import com.dechcaudron.xtreaming.model.Artist;
import com.dechcaudron.xtreaming.model.Repository;

import java.util.ArrayList;
import java.util.List;

public class DataController
{
    private static final String TAG = LogController.makeTag(DataController.class);

    private final RepositoriesDataSource repositoriesDataSource;
    private final ArtistsDataSource artistsDataSource;

    public DataController()
    {
        repositoriesDataSource = new RepositoriesDataSource();
        artistsDataSource = new ArtistsDataSource();
    }

    public List<Repository> getLinkedRepositories()
    {
        return repositoriesDataSource.getLinkedRepositories();
    }

    public boolean saveNewRepository(Repository repository)
    {
        return repositoriesDataSource.saveNewRepository(repository);
    }

    public interface OnArtistsAvailableListener
    {
        void onArtistsAvailable(List<Artist> artists);

        void onArtistsUnavailable(int errorResId);

    }

    public void fetchAllArtists(final OnArtistsAvailableListener listener)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    List<Repository> repositories = getLinkedRepositories();
                    List<Artist> artists;

                    artists = artistsDataSource.getArtists(repositories);

                    listener.onArtistsAvailable(artists);
                }catch (DataSourceException e)
                {
                    listener.onArtistsUnavailable(R.string.get_artists_error);
                }
            }
        }).start();
    }
}
