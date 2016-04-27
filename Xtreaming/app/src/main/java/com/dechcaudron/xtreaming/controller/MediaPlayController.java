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
                Intent intent = getServiceIntent(MediaPlayService.Actions.QUEUE);
                IntentUtils.specifyRepoLocalId(intent, song.getRepoLocalId());
                IntentUtils.specifySongId(intent, song.getId());
                BaseApplication.getContext().startService(intent);
            }
        }).start();
    }

    public static void pauseMedia()
    {
        Intent intent = getServiceIntent(MediaPlayService.Actions.PAUSE);
        BaseApplication.getContext().startService(intent);
    }

    public static void playMedia()
    {
        Intent intent = getServiceIntent(MediaPlayService.Actions.PLAY);
        BaseApplication.getContext().startService(intent);
    }

    public static void stopMedia()
    {
        Intent intent = getServiceIntent(MediaPlayService.Actions.STOP);
        BaseApplication.getContext().startService(intent);
    }

    private static Intent getServiceIntent(MediaPlayService.Actions action)
    {
        return new Intent(BaseApplication.getContext(), MediaPlayService.class).putExtra(MediaPlayService.ACTION_IK, action.getAction());
    }
}
