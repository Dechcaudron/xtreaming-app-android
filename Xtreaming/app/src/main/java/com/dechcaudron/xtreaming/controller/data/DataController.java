package com.dechcaudron.xtreaming.controller.data;

import com.dechcaudron.xtreaming.R;
import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.dataSources.AlbumsDataSource;
import com.dechcaudron.xtreaming.dataSources.ArtistsDataSource;
import com.dechcaudron.xtreaming.dataSources.RepositoriesDataSource;
import com.dechcaudron.xtreaming.exception.DataSourceException;
import com.dechcaudron.xtreaming.model.Album;
import com.dechcaudron.xtreaming.model.Artist;
import com.dechcaudron.xtreaming.model.Repository;

import java.util.List;

public class DataController
{
    private static final String TAG = LogController.makeTag(DataController.class);

    private final RepositoriesDataSource repositoriesDataSource;
    private final ArtistsDataSource artistsDataSource;
    private final AlbumsDataSource albumsDataSource;

    public DataController()
    {
        repositoriesDataSource = new RepositoriesDataSource();
        artistsDataSource = new ArtistsDataSource();
        albumsDataSource = new AlbumsDataSource();
    }

    public List<Repository> getLinkedRepositories()
    {
        return repositoriesDataSource.getLinkedRepositories();
    }

    public Repository getLinkedRepository(int repoLocalId)
    {
        List<Repository> linkedRepositories = getLinkedRepositories();

        for (Repository repo : linkedRepositories)
        {
            if (repo.getRepoLocalId() == repoLocalId)
                return repo;
        }

        return null;
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
                } catch (DataSourceException e)
                {
                    listener.onArtistsUnavailable(R.string.get_artists_error);
                }
            }
        }).start();
    }

    public interface OnAlbumsAvailableListener
    {
        void onAlbumsAvailable(List<Album> albums);

        void onAlbumsUnavailable(int errorResId);
    }

    public void fetchAlbums(final int repoLocalId, final String artistName, final OnAlbumsAvailableListener listener)
    {
        LogController.LOGD(TAG, "Fetching albums in repo of id " + repoLocalId + " for artist " + artistName);
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    listener.onAlbumsAvailable(albumsDataSource.getAlbums(getLinkedRepository(repoLocalId), artistName));
                } catch (DataSourceException e)
                {
                    LogController.LOGE(TAG, "Could not fetch albums", e);
                    listener.onAlbumsUnavailable(R.string.get_albums_error);
                }
            }
        }).start();
    }
}
