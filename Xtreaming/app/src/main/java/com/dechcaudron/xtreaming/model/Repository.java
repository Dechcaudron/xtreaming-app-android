package com.dechcaudron.xtreaming.model;

import com.dechcaudron.xtreaming.repositoryInterface.IRepositoryAuthToken;

public class Repository
{
    private final int repoType;
    private final String domainUrl;
    private final int port;
    private final boolean requireSSL;
    private final String username;
    private final IRepositoryAuthToken authenticationToken;

    public Repository(int repoType, String domainUrl, int port, boolean requireSSL, String username, IRepositoryAuthToken authenticationToken)
    {

        this.repoType = repoType;
        this.domainUrl = domainUrl;
        this.port = port;
        this.requireSSL = requireSSL;
        this.username = username;
        this.authenticationToken = authenticationToken;
    }

    public int getRepoType()
    {
        return repoType;
    }

    public String getDomainURL()
    {
        return domainUrl;
    }

    public int getPort()
    {
        return port;
    }

    public boolean requiresSSL()
    {
        return requireSSL;
    }

    public String getUsername()
    {
        return username;
    }

    public IRepositoryAuthToken getAuthenticationToken()
    {
        return authenticationToken;
    }
}
