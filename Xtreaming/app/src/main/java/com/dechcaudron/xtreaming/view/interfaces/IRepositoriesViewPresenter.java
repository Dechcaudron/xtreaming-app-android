package com.dechcaudron.xtreaming.view.interfaces;

import com.dechcaudron.xtreaming.model.Repository;

public interface IRepositoriesViewPresenter
{
    void onResume();
    void removeRepository(Repository repository);
}
