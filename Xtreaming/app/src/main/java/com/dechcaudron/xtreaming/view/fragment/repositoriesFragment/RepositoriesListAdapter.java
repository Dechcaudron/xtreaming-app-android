package com.dechcaudron.xtreaming.view.fragment.repositoriesFragment;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.dechcaudron.xtreaming.R;
import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.model.Repository;

import java.util.ArrayList;
import java.util.List;

public class RepositoriesListAdapter implements ListAdapter
{
    private static final String TAG = LogController.makeTag(RepositoriesListAdapter.class);

    private final Context context;
    private final List<DataSetObserver> observers;

    private List<Repository> repositories;

    public RepositoriesListAdapter(Context context)
    {
        this.context = context;
        observers = new ArrayList<>();
    }

    public void setRepositories(List<Repository> repositories)
    {
        this.repositories = repositories;

        for (DataSetObserver observer : observers)
        {
            observer.onChanged();
        }
    }

    @Override
    public boolean areAllItemsEnabled()
    {
        return false;
    }

    @Override
    public boolean isEnabled(int i)
    {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver)
    {
        observers.add(dataSetObserver);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver)
    {
        observers.remove(dataSetObserver);
    }

    @Override
    public int getCount()
    {
        return repositories == null ? 0 : repositories.size();
    }

    @Override
    public Object getItem(int i)
    {
        return repositories.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    //TODO: needs view recycling
    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.repository_list_view, viewGroup, false);

        Repository repository = repositories.get(i);

        TextView repoUrlTextView = (TextView) itemView.findViewById(R.id.repoUrlTextView);
        TextView repoPortTextView = (TextView) itemView.findViewById(R.id.repoPortTextView);
        TextView repoUsernameTextView = (TextView) itemView.findViewById(R.id.repoUsernameTextView);

        repoUrlTextView.setText(repository.getDomainURL());
        repoPortTextView.setText("Port " + repository.getPort());
        repoUsernameTextView.setText(repository.getUsername());

        return itemView;
    }

    @Override
    public int getItemViewType(int i)
    {
        return IGNORE_ITEM_VIEW_TYPE;
    }

    @Override
    public int getViewTypeCount()
    {
        return 1;
    }

    @Override
    public boolean isEmpty()
    {
        return repositories == null || repositories.isEmpty();
    }
}
