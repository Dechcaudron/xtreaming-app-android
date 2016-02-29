package com.dechcaudron.xtreaming.repositoryInterface.koel;


import android.support.annotation.Nullable;

import com.dechcaudron.koel.api.exceptions.KoelApiException;
import com.dechcaudron.koel.api.exceptions.KoelAuthenticationException;
import com.dechcaudron.koel.api.utils.AuthenticationUtils;
import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.repositoryInterface.IRepositoryAuthToken;
import com.dechcaudron.xtreaming.repositoryInterface.RepositoryInterface;
import com.dechcaudron.xtreaming.repositoryInterface.RepositoryInterfaceException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class KoelRepositoryInterface implements RepositoryInterface
{
    private static final String TAG = LogController.makeTag(KoelRepositoryInterface.class);

    @Nullable
    @Override
    public IRepositoryAuthToken attemptLogin(String domain, int port, boolean useHttps, String username, String password) throws RepositoryInterfaceException
    {
        URL serverURL = null;
        try
        {
            serverURL = new URL((useHttps ? "https://" : "http://") + domain + ":" + Integer.toString(port));
            return new KoelAuthTokenWrapper(AuthenticationUtils.login(serverURL, username, password));
        } catch (MalformedURLException e)
        {
            LogController.LOGE(TAG, "Malformed URL for domain " + domain, e);
        } catch (KoelAuthenticationException e)
        {
            LogController.LOGI(TAG, "Invalid login credentials (" + username + "," + password + ") for " + serverURL.toString(), e);
            return null;
        } catch (KoelApiException e)
        {
            LogController.LOGE(TAG, "API exception during login", e);

        } catch (IOException e)
        {
            LogController.LOGE(TAG, "IOException occurred during login for URL "+serverURL.toString(), e);
        }

        throw new RepositoryInterfaceException();
    }
}
