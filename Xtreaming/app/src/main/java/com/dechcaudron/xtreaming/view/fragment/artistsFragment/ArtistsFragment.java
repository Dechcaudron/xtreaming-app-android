package com.dechcaudron.xtreaming.view.fragment.artistsFragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.model.Artist;
import com.dechcaudron.xtreaming.presenter.ArtistsViewPresenter;
import com.dechcaudron.xtreaming.presenter.interfaces.IArtistsView;
import com.dechcaudron.xtreaming.view.interfaces.IArtistsViewPresenter;

import java.util.List;

public class ArtistsFragment extends ListFragment implements IArtistsView, AdapterView.OnItemClickListener
{
    private static final String TAG = LogController.makeTag(ArtistsFragment.class);
    IArtistsViewPresenter presenter;
    ArrayAdapter<Artist> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        adapter = new ArtistsListAdapter(getActivity());
        setListAdapter(adapter);

        presenter = new ArtistsViewPresenter(this, getActivity());
    }

    @Override
    public void onStart()
    {
        super.onStart();

        getListView().setOnItemClickListener(this);
        presenter.fetchArtists();
    }

    @Override
    public void displayArtists(final List<Artist> artists)
    {
        LogController.LOGD(TAG, "Displaying " + artists.size() + " artists");

        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                adapter.clear();
                adapter.addAll(artists);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        presenter.openArtist(adapter.getItem(position));
    }
}
