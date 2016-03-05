package com.dechcaudron.xtreaming.controller;

import com.dechcaudron.xtreaming.dataSources.RepositoriesDataSource;
import com.dechcaudron.xtreaming.model.Repository;

import java.util.List;

public class DataController
{
    private static final String TAG = LogController.makeTag(DataController.class);

    private RepositoriesDataSource repositoriesDataSource;

    public DataController()
    {
        repositoriesDataSource = new RepositoriesDataSource();
    }

    public List<Repository> getLinkedRepositories()
    {
        return repositoriesDataSource.getLinkedRepositories();
    }

    public boolean saveNewRepository(Repository repository)
    {
        return repositoriesDataSource.saveNewRepository(repository);
    }
}
