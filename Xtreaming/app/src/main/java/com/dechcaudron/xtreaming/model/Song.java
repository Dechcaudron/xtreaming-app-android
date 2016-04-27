package com.dechcaudron.xtreaming.model;

public class Song
{
    private final int repoLocalId;
    private final String artistName;
    private final String albumName;
    private final String name;
    private final String id;

    public Song(int repoLocalId, String artistName, String albumName, String name, String id)
    {
        this.repoLocalId = repoLocalId;
        this.artistName = artistName;
        this.albumName = albumName;
        this.name = name;
        this.id = id;
    }

    public int getRepoLocalId()
    {
        return repoLocalId;
    }

    public String getArtistName()
    {
        return artistName;
    }

    public String getAlbumName()
    {
        return albumName;
    }

    public String getName()
    {
        return name;
    }

    public String getId()
    {
        return id;
    }

    public boolean isLocal()
    {
        return repoLocalId == 0;
    }

    @Override
    public String toString()
    {
        return getName();
    }

    public String toDebugString()
    {
        return "[" + getId() + "] " + getName() + " -> " + getAlbumName() + " -> " + getArtistName();
    }
}
