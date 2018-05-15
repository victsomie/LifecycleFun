package com.example.admin.lifecyclefun.utils;

import android.arch.lifecycle.LiveData;

/**
 * Created by Admin on 2018-05-15.
 */

public class AbsentLiveData extends LiveData{
    private  AbsentLiveData() {

        postValue(null);
    }
    public static <T> LiveData<T> create() {
        //noinspection unchecked
        return new AbsentLiveData();
    }
}
