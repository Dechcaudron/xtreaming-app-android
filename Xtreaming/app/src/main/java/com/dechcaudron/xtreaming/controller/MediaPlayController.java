package com.dechcaudron.xtreaming.controller;

import android.content.Intent;

import com.dechcaudron.xtreaming.BaseApplication;
import com.dechcaudron.xtreaming.model.Song;
import com.dechcaudron.xtreaming.service.MediaPlayService;
import com.dechcaudron.xtreaming.utils.IntentUtils;

public abstract class MediaPlayController
{
    public static void playSong(final Song song)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent = getServiceIntent();
                IntentUtils.specifyRepoLocalId(intent, song.getRepoLocalId());
                IntentUtils.specifySongId(intent, song.getId());
                BaseApplication.getContext().startService(intent);
            }
        }).start();

    }

    private static Intent getServiceIntent()
    {
        return new Intent(BaseApplication.getContext(), MediaPlayService.class);
    }
}
