package com.example.admin.lifecyclefun;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.admin.lifecyclefun.viewmodels.FirstViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.tv_data_update)
    TextView tvDataUpdate;

    MyLifecycleActivity myLifecycleActivity;
    FirstViewModel firstViewModel;

    private final Observer<String> dataObserver = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
            Log.e(TAG, s);
            tvDataUpdate.setText(s);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        firstViewModel = ViewModelProviders.of(this).get(FirstViewModel.class);
        myLifecycleActivity = new MyLifecycleActivity(this);

        // firstViewModel.fetchMainData().observe(this, dataObserver);

        firstViewModel.fetchMainData().observe(this, dataObserver);

        updateView();
    }

    private void updateView() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms

                firstViewModel.addData("This String was added later");
            }
        }, 3000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                firstViewModel.addData("This was the last updated");
            }
        }, 6000);
    }
}
