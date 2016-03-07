package com.dechcaudron.xtreaming.repositoryInterface;

public enum RepoTypes
{
    Koel(0);

    private final int code;

    RepoTypes(int code)
    {
        this.code = code;
    }

    public static RepoTypes getRepoType(int code) throws IllegalArgumentException
    {
        for (RepoTypes type : RepoTypes.values())
            if (type.getCode() == code) return type;

        throw new IllegalArgumentException("Supplied an invalid repoType code " + code);
    }

    private int getCode()
    {
        return code;
    }
}
