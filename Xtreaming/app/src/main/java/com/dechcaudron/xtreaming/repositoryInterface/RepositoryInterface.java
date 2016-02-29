package com.dechcaudron.xtreaming.repositoryInterface;

public interface RepositoryInterface
{
    IRepositoryAuthToken attemptLogin(String domain, int port, boolean useHttps, String username, String password) throws RepositoryInterfaceException;
}
