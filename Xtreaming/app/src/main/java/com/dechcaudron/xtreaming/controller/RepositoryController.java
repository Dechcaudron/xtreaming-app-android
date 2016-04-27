package com.dechcaudron.xtreaming.controller;

import com.dechcaudron.xtreaming.R;
import com.dechcaudron.xtreaming.controller.data.DataController;
import com.dechcaudron.xtreaming.model.Repository;
import com.dechcaudron.xtreaming.repositoryInterface.IRepositoryAuthToken;
import com.dechcaudron.xtreaming.repositoryInterface.RepoTypes;
import com.dechcaudron.xtreaming.repositoryInterface.RepositoryInterface;
import com.dechcaudron.xtreaming.repositoryInterface.RepositoryInterfaceFactory;

import java.util.List;

public abstract class RepositoryController
{
    private static final String TAG = LogController.makeTag(RepositoryController.class);

    private static List<Repository> linkedRepositories;

    public interface FetchLinkedRepositoriesListener
    {
        void onLinkedRepositoriesAvailable(List<Repository> repositories);

        void onFetchLinkedRepositoriesError(int errorResId);
    }

    public static void fetchLinkedRepositories(FetchLinkedRepositoriesListener listener)
    {
        listener.onLinkedRepositoriesAvailable(DataController.getSingleton().getLinkedRepositories());
    }

    public interface AddRepositoryListener
    {
        void onRepositoryAdded(Repository repository);

        void onInvalidRepository(int errorResId);

        void onUnreachableRepository(int errorResId);

        void onInvalidCredentials();

        void onAddRepositoryError(int errorResId);
    }

    public static void addRepository(final AddRepositoryListener listener, final int repoTypeCode, final String domain, final int port, final boolean requireSSL, final String username, final String password)
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

                    try
                    {
                        repoType = RepoTypes.getRepoType(repoTypeCode);
                    } catch (IllegalArgumentException e)
                    {
                        LogController.LOGE(TAG, "Bad repoTypeCode supplied: " + repoTypeCode, e);
                        listener.onInvalidRepository(R.string.invalid_repo_type);
                        return;
                    }

                    RepositoryInterface repoInterface = RepositoryInterfaceFactory.getInterface(repoType);

                    try
                    {
                        IRepositoryAuthToken authToken = repoInterface.attemptLogin(domain, port, requireSSL, username, password);
                        if (authToken == null)
                            listener.onInvalidCredentials();
                        else
                        {
                            Repository repository = new Repository(getNextRepoLocalId(), repoTypeCode, domain, port, requireSSL, username, authToken);
                            if (saveNewRepository(repository))
                            {
                                listener.onRepositoryAdded(repository);
                            } else
                            {
                                listener.onAddRepositoryError(R.string.add_repo_error);
                            }
                        }

                    } catch (Exception e)
                    {
                        listener.onUnreachableRepository(R.string.unreachable_repository);
                    }

                }
            }).start();
        }
    }

    public interface OnRepositoryRemovedListener
    {
        void onRepositoryRemoved();

        void onRepositoryRemoveError(int errorResId);
    }

    public static void removeRepository(final Repository repository, final OnRepositoryRemovedListener listener)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                if (DataController.getSingleton().removeRepository(repository.getRepoLocalId()))
                {
                    listener.onRepositoryRemoved();
                } else
                {
                    listener.onRepositoryRemoveError(R.string.remove_repo_error);
                }
            }
        }).start();
    }

    static private int getNextRepoLocalId()
    {
        List<Repository> linkedRepositories = DataController.getSingleton().getLinkedRepositories();

        int nextId = 0;
        boolean idUsed;
        do
        {
            ++nextId;
            idUsed = false;
            for (Repository repo : linkedRepositories)
            {
                if (repo.getRepoLocalId() == nextId)
                {
                    idUsed = true;
                    break;
                }
            }

        } while (idUsed);

        return nextId;
    }

    private static boolean saveNewRepository(Repository newRepository)
    {
        return DataController.getSingleton().saveNewRepository(newRepository);
    }

    private static boolean isDomainValid(String domain)
    {
        return domain.contains(".") && !domain.contains("/") && !domain.contains(":") && !domain.contains(" ");
    }
}
