package com.dechcaudron.xtreaming.presenter.interfaces;

public interface IAddRepositoryView {
    void onRepositoryAdded();

    void onAddRepositoryError(int errorResId);

    void setBusy(boolean busy);
}
