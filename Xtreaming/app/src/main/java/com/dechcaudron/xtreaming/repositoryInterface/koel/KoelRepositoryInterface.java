package com.dechcaudron.xtreaming.repositoryInterface.koel;


import android.support.annotation.Nullable;

import com.dechcaudron.koel.api.exceptions.KoelApiException;
import com.dechcaudron.koel.api.exceptions.KoelAuthenticationException;
import com.dechcaudron.koel.api.objects.KoelAuthenticationToken;
import com.dechcaudron.koel.api.utils.AuthenticationUtils;
import com.dechcaudron.koel.api.utils.MediaUtils;
import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.model.Artist;
import com.dechcaudron.xtreaming.repositoryInterface.IRepositoryAuthToken;
import com.dechcaudron.xtreaming.repositoryInterface.RepositoryInterface;
import com.dechcaudron.xtreaming.repositoryInterface.RepositoryInterfaceException;

import org.json.JSONArray;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KoelRepositoryInterface implements RepositoryInterface
{
    private static final String TAG = LogController.makeTag(KoelRepositoryInterface.class);

    @Nullable
    @Override
    public IRepositoryAuthToken attemptLogin(String domain, int port, boolean useHttps, String username, String password) throws RepositoryInterfaceException, IOException
    {
        URL serverURL = null;
        try
        {
            serverURL = buildURL(domain, port, useHttps);
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

        }

        throw new RepositoryInterfaceException();
    }

    @Override
    public List<Artist> getArtists(String domain, int port, boolean useHttps, IRepositoryAuthToken authToken) throws RepositoryInterfaceException, IOException
    {
        URL serverUrl;

        try
        {
            serverUrl = buildURL(domain, port, useHttps);
            List<com.dechcaudron.koel.api.objects.Artist> apiArtists = MediaUtils.getMediaInfo(serverUrl, new KoelAuthenticationToken(authToken.getSerialized())).getArtists();

            List<Artist> artists = new ArrayList<>(apiArtists.size());

            for (com.dechcaudron.koel.api.objects.Artist apiArtist : apiArtists)
            {
                artists.add(new Artist(apiArtist.getName()));
            }

            return artists;

        } catch (MalformedURLException e)
        {
            LogController.LOGE(TAG, "Malformed URL for domain " + domain, e);

        } catch (KoelApiException e)
        {
            LogController.LOGE(TAG, "", e);
        }

        throw new RepositoryInterfaceException("Could not fetch artists");
    }

    private URL buildURL(String domain, int port, boolean useHttps) throws MalformedURLException
    {
        return new URL((useHttps ? "https://" : "http://") + domain + ":" + Integer.toString(port));
    }
}
