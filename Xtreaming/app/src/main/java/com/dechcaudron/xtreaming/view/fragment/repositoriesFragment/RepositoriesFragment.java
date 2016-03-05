package com.dechcaudron.xtreaming.view.fragment.repositoriesFragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.dechcaudron.xtreaming.R;
import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.model.Repository;
import com.dechcaudron.xtreaming.view.dialog.AddRepositoryDialog;
import com.dechcaudron.xtreaming.view.presenter.IRepositoriesView;
import com.dechcaudron.xtreaming.view.presenter.RepositoriesFragmentPresenter;

import java.util.List;

public class RepositoriesFragment extends ListFragment implements IRepositoriesView, View.OnClickListener
{

    private static final String TAG = LogController.makeTag(RepositoriesFragment.class);

    ListView repositoriesListView;
    View addRepoButton;
    RepositoriesListAdapter repositoriesListAdapter;

    IRepositoriesFragmentPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_repostitories, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        presenter = new RepositoriesFragmentPresenter(this);

        repositoriesListView = getListView();
        repositoriesListAdapter = new RepositoriesListAdapter(getActivity());
        repositoriesListView.setAdapter(repositoriesListAdapter);

        addRepoButton = getView().findViewById(R.id.addRepoButton);

        addRepoButton.setOnClickListener(this);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        presenter.showLinkedRepositories();
    }

    @Override
    public void displayRepositories(final List<Repository> repositories)
    {
        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                repositoriesListAdapter.setRepositories(repositories);
            }
        });
    }

    @Override
    public void displayError(final int errorResId)
    {
        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(getActivity(), errorResId, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.addRepoButton:
                showAddRepoDialog();
                break;

            default:
                LogController.LOGW(TAG, "Clicked on unhandled view");
        }
    }

    private void showAddRepoDialog()
    {
        AddRepositoryDialog dialog = new AddRepositoryDialog(getActivity());
        dialog.show();
    }
}
