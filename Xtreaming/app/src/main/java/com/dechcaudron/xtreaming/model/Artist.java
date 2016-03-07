package com.dechcaudron.xtreaming.model;

public class Artist
{
    private final String name;

    public Artist(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
