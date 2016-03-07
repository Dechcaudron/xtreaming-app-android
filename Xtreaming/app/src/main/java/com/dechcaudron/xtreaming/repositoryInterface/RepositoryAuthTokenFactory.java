package com.dechcaudron.xtreaming.repositoryInterface;

import com.dechcaudron.xtreaming.repositoryInterface.koel.KoelAuthTokenWrapper;

public abstract class RepositoryAuthTokenFactory
{
    public static IRepositoryAuthToken decodeAuthToken(RepoTypes repoType, String encodedToken) throws RepositoryInterfaceException
    {
        switch (repoType)
        {
            case Koel:
                return new KoelAuthTokenWrapper(encodedToken);

            default:
                throw new RepositoryInterfaceException("Unavailable instructions to decode token for repoType " + repoType.name());
        }
    }
}
