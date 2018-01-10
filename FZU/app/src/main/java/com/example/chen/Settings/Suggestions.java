package com.example.chen.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.chen.tools.ConnectServer;
import com.example.chen.fzu.R;
import com.example.chen.http.NetUtil;
import com.example.chen.start.Login;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by laixl on 2018/1/7.
 */

public class Suggestions extends AppCompatActivity {
    Button send;
    ImageButton back;
    EditText inputSuggestion;
    String result;
    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message message) {
            Intent intent=new Intent(Suggestions.this,Setting.class);
            startActivity(intent);
            finish();
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        TextView title=(TextView)findViewById(R.id.titlebar_title_tv);
        title.setText("意见反馈");
        //返回按钮监听,返回到设置页
        back=(ImageButton)findViewById(R.id.backImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Suggestions.this,Setting.class);
                startActivity(intent);
                finish();
            }
        });
        send=(Button)findViewById(R.id.send);
        inputSuggestion=(EditText)findViewById(R.id.suggestion);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputSuggestion.getText().length()<10){
                    Toast.makeText(Suggestions.this,"反馈意见少于10个字",Toast.LENGTH_LONG).show();
                }else{
                    String content=inputSuggestion.getText().toString();
                    final Map<String, Object> userInfo = new HashMap<String, Object>();
                    userInfo.put("studentId", Login.userId);
                    userInfo.put("content",content);
                    final String user = "studentId=" + Login.userId+"&content="+content;
                    ConnectServer.Connect(NetUtil.PATH_Insert_Suggest, user,mHanlder);
                }
            }
        });
    }
    //重写onKeyDown方法,对按键(不一定是返回按键)监听
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            Intent intent = new Intent(Suggestions.this, Setting.class);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
