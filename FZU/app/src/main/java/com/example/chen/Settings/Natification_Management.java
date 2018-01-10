package com.example.chen.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.chen.fzu.R;

public class Natification_Management extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_natification__management);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        TextView title=(TextView)findViewById(R.id.titlebar_title_tv);
        title.setText("通知设置");
        //返回按钮监听,返回到设置页面
        ImageButton back=(ImageButton)findViewById(R.id.backImage);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Natification_Management.this, Setting.class);
                startActivity(intent);
                finish();
            }
        });

    }
    //重写onKeyDown方法,对按键(不一定是返回按键)监听
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            Natification_Management.this.finish();//结束当前Activity
            Intent intent=new Intent(Natification_Management.this, Setting.class);
            startActivity(intent);
            finish();
            }
        return false;
    }
}
