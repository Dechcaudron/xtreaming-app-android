package com.dechcaudron.xtreaming.model;

import com.dechcaudron.xtreaming.repositoryInterface.IRepositoryAuthToken;

public class Repository
{
    private final int repoLocalId;
    private final int repoTypeCode;
    private final String domainURL;
    private final int port;
    private final boolean requireSSL;
    private final String username;
    private final IRepositoryAuthToken authenticationToken;

    public Repository(int repoLocalId, int repoTypeCode, String domainURL, int port, boolean requireSSL, String username, IRepositoryAuthToken authenticationToken)
    {
        this.repoLocalId = repoLocalId;
        this.repoTypeCode = repoTypeCode;
        this.domainURL = domainURL;
        this.port = port;
        this.requireSSL = requireSSL;
        this.username = username;
        this.authenticationToken = authenticationToken;
    }

    public int getRepoLocalId()
    {
        return repoLocalId;
    }

    public int getRepoTypeCode()
    {
        return repoTypeCode;
    }

    public String getDomainURL()
    {
        return domainURL;
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

    @Override
    public String toString()
    {
        return "[LocalId " + repoLocalId + "] [RepoType " + repoTypeCode + "] " + domainURL + ":" + port +" "+ (requireSSL ? "" : "NO ") + "SSL Username: " + username;
    }
}
