package com.example.admin.lifecyclefun;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.admin.lifecyclefun.api.GithubService;
import com.example.admin.lifecyclefun.models.Project;
import com.example.admin.lifecyclefun.utils.Resource;
import com.example.admin.lifecyclefun.viewmodels.FirstViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.tv_data_update)
    TextView tvDataUpdate;

    MyLifecycleActivity myLifecycleActivity;
    public FirstViewModel firstViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        firstViewModel = ViewModelProviders.of(this).get(FirstViewModel.class);
        myLifecycleActivity = new MyLifecycleActivity(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        showProjects();
    }

    private void showProjects() {

        firstViewModel.getUserProjects("google").observe(this, listResource -> {
            if (listResource != null && listResource.data!= null) {
                for (Project project :
                        listResource.data) {

                    tvDataUpdate.append("\n" + listResource.data.indexOf(project) + " " +project.full_name );
                    Log.e(TAG, project.full_name);
                }
            };
        });
    }
}
