package com.example.chen.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.chen.fzu.R;
import com.example.chen.start.Login;

public class Account_Information extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__information);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        TextView title=(TextView)findViewById(R.id.titlebar_title_tv);
        title.setText("账号信息");

        //获取控件
        TextView InputName=(TextView)findViewById(R.id.InputName);
        TextView InputSex=(TextView)findViewById(R.id.InputSex);
        TextView InputBirth=(TextView)findViewById(R.id.InputBirth);
        TextView InputNo=(TextView)findViewById(R.id.InputNo);
        TextView InputAcademy=(TextView)findViewById(R.id.InputAcademy);
        TextView InputMajor=(TextView)findViewById(R.id.InputMajor);
        TextView InputEmail=(TextView)findViewById(R.id.InputEmail);
        TextView InputTele=(TextView)findViewById(R.id.InputTelephone);

        //向控件写入数据，显示在屏幕上
        InputName.setText(Login.userName);
        InputSex.setText(Login.userSex);
        InputBirth.setText(Login.userBirth);
        InputNo.setText(Login.userNo);
        InputAcademy.setText(Login.userAcademy);
        InputMajor.setText(Login.userMajor);
        InputEmail.setText(Login.userEmail);
        InputTele.setText(Login.userTele);

        //返回按钮监听,返回到设置页面
        ImageButton back=(ImageButton)findViewById(R.id.backImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Account_Information.this,Setting.class);
                startActivity(intent);
                Account_Information.this.finish();
            }
        });

        //修改按钮控件监听
        Button update=(Button)findViewById(R.id.modify);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Account_Information.this,Information_Update.class);
                startActivity(intent);
                finish();
            }
        });

    }
    //重写onKeyDown方法,对按键(不一定是返回按键)监听
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            Intent intent = new Intent(Account_Information.this, Setting.class);
            startActivity(intent);
            Account_Information.this.finish();
        }
        return false;
    }
}
