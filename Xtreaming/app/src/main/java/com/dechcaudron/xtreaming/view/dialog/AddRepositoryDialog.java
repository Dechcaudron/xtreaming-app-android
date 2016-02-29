package com.dechcaudron.xtreaming.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dechcaudron.xtreaming.R;
import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.view.presenter.AddRepositoryViewPresenter;
import com.dechcaudron.xtreaming.view.presenter.IAddRepositoryView;

public class AddRepositoryDialog extends Dialog implements View.OnClickListener, IAddRepositoryView
{

    private static final String TAG = LogController.makeTag(AddRepositoryDialog.class);

    IAddRepositoryViewPresenter presenter;
    Activity activity;

    EditText domainEditText;
    EditText usernameEditText;
    EditText passwordEditText;

    Button addButton;
    Button cancelButton;

    public AddRepositoryDialog(Activity activity)
    {
        super(activity);
        this.activity = activity;

        setContentView(R.layout.add_repository_dialog);
        setTitle("Add Koel Repository");
        setCancelable(true);

        domainEditText = (EditText) findViewById(R.id.domainEditText);
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        addButton = (Button) findViewById(R.id.addButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        addButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        presenter = new AddRepositoryViewPresenter(this);
    }

    @Override
    public void setBusy(final boolean busy)
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                if (busy)
                {
                    setCancelable(false);
                    addButton.setEnabled(false);
                    cancelButton.setEnabled(false);

                } else
                {
                    setCancelable(true);
                    addButton.setEnabled(true);
                    cancelButton.setEnabled(true);
                }
            }
        });
    }

    @Override
    public void onRepositoryAdded()
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(getContext(), "Repository added", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }

    @Override
    public void onAddRepositoryError(final int errorResId)
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(getContext(), errorResId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.addButton:
                //0 goes for Koel, no need to add anything else by now
                presenter.addRepository(0, domainEditText.getText().toString().trim(), usernameEditText.getText().toString().trim(), passwordEditText.getText().toString());
                break;

            case R.id.cancelButton:
                dismiss();
                break;

            default:
                LogController.LOGW(TAG, "Clicked on an unhandled view");
        }
    }
}
