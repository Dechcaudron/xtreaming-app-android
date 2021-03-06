package com.dechcaudron.xtreaming.presenter.interfaces;

import com.dechcaudron.xtreaming.model.Repository;

import java.util.List;

public interface IRepositoriesView
{
    void displayRepositories(List<Repository> repositories);

    void displayError(int errorResId);
}
