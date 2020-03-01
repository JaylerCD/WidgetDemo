package com.jayler.widget.activity;

import android.os.Bundle;

import com.jayler.widget.R;
import com.jayler.widget.base.BaseActivity;
import com.jayler.widget.widget.AliProgressBar;
import com.jayler.widget.widget.NumberProgressBar;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;

public class ProgressBarActivity extends BaseActivity {
    private Timer timer;
    private NumberProgressBar bnp;
    private AliProgressBar aliProgressBar;
    int progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);

        bnp = findViewById(R.id.numberbar1);
        aliProgressBar = findViewById(R.id.AliProgressBar);
        aliProgressBar.setProgress(200);
        bnp.setOnProgressBarListener(null);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bnp.incrementProgressBy(1);

                        progress += 1;
                    }
                });
            }
        }, 1000, 100);
    }
}
