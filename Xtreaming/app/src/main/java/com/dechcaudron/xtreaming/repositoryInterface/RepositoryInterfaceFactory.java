package com.dechcaudron.xtreaming.repositoryInterface;

import android.support.annotation.NonNull;

import com.dechcaudron.xtreaming.repositoryInterface.koel.KoelRepositoryInterface;

public abstract class RepositoryInterfaceFactory
{
    @NonNull
    public static RepositoryInterface getInterface(RepoTypes repoType)
    {
        switch (repoType)
        {
            case Koel:
                return new KoelRepositoryInterface();

            default:
                throw new RuntimeException("Unhandled repoType " + repoType.name());
        }
    }
}
