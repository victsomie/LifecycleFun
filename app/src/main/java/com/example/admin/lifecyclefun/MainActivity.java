package com.example.admin.lifecyclefun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity{

    MyLifecycleActivity myLifecycleActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLifecycleActivity = new MyLifecycleActivity(this);
    }
}
