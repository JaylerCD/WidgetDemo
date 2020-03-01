package com.jayler.widget.activity;

import android.os.Bundle;
import android.view.View;

import com.jayler.widget.base.BaseActivity;
import com.jayler.widget.R;
import com.jayler.widget.wavesidebar.WaveSideBarViewActivity;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_side_bar).setOnClickListener(this);
        findViewById(R.id.btn_progress_bar).setOnClickListener(this);
        findViewById(R.id.btn_refresh).setOnClickListener(this);
        findViewById(R.id.btn_customize).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_side_bar:
                start(this, WaveSideBarViewActivity.class);
                break;
            case R.id.btn_progress_bar:
                start(this, ProgressBarActivity.class);
                break;
            case R.id.btn_refresh:
                start(this, RefreshListActivity.class);
                break;
            case R.id.btn_customize:
                start(this, CustomizeViewActivity.class);
                break;
        }
    }
}
