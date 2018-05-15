package com.example.admin.lifecyclefun.repos;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.admin.lifecyclefun.api.AppExecutors;
import com.example.admin.lifecyclefun.api.GithubService;
import com.example.admin.lifecyclefun.models.Project;
import com.example.admin.lifecyclefun.utils.AbsentLiveData;
import com.example.admin.lifecyclefun.utils.ApiResponse;
import com.example.admin.lifecyclefun.utils.NetworkBoundResource;
import com.example.admin.lifecyclefun.utils.Resource;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Admin on 2018-05-14.
 */

public class ProjectsRepository {


    private static final String TAG = "FirstViewModel";

    private GithubService githubService;

    //private MutableLiveData<List<Project>> mData = new MutableLiveData<>();
    private MutableLiveData<List<Project>> mData = new MutableLiveData<>();


    LiveData<List<Project>> projectList = new LiveData<List<Project>>() {
        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<List<Project>> observer) {
            super.observe(owner, observer);
        }
    };

    private AppExecutors appExecutors;


    public ProjectsRepository(GithubService githubService, AppExecutors appExecutors) {
        this.githubService = githubService;
        this.appExecutors = appExecutors;
    }

    // Temp ResultType
    private List<Project> dummyResult;

    public LiveData<Resource<List<Project>>> getUserProjects(String userName) {

        //return githubService.getProjectList(userName);
        Log.e(TAG, "getUserPorjects() inside Repository");


        return new NetworkBoundResource<List<Project>, List<Project>>() {

            @Override
            protected void saveCallResult(@NonNull List<Project> item) {
                Log.e(TAG, "inside saveCallResult()");
                // if you don't care about order
                dummyResult = item;

            }

            @Override
            protected boolean shouldFetch(@Nullable List<Project> data) {
                Log.e(TAG, "Inside should fetch");
                return true;
            }


            @NonNull
            @Override
            protected LiveData<List<Project>> loadFromDb() {
                Log.e(TAG, "Loading from local db");
                if (dummyResult == null) {
                    return AbsentLiveData.create();
                } else {
                    return new LiveData<List<Project>>() {
                        @Override
                        protected void onActive() {
                            super.onActive();
                            setValue(dummyResult);
                        }
                    };
                }
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Project>>> createCall() {
                Log.d(TAG, "Fetching from Network");
                return githubService.getProjectList(userName);
            }

            @Override
            protected void onFetchFailed() {
                super.onFetchFailed();
                Log.d(TAG, "onFetchFailed");
            }
        }.getAsLiveData();
    }
}
