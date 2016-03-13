package com.dechcaudron.xtreaming.model;

public class Album
{
    private final int repoLocalId;
    private final String artistName;
    private final String name;

    public Album(int repoLocalId, String artistName, String name)
    {
        this.repoLocalId = repoLocalId;
        this.artistName = artistName;
        this.name = name;
    }

    public int getRepoLocalId()
    {
        return repoLocalId;
    }

    public String getArtistName()
    {
        return artistName;
    }

    String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return getName();
    }
}
