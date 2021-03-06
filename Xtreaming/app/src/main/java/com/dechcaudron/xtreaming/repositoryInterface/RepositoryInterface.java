package com.dechcaudron.xtreaming.repositoryInterface;

import com.dechcaudron.xtreaming.model.Album;
import com.dechcaudron.xtreaming.model.Artist;
import com.dechcaudron.xtreaming.model.Repository;
import com.dechcaudron.xtreaming.model.Song;

import java.io.IOException;
import java.net.URI;
import java.util.List;

public interface RepositoryInterface
{
    IRepositoryAuthToken attemptLogin(String domain, int port, boolean useHttps, String username, String password) throws RepositoryInterfaceException, IOException;

    List<Artist> getArtists(Repository repository) throws RepositoryInterfaceException, IOException;

    List<Album> getAlbums(Repository repository) throws RepositoryInterfaceException, IOException;

    List<Album> getAlbums(Repository repository, String artistName) throws RepositoryInterfaceException, IOException;

    List<Song> getSongs(Repository repository, String artistName, String albumName) throws RepositoryInterfaceException, IOException;

    List<Song> getSongs(Repository repository, String artistName) throws RepositoryInterfaceException, IOException;

    List<Song> getSongs(Repository repository) throws RepositoryInterfaceException, IOException;

    URI getSongURI(Repository repository, Song song) throws RepositoryInterfaceException;
}
