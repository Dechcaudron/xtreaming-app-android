package com.dechcaudron.xtreaming.repositoryInterface.koel;


import android.support.annotation.Nullable;

import com.dechcaudron.koel.api.exceptions.KoelApiException;
import com.dechcaudron.koel.api.exceptions.KoelAuthenticationException;
import com.dechcaudron.koel.api.objects.KoelAuthenticationToken;
import com.dechcaudron.koel.api.objects.MediaInfo;
import com.dechcaudron.koel.api.utils.AuthenticationUtils;
import com.dechcaudron.koel.api.utils.MediaUtils;
import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.model.Album;
import com.dechcaudron.xtreaming.model.Artist;
import com.dechcaudron.xtreaming.model.Repository;
import com.dechcaudron.xtreaming.model.Song;
import com.dechcaudron.xtreaming.repositoryInterface.IRepositoryAuthToken;
import com.dechcaudron.xtreaming.repositoryInterface.RepositoryInterface;
import com.dechcaudron.xtreaming.repositoryInterface.RepositoryInterfaceException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class KoelRepositoryInterface implements RepositoryInterface
{
    private static final String TAG = LogController.makeTag(KoelRepositoryInterface.class);

    @Nullable
    @Override
    public IRepositoryAuthToken attemptLogin(String domain, int port, boolean useHttps, String username, String password) throws RepositoryInterfaceException, IOException
    {
        URL serverURL = null;
        try
        {
            serverURL = buildURL(domain, port, useHttps);
            return new KoelAuthTokenWrapper(AuthenticationUtils.login(serverURL, username, password));
        } catch (MalformedURLException e)
        {
            LogController.LOGE(TAG, "Malformed URL for domain " + domain, e);
        } catch (KoelAuthenticationException e)
        {
            LogController.LOGI(TAG, "Invalid login credentials (" + username + "," + password + ") for " + serverURL.toString(), e);
            return null;
        } catch (KoelApiException e)
        {
            LogController.LOGE(TAG, "API exception during login", e);

        }

        throw new RepositoryInterfaceException();
    }

    @Override
    public List<Artist> getArtists(Repository repository) throws RepositoryInterfaceException, IOException
    {
        List<com.dechcaudron.koel.api.objects.Artist> apiArtists = getMediaInfo(repository).getArtists();

        List<Artist> artists = new ArrayList<>(apiArtists.size());

        for (com.dechcaudron.koel.api.objects.Artist apiArtist : apiArtists)
        {
            artists.add(new Artist(apiArtist.getName(), repository.getRepoLocalId()));
        }

        return artists;
    }

    @Override
    public List<Album> getAlbums(Repository repository) throws RepositoryInterfaceException, IOException
    {
        List<com.dechcaudron.koel.api.objects.Album> apiAlbums = getMediaInfo(repository).getAllAlbums();

        List<Album> albums = new ArrayList<>(apiAlbums.size());

        for (com.dechcaudron.koel.api.objects.Album apiAlbum : apiAlbums)
        {
            albums.add(new Album(repository.getRepoLocalId(), apiAlbum.getArtistName(), apiAlbum.getName()));
        }

        return albums;
    }

    @Override
    public List<Album> getAlbums(Repository repository, String artistName) throws RepositoryInterfaceException, IOException
    {
        List<Album> repoAlbums = getAlbums(repository);

        List<Album> artistAlbums = new ArrayList<>();

        for (Album album : repoAlbums)
        {
            if (album.getArtistName().equals(artistName))
                artistAlbums.add(album);
        }

        return artistAlbums;
    }

    @Override
    public List<Song> getSongs(Repository repository, String artistName, String albumName) throws RepositoryInterfaceException, IOException
    {
        List<Song> allSongs = getSongs(repository);

        List<Song> albumSongs = new ArrayList<>();

        for (Song song : allSongs)
        {
            if (song.getAlbumName().equals(albumName) && song.getArtistName().equals(artistName))
                albumSongs.add(song);
        }

        return albumSongs;
    }

    @Override
    public List<Song> getSongs(Repository repository, String artistName) throws RepositoryInterfaceException, IOException
    {
        List<Song> allSongs = getSongs(repository);

        List<Song> artistSongs = new ArrayList<>();

        for (Song song : allSongs)
        {
            if (song.getArtistName().equals(artistName))
                artistSongs.add(song);
        }

        return artistSongs;
    }

    @Override
    public List<Song> getSongs(Repository repository) throws RepositoryInterfaceException, IOException
    {
        List<com.dechcaudron.koel.api.objects.Song> apiSongs = getMediaInfo(repository).getAllSongs();
        
        List<Song> allSongs = new ArrayList<>(apiSongs.size());

        for (com.dechcaudron.koel.api.objects.Song apiSong : apiSongs)
        {
            allSongs.add(new Song(repository.getRepoLocalId(), apiSong.getArtistName(), apiSong.getAlbumName(), apiSong.getTitle(), apiSong.getId()));
        }

        return allSongs;
    }

    private MediaInfo getMediaInfo(Repository repository) throws IOException, RepositoryInterfaceException
    {
        try
        {
            URL serverURL = buildURL(repository.getDomainURL(), repository.getPort(), repository.requiresSSL());
            return MediaUtils.getMediaInfo(serverURL, new KoelAuthenticationToken(repository.getAuthenticationToken().getSerialized()));

        } catch (MalformedURLException e)
        {
            LogController.LOGE(TAG, "Malformed URL for domain " + repository.getDomainURL(), e);

        } catch (KoelApiException e)
        {
            LogController.LOGE(TAG, "API exception", e);
        }

        throw new RepositoryInterfaceException("Could not get media info");
    }

    private URL buildURL(String domain, int port, boolean useHttps) throws MalformedURLException
    {
        return new URL((useHttps ? "https://" : "http://") + domain + ":" + Integer.toString(port));
    }
}
