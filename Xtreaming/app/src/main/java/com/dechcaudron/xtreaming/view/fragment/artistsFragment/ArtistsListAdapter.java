package com.dechcaudron.xtreaming.view.fragment.artistsFragment;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.dechcaudron.xtreaming.R;
import com.dechcaudron.xtreaming.model.Artist;

public class ArtistsListAdapter extends ArrayAdapter<Artist>
{
    public ArtistsListAdapter(Context context)
    {
        super(context, R.layout.simple_list_view_item);
    }
}
