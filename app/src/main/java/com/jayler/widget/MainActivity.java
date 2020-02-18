package com.jayler.widget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jayler.widget.widget.AliProgressBar;
import com.jayler.widget.widget.NumberProgressBar;
import com.jayler.widget.widget.OnProgressBarListener;
import com.jayler.widget.widget.refresh.RefreshableView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements OnProgressBarListener {
    private Timer timer;

    private NumberProgressBar bnp;
    private AliProgressBar aliProgressBar;
    int progress;

    RefreshableView refreshableView;
    ListView listView;
    ArrayAdapter<String> adapter;
    String[] items = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "F", "G", "H", "I", "J", "K", "L" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnp = (NumberProgressBar)findViewById(R.id.numberbar1);
        aliProgressBar = findViewById(R.id.AliProgressBar);
        aliProgressBar.setProgress(200);
        bnp.setOnProgressBarListener(this);
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


        refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
        listView = (ListView) findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        }, 0);

    }

    @Override
    public void onProgressChange(int current, int max) {

    }
}
