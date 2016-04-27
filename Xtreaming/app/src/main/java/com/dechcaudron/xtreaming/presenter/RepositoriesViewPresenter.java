package com.dechcaudron.xtreaming.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.controller.RepositoryController;
import com.dechcaudron.xtreaming.model.Repository;
import com.dechcaudron.xtreaming.presenter.interfaces.IRepositoriesView;
import com.dechcaudron.xtreaming.view.interfaces.IRepositoriesViewPresenter;

import java.util.List;

public class RepositoriesViewPresenter implements IRepositoriesViewPresenter, RepositoryController.FetchLinkedRepositoriesListener, RepositoryController.OnRepositoryRemovedListener
{
    private static final String TAG = LogController.makeTag(RepositoriesViewPresenter.class);

    private final IRepositoriesView view;
    private final Context context;

    public RepositoriesViewPresenter(Context context, IRepositoriesView view)
    {
        this.context = context;
        this.view = view;
    }

    @Override
    public void onResume()
    {
        fetchLinkedRepositories();
    }

    @Override
    public void removeRepository(final Repository repository)
    {
        AlertDialog dialog = new AlertDialog.Builder(context).setTitle("Remove Repository")
                .setMessage("Do you really wish to remove the link with the repository at " + repository.getDomainURL() + " ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        RepositoryController.removeRepository(repository, RepositoriesViewPresenter.this);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                })
                .setCancelable(true)
                .create();

        dialog.show();
    }

    @Override
    public void onLinkedRepositoriesAvailable(List<Repository> repositories)
    {
        view.displayRepositories(repositories);
    }

    @Override
    public void onRepositoryRemoved()
    {
        fetchLinkedRepositories();
    }

    @Override
    public void onRepositoryRemoveError(int errorResId)
    {
        view.displayError(errorResId);
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
