package com.dechcaudron.xtreaming.view.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.dechcaudron.xtreaming.R;
import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.model.Album;
import com.dechcaudron.xtreaming.presenter.AlbumsViewPresenter;
import com.dechcaudron.xtreaming.presenter.interfaces.IAlbumsView;
import com.dechcaudron.xtreaming.view.interfaces.IAlbumsViewPresenter;

import java.util.List;

public class AlbumsActivity extends ListActivity implements IAlbumsView
{
    private static final String TAG = LogController.makeTag(AlbumsActivity.class);

    private IAlbumsViewPresenter presenter;
    private ArrayAdapter<Album> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        adapter = new ArrayAdapter<>(this, R.layout.simple_list_view_item);
        setListAdapter(adapter);

        presenter = new AlbumsViewPresenter(this, getIntent().getExtras());
    }

    @Override
    public void displayAlbums(final List<Album> albums)
    {
        LogController.LOGD(TAG, "Displaying " + albums.size() + " albums");

        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                adapter.clear();
                adapter.addAll(albums);
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
                Toast.makeText(AlbumsActivity.this, errorResId, Toast.LENGTH_SHORT).show();
            }
        });
    }
}