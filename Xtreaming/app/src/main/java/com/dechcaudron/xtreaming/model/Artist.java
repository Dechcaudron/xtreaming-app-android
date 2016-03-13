package com.dechcaudron.xtreaming.model;

public class Artist
{
    private final String name;
    private final int repoLocalId;

    public Artist(String name, int repoLocalId)
    {
        this.name = name;
        this.repoLocalId = repoLocalId;
    }

    public String getName()
    {
        return name;
    }

    public int getRepoLocalId()
    {
        return repoLocalId;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
