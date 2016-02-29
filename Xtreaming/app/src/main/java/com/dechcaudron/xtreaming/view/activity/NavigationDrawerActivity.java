package com.dechcaudron.xtreaming.view.activity;

import android.os.Bundle;

import com.dechcaudron.xtreaming.view.fragment.RepositoriesFragment;
import com.dechcaudron.xtreaming.view.fragment.WelcomeFragment;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

public class NavigationDrawerActivity extends MaterialNavigationDrawer {

    @Override
    public void init(Bundle bundle) {

        addSection(newSection("Section 1", new WelcomeFragment()));
        addSection(newSection("Repositories", new RepositoriesFragment()));
    }
}
