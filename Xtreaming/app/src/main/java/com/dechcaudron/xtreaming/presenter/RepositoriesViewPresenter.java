package com.dechcaudron.xtreaming.presenter;

import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.controller.RepositoryController;
import com.dechcaudron.xtreaming.model.Repository;
import com.dechcaudron.xtreaming.presenter.interfaces.IRepositoriesView;
import com.dechcaudron.xtreaming.view.interfaces.IRepositoriesViewPresenter;

import java.util.List;

public class RepositoriesViewPresenter implements IRepositoriesViewPresenter, RepositoryController.FetchLinkedRepositoriesListener
{
    private static final String TAG = LogController.makeTag(RepositoriesViewPresenter.class);

    private final IRepositoriesView view;

    public RepositoriesViewPresenter(IRepositoriesView view)
    {
        this.view = view;
    }

    @Override
    public void onResume()
    {
        fetchLinkedRepositories();
    }

    @Override
    public void onLinkedRepositoriesAvailable(List<Repository> repositories)
    {
        view.displayRepositories(repositories);
    }

    @Override
    public void onFetchLinkedRepositoriesError(int errorResId)
    {
        view.displayError(errorResId);
    }

    private void fetchLinkedRepositories()
    {
        RepositoryController.fetchLinkedRepositories(this);
    }

}
