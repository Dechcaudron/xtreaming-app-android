package com.dechcaudron.xtreaming.utils;

import android.content.Intent;
import android.os.Bundle;

public abstract class IntentUtils
{
    public static final int REPO_LOCAL_ID_UNSPECIFIED = 0;

    private static final String REPO_LOCAL_ID_INTENT_KEY = "REPO_LOCAL_ID_IK";
    private static final String ARTIST_INTENT_KEY = "ARTIST_IK";
    private static final String ALBUM_INTENT_KEY = "ALBUM_IK";
    private static final String SONG_ID_INTENT_KEY = "SONG_ID_IK";

    public static void specifyRepoLocalId(Intent intent, int repoLocalId)
    {
        intent.putExtra(REPO_LOCAL_ID_INTENT_KEY, repoLocalId);
    }

    public static int getSpecifiedRepoLocalId(Intent intent)
    {
        return intent.getIntExtra(REPO_LOCAL_ID_INTENT_KEY, REPO_LOCAL_ID_UNSPECIFIED);
    }

    public static int getSpecifiedRepoLocalId(Bundle bundle)
    {
        return bundle.getInt(REPO_LOCAL_ID_INTENT_KEY, REPO_LOCAL_ID_UNSPECIFIED);
    }

    public static void specifyArtist(Intent intent, String artistName)
    {
        intent.putExtra(ARTIST_INTENT_KEY, artistName);
    }

    public static String getSpecifiedArtist(Intent intent)
    {
        return intent.getStringExtra(ARTIST_INTENT_KEY);
    }

    public static String getSpecifiedArtist(Bundle bundle)
    {
        return bundle.getString(ARTIST_INTENT_KEY);
    }

    public static void specifyAlbum(Intent intent, String albumName)
    {
        intent.putExtra(ALBUM_INTENT_KEY, albumName);
    }

    public static String getSpecifiedAlbum(Intent intent)
    {
        return intent.getStringExtra(ALBUM_INTENT_KEY);
    }

    public static String getSpecifiedAlbum(Bundle bundle)
    {
        return bundle.getString(ALBUM_INTENT_KEY);
    }

    public static void specifySongId(Intent intent, String songId)
    {
        intent.putExtra(SONG_ID_INTENT_KEY, songId);
    }

    public static String getSpecifiedSongId(Intent intent)
    {
        return intent.getStringExtra(SONG_ID_INTENT_KEY);
    }

    public static String getSpecifiedSongId(Bundle bundle)
    {
        return bundle.getString(SONG_ID_INTENT_KEY);
    }

}
