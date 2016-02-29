package com.dechcaudron.xtreaming.view.presenter;

import com.dechcaudron.xtreaming.controller.RepositoryController;
import com.dechcaudron.xtreaming.model.Repository;
import com.dechcaudron.xtreaming.view.fragment.IRepositoriesFragmentPresenter;

import java.util.List;

public class RepositoriesFragmentPresenter implements IRepositoriesFragmentPresenter, RepositoryController.FetchLinkedRepositoriesListener {

    private final IRepositoriesView view;

    public RepositoriesFragmentPresenter(IRepositoriesView view) {
        this.view = view;
    }

    @Override
    public void showAddedRepositories() {
        RepositoryController.fetchLinkedRepositories(this);
    }

    @Override
    public void onLinkedRepositoriesAvailable(List<Repository> repositories) {
        view.displayRepositories(repositories);
    }

    @Override
    public void onFetchLinkedRepositoriesError(int errorResId) {
        view.displayError(errorResId);
    }
}
