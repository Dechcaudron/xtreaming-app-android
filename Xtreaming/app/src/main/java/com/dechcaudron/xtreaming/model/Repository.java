package com.dechcaudron.xtreaming.model;

public class Repository {

    private final int repoType;
    private final String domainUrl;
    private final String username;
    private final String authenticationToken;

    public Repository(int repoType, String domainUrl, String username, String authenticationToken) {

        this.repoType = repoType;
        this.domainUrl = domainUrl;
        this.username = username;
        this.authenticationToken = authenticationToken;
    }

    public int getRepoType() {
        return repoType;
    }

    public String getURL() {
        return domainUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }
}
