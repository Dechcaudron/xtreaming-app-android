package com.dechcaudron.xtreaming.view.dialog;

import android.app.AlertDialog;
import android.content.Context;

import com.dechcaudron.xtreaming.model.Repository;

class RemoveRepositoryDialog extends AlertDialog
{
    private final Repository repository;

    public RemoveRepositoryDialog(Context context, Repository repository)
    {
        super(context);
        this.repository = repository;
        setTitle("Remove Repository");
        setCancelable(false);
    }
}
