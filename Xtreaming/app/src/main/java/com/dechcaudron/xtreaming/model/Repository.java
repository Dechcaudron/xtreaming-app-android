package com.dechcaudron.xtreaming.model;

public class Repository
{

    private final int repoType;
    private final String domainUrl;
    private final int port;
    private final boolean requireSSL;
    private final String username;
    private final String authenticationToken;

    public Repository(int repoType, String domainUrl, int port, boolean requireSSL, String username, String authenticationToken)
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

    public String getAuthenticationToken()
    {
        return authenticationToken;
    }
}
