package com.example.chen.My;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.chen.fzu.R;

public class My_Look extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__look);
        getSupportActionBar().setTitle("最近浏览");
    }
}
