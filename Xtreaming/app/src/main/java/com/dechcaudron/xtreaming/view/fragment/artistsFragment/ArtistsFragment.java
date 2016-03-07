package com.dechcaudron.xtreaming.view.fragment.artistsFragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.model.Artist;
import com.dechcaudron.xtreaming.view.presenter.ArtistsViewPresenter;
import com.dechcaudron.xtreaming.view.presenter.IArtistsView;

import java.util.List;

public class ArtistsFragment extends ListFragment implements IArtistsView
{
    private static final String TAG = LogController.makeTag(ArtistsFragment.class);
    IArtistsViewPresenter presenter;
    ArrayAdapter<Artist> arrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        presenter = new ArtistsViewPresenter(this);
        arrayAdapter = new ArtistsListAdapter(getActivity());
        setListAdapter(arrayAdapter);
    }

    @Override
    public void onStart()
    {
        super.onStart();

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
                arrayAdapter.clear();
                arrayAdapter.addAll(artists);
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
}
