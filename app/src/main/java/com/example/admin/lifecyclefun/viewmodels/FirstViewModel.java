package com.example.admin.lifecyclefun.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.admin.lifecyclefun.api.AppExecutors;
import com.example.admin.lifecyclefun.api.GithubService;
import com.example.admin.lifecyclefun.models.Project;
import com.example.admin.lifecyclefun.repos.ProjectsRepository;
import com.example.admin.lifecyclefun.utils.LiveDataCallAdapterFactory;
import com.example.admin.lifecyclefun.utils.Resource;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Admin on 2018-05-11.
 */

public class FirstViewModel extends ViewModel {

    private static final String TAG = "FirstViewModel";

    private ProjectsRepository projectsRepository;
    /*
    *  Pro-Tips:
    *  AVOID references to the views from the ViewModel class
    *  Reason: The lifecycle of the View model may be longer than the view (Activity or Fragment) itself
    *
    * */

    private MutableLiveData<Project> mData = new MutableLiveData<>();
    private ExecutorService mExecutor = Executors.newFixedThreadPool(5);

    public FirstViewModel() {
        this.projectsRepository = new ProjectsRepository(provideGithubService(), new AppExecutors());
    }


    public LiveData<Project> fetchMainData() {

        Log.e(TAG, "Fetching your data......");
        return mData;
    }

    public LiveData<Resource<List<Project>>> getUserProjects(String userName){
        Log.e(TAG, "getUserPorjects() inside ViewModel");
        return projectsRepository.getUserProjects(userName);
    }



    @Override
    protected void onCleared() {
        super.onCleared();
        Log.e(TAG, "onCleared() >> The view model has been cleared");
    }

    private GithubService provideGithubService() {
        return new Retrofit.Builder()
                .baseUrl(GithubService.HTTPS_API_GITHUB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(GithubService.class);
    }
}
