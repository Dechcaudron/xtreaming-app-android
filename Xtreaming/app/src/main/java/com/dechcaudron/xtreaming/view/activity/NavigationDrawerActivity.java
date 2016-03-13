package com.dechcaudron.xtreaming.view.activity;

import android.os.Bundle;

import com.dechcaudron.xtreaming.view.fragment.WelcomeFragment;
import com.dechcaudron.xtreaming.view.fragment.artistsFragment.ArtistsFragment;
import com.dechcaudron.xtreaming.view.fragment.repositoriesFragment.RepositoriesFragment;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

public class NavigationDrawerActivity extends MaterialNavigationDrawer
{

    @Override
    public void init(Bundle bundle)
    {
        addSection(newSection("Welcome", new WelcomeFragment()));
        addSection(newSection("Repositories", new RepositoriesFragment()));
        addSection(newSection("Artists", new ArtistsFragment()));
    }
}
