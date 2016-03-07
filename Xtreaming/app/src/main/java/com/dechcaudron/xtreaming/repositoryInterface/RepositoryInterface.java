package com.dechcaudron.xtreaming.repositoryInterface;

import com.dechcaudron.xtreaming.model.Artist;

import java.io.IOException;
import java.util.List;

public interface RepositoryInterface
{
    IRepositoryAuthToken attemptLogin(String domain, int port, boolean useHttps, String username, String password) throws RepositoryInterfaceException, IOException;

    List<Artist> getArtists(String domain, int port, boolean useHttps, IRepositoryAuthToken authToken) throws RepositoryInterfaceException, IOException;
}
