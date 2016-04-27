package com.dechcaudron.xtreaming.dataSources;

import com.dechcaudron.xtreaming.BaseApplication;
import com.dechcaudron.xtreaming.controller.LogController;
import com.dechcaudron.xtreaming.model.Repository;
import com.dechcaudron.xtreaming.repositoryInterface.IRepositoryAuthToken;
import com.dechcaudron.xtreaming.repositoryInterface.RepoTypes;
import com.dechcaudron.xtreaming.repositoryInterface.RepositoryAuthTokenFactory;

import java.util.ArrayList;
import java.util.List;

public class RepositoriesDataSource
{
    private static final String TAG = LogController.makeTag(RepositoriesDataSource.class);


    public List<Repository> getLinkedRepositories()
    {
        return getLinkedRepositoriesFromPreferences();
    }

    public boolean saveNewRepository(Repository repository)
    {
        List<Repository> repositories = getLinkedRepositories();
        repositories.add(repository);
        return storeRepositoriesInPreferences(repositories);
    }

    public boolean removeRepository(int repositoryLocalId)
    {
        List<Repository> repositories = getLinkedRepositories();

        for (int i = 0; i < repositories.size(); ++i)
        {
            if (repositories.get(i).getRepoLocalId() == repositoryLocalId)
            {
                repositories.remove(i);
                break;
            }
        }

        return storeRepositoriesInPreferences(repositories);
    }

    private static final String LINKED_REPOSITORIES_PREFS_KEY = "linkedRepos";

    private String getLinkedRepositoriesPreference()
    {
        return BaseApplication.getStringPref(LINKED_REPOSITORIES_PREFS_KEY, "");
    }

    private boolean saveLinkedRepositoriesPreference(String preferenceContent)
    {
        return BaseApplication.setStringPrefSync(LINKED_REPOSITORIES_PREFS_KEY, preferenceContent);
    }

    private static final String LINKED_REPOSITORIES_SEPARATION_STRING = "_---_";
    private static final String SERIALIZED_REPOSITORY_FIELD_SEPARATION_STRING = "___";

    /*
     * Repositories are stored altogether as a single string as follows:
     *      REPO1_---_REPO2_---_REPO3
     *
     * Where each repository has the following fields stored as follows
     *      REPOLOCALID___REPOTYPE___DOMAINURL___PORT___REQUIRESSL(0,1)___USERNAME___AUTHENTICATIONTOKEN
     */

    private boolean storeRepositoriesInPreferences(List<Repository> repositories)
    {
        String preferenceData = "";

        for (Repository repo : repositories)
        {
            if (!preferenceData.isEmpty())
            {
                preferenceData += LINKED_REPOSITORIES_SEPARATION_STRING;
            }

            preferenceData += serializeRepository(repo);
        }

        return saveLinkedRepositoriesPreference(preferenceData);
    }

    private String serializeRepository(Repository repository)
    {
        StringBuilder builder = new StringBuilder();

        builder.append(repository.getRepoLocalId());
        builder.append(SERIALIZED_REPOSITORY_FIELD_SEPARATION_STRING);
        builder.append(repository.getRepoTypeCode());
        builder.append(SERIALIZED_REPOSITORY_FIELD_SEPARATION_STRING);
        builder.append(repository.getDomainURL());
        builder.append(SERIALIZED_REPOSITORY_FIELD_SEPARATION_STRING);
        builder.append(repository.getPort());
        builder.append(SERIALIZED_REPOSITORY_FIELD_SEPARATION_STRING);
        builder.append(repository.requiresSSL() ? "1" : "0");
        builder.append(SERIALIZED_REPOSITORY_FIELD_SEPARATION_STRING);
        builder.append(repository.getUsername());
        builder.append(SERIALIZED_REPOSITORY_FIELD_SEPARATION_STRING);
        builder.append(repository.getAuthenticationToken().getSerialized());

        //LogController.LOGD(TAG, "Repo serialized as " + builder.toString());

        return builder.toString();
    }

    private List<Repository> getLinkedRepositoriesFromPreferences()
    {
        String preference = getLinkedRepositoriesPreference();

        List<Repository> linkedRepositories = new ArrayList<>();

        if (preference.isEmpty())
            return linkedRepositories;

        String[] serializedRepositories = preference.split(LINKED_REPOSITORIES_SEPARATION_STRING);

        for (String serializedRepo : serializedRepositories)
        {
            if (serializedRepo.isEmpty())
            {
                LogController.LOGW(TAG, "Found empty serialized repo, this should not have happened");
                continue;
            }

            Repository repo = deserializeRepo(serializedRepo);


            if (repo != null)
                linkedRepositories.add(repo);
            else
            {
                LogController.LOGW(TAG, "One repository could not be retrieved from preferences");
            }
        }

        return linkedRepositories;
    }

    private Repository deserializeRepo(String serializedRepoString)
    {
        String[] repoData = serializedRepoString.split(SERIALIZED_REPOSITORY_FIELD_SEPARATION_STRING);

        try
        {
            IRepositoryAuthToken authToken = RepositoryAuthTokenFactory.decodeAuthToken(RepoTypes.getRepoType(Integer.parseInt(repoData[1])), repoData[6]);

            return new Repository(Integer.parseInt(repoData[0]), Integer.parseInt(repoData[1]), repoData[2], Integer.parseInt(repoData[3]), repoData[4].equals("1"), repoData[5], authToken);
        } catch (Exception e)
        {
            LogController.LOGE(TAG, "Failed deserializing repository from " + serializedRepoString + " returning null", e);
            return null;
        }
    }
}
