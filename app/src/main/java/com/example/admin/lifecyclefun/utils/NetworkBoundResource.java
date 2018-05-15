package com.example.admin.lifecyclefun.utils;

/**
 * Created by Admin on 2018-05-12.
 */

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.example.admin.lifecyclefun.api.AppExecutors;
import com.example.admin.lifecyclefun.models.Project;

import java.util.List;
import java.util.Objects;

/**
 * Some guideline of HOW TO USE THIS CLASS
 * https://proandroiddev.com/build-an-app-with-offline-support-exposing-network-states-1e09e138b1ed
 */

// ResultType: Type for the Resource data
// RequestType: Type for the API response
public abstract class NetworkBoundResource<ResultType, RequestType> {
    //private final AppExecutors appExecutors ;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();
    private static final String TAG = "NetworkBoundResource";
    @MainThread
    public NetworkBoundResource() {
        Log.e(TAG, "Inside network bound resource");
        result.setValue(Resource.loading(null));
        LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource,
                        newData -> result.setValue(Resource.success(newData)));
            }
        });
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        Log.e(TAG, "Inside fetchFromNetwork()");
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        // we re-attach dbSource as a new source,
        // it will dispatch its latest value quickly
        result.addSource(dbSource,
                newData -> result.setValue(Resource.loading(newData)));
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            result.removeSource(dbSource);
            //noinspection ConstantConditions
            if (response.isSuccessful()) {
                Log.e(TAG, "fetchFromNetwork() successful! - " + dbSource.getValue());
                saveResultAndReInit(response);
            } else {
                Log.e(TAG, "fetchFromNetwork() failed! - " + dbSource.getValue());
                onFetchFailed();
                result.addSource(dbSource,
                        newData -> result.setValue(
                                Resource.error(response.errorMessage.Message, newData)));
            }
        });
    }

    @MainThread
    private void saveResultAndReInit(ApiResponse<RequestType> response) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                saveCallResult(response.body);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                // we specially request a new live data,
                // otherwise we will get immediately last cached value,
                // which may not be updated with latest results received from network.
                result.addSource(loadFromDb(),
                        newData -> result.setValue(Resource.success(newData)));
            }
        }.execute();
    }

    // Called to save the result of the API response into the database
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    // Called with the data in the database to decide whether it should be
    // fetched from the network.
    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    // Called to get the cached data from the database
    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    // Called to create the API call.
    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    @MainThread
    protected void onFetchFailed() {
    }

    // returns a LiveData that represents the resource, implemented
    // in the base class.
    public LiveData<Resource<ResultType>> getAsLiveData(){
        return result;
    };
}

