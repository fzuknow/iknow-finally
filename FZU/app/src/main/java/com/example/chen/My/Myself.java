package com.example.chen.My;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chen.fzu.Home_Page;
import com.example.chen.fzu.R;

public class Myself extends AppCompatActivity implements View.OnClickListener{
    private ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myself);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        TextView title = (TextView) findViewById(R.id.titlebar_title_tv);
        title.setText("我的");
        back = (ImageButton) findViewById(R.id.backImage);
        //返回到主页面
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Myself.this,Home_Page.class);
                startActivity(intent);
                finish();
            }
        });
        initView();
    }
    private Button button_1;
    private Button button_3;
    private Button button_4;


    private void initView(){
        //找到四个按钮
        button_1 = (Button) findViewById(R.id.my_ask);
        button_3 = (Button) findViewById(R.id.my_praise);
        button_4 = (Button) findViewById(R.id.my_look);

        //设置按钮点击监听
        button_1.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
    }

    //点击事件处理
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.my_ask) {
            Intent intent=new Intent(Myself.this,My_Ask.class);
            startActivity(intent);
            finish();
        }  else if (v.getId() == R.id.my_praise) {
            Toast.makeText(this, "开发中", Toast.LENGTH_LONG).show();
        } else if (v.getId() == R.id.my_look) {
            Toast.makeText(this, "开发中", Toast.LENGTH_LONG).show();
        }
    }
}
