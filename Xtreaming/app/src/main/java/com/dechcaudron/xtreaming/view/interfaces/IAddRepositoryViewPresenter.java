package com.dechcaudron.xtreaming.view.interfaces;

public interface IAddRepositoryViewPresenter
{
    void addRepository(int repoType, String domain, int port, boolean useHttps, String username, String password);
}
