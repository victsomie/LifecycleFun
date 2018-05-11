package com.example.admin.lifecyclefun.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Admin on 2018-05-11.
 */

public class FirstViewModel extends ViewModel {

    private static final String TAG = "FirstViewModel";
    /*
    *  Pro-Tips:
    *  AVOID references to the views from the ViewModel class
    *  Reason: The lifecycle of the View model may be longer than the view (Activity or Fragment) itself
    *
    * */

    private MutableLiveData<String> mData = new MutableLiveData<>();
    ExecutorService mExecutor = Executors.newFixedThreadPool(5);

    public LiveData<String> fetchMainData() {
        Log.e(TAG, "Fetching your data......");
        return mData;
    }


    public void addData(final String myString) {
        mExecutor.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        mData.postValue(myString);
                    }
                }
        );

    }


    @Override
    protected void onCleared() {
        super.onCleared();
        Log.e(TAG, "onCleared() >> The view model has been cleared");
    }
}
