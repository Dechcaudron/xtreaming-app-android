package com.dechcaudron.xtreaming.notifications;

public enum MediaNotificationIntents
{
    PAUSE("com.dechcaudron.xtreaming.notifications.PAUSE"),
    PLAY("com.dechcaudron.xtreaming.notifications.PLAY"),
    STOP("com.dechcaudron.xtreaming.notifications.STOP"),
    NEXT("com.dechcaudron.xtreaming.notifications.NEXT");

    private final String intentString;

    MediaNotificationIntents(String intentString)
    {
        this.intentString = intentString;
    }

    public String getIntentString()
    {
        return intentString;
    }
}
