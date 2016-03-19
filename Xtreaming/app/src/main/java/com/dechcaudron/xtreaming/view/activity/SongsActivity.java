package com.dechcaudron.xtreaming.view.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.dechcaudron.xtreaming.R;
import com.dechcaudron.xtreaming.model.Song;
import com.dechcaudron.xtreaming.presenter.SongsViewPresenter;
import com.dechcaudron.xtreaming.presenter.interfaces.ISongsView;
import com.dechcaudron.xtreaming.view.interfaces.ISongsViewPresenter;

import java.util.List;

public class SongsActivity extends ListActivity implements ISongsView, AdapterView.OnItemClickListener
{
    private ISongsViewPresenter presenter;
    private ArrayAdapter<Song> songsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        songsAdapter = new ArrayAdapter<>(this, R.layout.simple_list_view_item);
        setListAdapter(songsAdapter);

        presenter = new SongsViewPresenter(this, getIntent().getExtras());
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        getListView().setOnItemClickListener(this);
    }

    @Override
    public void displaySongs(final List<Song> songs)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                songsAdapter.clear();
                songsAdapter.addAll(songs);
            }
        });
    }

    @Override
    public void displayError(final int errorResId)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(SongsActivity.this, errorResId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        presenter.playSong(songsAdapter.getItem(position));
    }
}
