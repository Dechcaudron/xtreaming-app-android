package com.dechcaudron.xtreaming.view.fragment.repositoriesFragment;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.dechcaudron.xtreaming.R;
import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.model.Repository;

import java.util.ArrayList;
import java.util.List;

public class RepositoriesListAdapter  extends ArrayAdapter<Repository>
{
    private static final String TAG = LogController.makeTag(RepositoriesListAdapter.class);

    public RepositoriesListAdapter(Context context)
    {
        super(context, R.layout.repository_list_view);
    }

    //TODO: needs view recycling
    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.repository_list_view, viewGroup, false);

        Repository repository = getItem(i);

        TextView repoUrlTextView = (TextView) itemView.findViewById(R.id.repoUrlTextView);
        TextView repoPortTextView = (TextView) itemView.findViewById(R.id.repoPortTextView);
        TextView repoUsernameTextView = (TextView) itemView.findViewById(R.id.repoUsernameTextView);

        repoUrlTextView.setText(repository.getDomainURL());
        repoPortTextView.setText("Port " + repository.getPort());
        repoUsernameTextView.setText(repository.getUsername());

        return itemView;
    }
}
