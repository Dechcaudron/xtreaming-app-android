package com.dechcaudron.xtreaming.view.presenter;

public interface IAddRepositoryView {
    void onRepositoryAdded();

    void onAddRepositoryError(int errorResId);

    void setBusy(boolean busy);
}
