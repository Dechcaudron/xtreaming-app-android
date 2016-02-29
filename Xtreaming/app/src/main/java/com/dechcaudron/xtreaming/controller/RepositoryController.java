package com.dechcaudron.xtreaming.controller;

import android.support.annotation.Nullable;

import com.dechcaudron.xtreaming.BaseApplication;
import com.dechcaudron.xtreaming.R;
import com.dechcaudron.xtreaming.model.Repository;
import com.dechcaudron.xtreaming.repositoryInterface.IRepositoryAuthToken;
import com.dechcaudron.xtreaming.repositoryInterface.RepoTypes;
import com.dechcaudron.xtreaming.repositoryInterface.RepositoryInterface;
import com.dechcaudron.xtreaming.repositoryInterface.RepositoryInterfaceException;
import com.dechcaudron.xtreaming.repositoryInterface.RepositoryInterfaceFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class RepositoryController
{
    private static final String TAG = LogController.makeTag(RepositoryController.class);

    private static final String LINKED_REPOSITORIES_PREFS_KEY = "linkedRepos";

    private static final String LINKED_REPOSITORIES_SEPARATION_STRING = "_---_";
    private static final String SERIALIZED_REPOSITORY_FIELD_SEPARATION_STRING = "___";

    private static List<Repository> linkedRepositories;


    public interface FetchLinkedRepositoriesListener
    {
        void onLinkedRepositoriesAvailable(List<Repository> repositories);

        void onFetchLinkedRepositoriesError(int errorResId);
    }

    public static void fetchLinkedRepositories(FetchLinkedRepositoriesListener listener)
    {
        if (linkedRepositories == null)
            linkedRepositories = getLinkedRepositoriesFromPreferences();

        listener.onLinkedRepositoriesAvailable(linkedRepositories);
    }

    public interface AddRepositoryListener
    {
        void onRepositoryAdded(Repository repository);

        void onInvalidRepository(int errorResId);

        void onUnreachableRepository(int errorResId);

        void onInvalidCredentials();

        void onAddRepositoryError(int errorResId);
    }

    public static void addRepository(final AddRepositoryListener listener, final int repoTypeCode, final String domain, final int port, final String username, final String password)
    {
        if (!isDomainValid(domain))
            listener.onInvalidRepository(R.string.invalid_domain);
        else
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    RepoTypes repoType;

                    if (repoTypeCode == 0)
                        repoType = RepoTypes.Koel;
                    else
                    {
                        listener.onInvalidRepository(R.string.invalid_repo_type);
                        return;
                    }

                    RepositoryInterface repoInterface = RepositoryInterfaceFactory.getInterface(repoType);

                    try
                    {
                        IRepositoryAuthToken authToken = repoInterface.attemptLogin(domain, port, false, username, password);
                        if (authToken == null)
                            listener.onInvalidCredentials();
                        else
                            LogController.LOGD(TAG, "Credentials: " + authToken.getSerialized());

                    } catch (RepositoryInterfaceException e)
                    {
                        listener.onUnreachableRepository(R.string.unreachable_repository);
                    }

                }
            }).start();
        }
    }

    private static boolean isDomainValid(String domain)
    {
        return domain.contains(".") && !domain.contains("/") && !domain.contains(":") && !domain.contains(" ");
    }

    private static void storeRepository(RepoTypes repoTypes, String domain, int port, String serializedAuthToken)
    {

    }

    private static List<Repository> getLinkedRepositoriesFromPreferences()
    {
        String preference = BaseApplication.readStringPref(LINKED_REPOSITORIES_PREFS_KEY, null);

        if (preference == null)
            return new ArrayList<>();

        List<Repository> linkedRepositories = new ArrayList<>();
        String[] serializedRepositories = preference.split(LINKED_REPOSITORIES_SEPARATION_STRING);

        for (String serializedRepo : serializedRepositories)
        {
            Repository repo = deserializeRepo(serializedRepo);

            if (repo != null)
                linkedRepositories.add(repo);
            else
            {
                //TODO: log warning
            }
        }

        return linkedRepositories;
    }

    @Nullable
    private static Repository deserializeRepo(String serializedRepoString)
    {
        String[] repoData = serializedRepoString.split(SERIALIZED_REPOSITORY_FIELD_SEPARATION_STRING);

        try
        {
            return new Repository(Integer.parseInt(repoData[0]), repoData[1], repoData[2], repoData[3]);
        } catch (IndexOutOfBoundsException e)
        {
            return null;
        }
    }

}
