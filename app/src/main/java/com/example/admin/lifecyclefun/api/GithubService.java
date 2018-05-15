package com.example.admin.lifecyclefun.api;

import android.arch.lifecycle.LiveData;

import com.example.admin.lifecyclefun.models.Project;
import com.example.admin.lifecyclefun.utils.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Admin on 2018-05-14.
 */

public interface GithubService {

    String HTTPS_API_GITHUB_URL = "https://api.github.com/";

    @Headers("Content-Type: application/json")
    @GET("users/{user}/repos?page=7&per_page=700")
    LiveData<ApiResponse<List<Project>>> getProjectList(@Path("user") String user);

    // @GET("repos/{user}/{reponame}")
    @GET("users/{user}/repos/{reponame}")
    Call<Project> getProjectDetails(@Path("user") String user, @Path("reponame") String projectName);

}
