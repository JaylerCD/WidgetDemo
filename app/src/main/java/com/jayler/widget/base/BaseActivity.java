package com.jayler.widget.base;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    public static void start(Context context, Class clazz){
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {

    }
}
