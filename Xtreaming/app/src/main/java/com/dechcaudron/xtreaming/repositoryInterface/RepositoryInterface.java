package com.dechcaudron.xtreaming.repositoryInterface;

import com.dechcaudron.xtreaming.model.Artist;
import com.dechcaudron.xtreaming.model.Album;
import com.dechcaudron.xtreaming.model.Repository;

import java.io.IOException;
import java.util.List;

public interface RepositoryInterface
{
    IRepositoryAuthToken attemptLogin(String domain, int port, boolean useHttps, String username, String password) throws RepositoryInterfaceException, IOException;

    List<Artist> getArtists(Repository repository) throws RepositoryInterfaceException, IOException;

    List<Album> getAlbums(Repository repository) throws RepositoryInterfaceException, IOException;

    List<Album> getAlbums(Repository repository, String artistName) throws RepositoryInterfaceException, IOException;
}
