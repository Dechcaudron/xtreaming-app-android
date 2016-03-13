package com.dechcaudron.xtreaming.presenter;

import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.controller.RepositoryController;
import com.dechcaudron.xtreaming.model.Repository;
import com.dechcaudron.xtreaming.presenter.interfaces.IAddRepositoryView;
import com.dechcaudron.xtreaming.view.interfaces.IAddRepositoryViewPresenter;

public class AddRepositoryViewPresenter implements IAddRepositoryViewPresenter, RepositoryController.AddRepositoryListener
{
    private static final String TAG = LogController.makeTag(AddRepositoryViewPresenter.class);

    private IAddRepositoryView view;

    public AddRepositoryViewPresenter(IAddRepositoryView view)
    {
        this.view = view;
    }

    @Override
    public void addRepository(int repoType, String domain, int port, boolean requireSSL, String username, String password)
    {
        view.setBusy(true);
        RepositoryController.addRepository(this, repoType, domain, port, requireSSL, username, password);
    }

    @Override
    public void onRepositoryAdded(Repository repository)
    {
        view.setBusy(false);
        view.onRepositoryAdded();
    }

    @Override
    public void onInvalidRepository(int errorResId)
    {
        view.setBusy(false);
        view.onAddRepositoryError(errorResId);
    }

    @Override
    public void onInvalidCredentials()
    {
        view.setBusy(false);
        LogController.LOGI(TAG, "Invalid credentials!");
    }

    @Override
    public void onUnreachableRepository(int errorResId)
    {
        view.setBusy(false);
        view.onAddRepositoryError(errorResId);

    }

    @Override
    public void onAddRepositoryError(int errorResId)
    {
        view.setBusy(false);
        view.onAddRepositoryError(errorResId);
    }
}
