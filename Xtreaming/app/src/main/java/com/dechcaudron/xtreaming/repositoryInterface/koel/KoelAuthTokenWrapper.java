package com.dechcaudron.xtreaming.repositoryInterface.koel;

import com.dechcaudron.koel.api.exceptions.KoelApiException;
import com.dechcaudron.koel.api.objects.KoelAuthenticationToken;
import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.repositoryInterface.IRepositoryAuthToken;
import com.dechcaudron.xtreaming.repositoryInterface.RepositoryInterfaceException;

public class KoelAuthTokenWrapper implements IRepositoryAuthToken
{
    private static final String TAG = LogController.makeTag(KoelAuthTokenWrapper.class);

    private final KoelAuthenticationToken token;

    public KoelAuthTokenWrapper(KoelAuthenticationToken token)
    {
        this.token = token;
    }

    public KoelAuthTokenWrapper(String serializedToken) throws RepositoryInterfaceException
    {
        try
        {
            this.token = new KoelAuthenticationToken(serializedToken);
        } catch (KoelApiException e)
        {
            LogController.LOGE(TAG, "error constructing KoelAuthenticationToken from serializedToken " + serializedToken, e);
            throw new RepositoryInterfaceException();
        }
    }

    @Override
    public String getSerialized()
    {
        return token.getEncodedToken();
    }
}
