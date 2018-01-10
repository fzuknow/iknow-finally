package com.example.chen.interlocution;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
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


public class Ask_Question extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    TextView queContent;
    String result;
    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message message) {
            result = (String) message.obj;
            JSONObject resultJson = JSONObject.parseObject(result);
            String finalResult = resultJson.getString("result");
            if (finalResult.equals("成功插入问题")) {
                //插入成功,跳转到最新问题界面
                setResult(10);
                finish();
                Toast.makeText(Ask_Question.this, "问题发布成功", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(Ask_Question.this, "问题发布失败，请稍后重试", Toast.LENGTH_SHORT).show();
            }
        }
    };

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ask__question);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        TextView title=(TextView)findViewById(R.id.titlebar_title_tv);
        title.setText("提问");
        //返回按钮监听
        ImageButton back=(ImageButton)findViewById(R.id.backImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(10);
                finish();
            }
        });
        button = (Button) findViewById(R.id.button);
        queContent=(TextView)findViewById(R.id.edittext);
        button.setOnClickListener(this);
    }
    //重写onKeyDown方法,对按键(不一定是返回按键)监听
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            setResult(10);
            finish();
        }
        return false;
    }
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            if (queContent.getText().length() <= 0) {
                Toast.makeText(Ask_Question.this,"请输入问题！",Toast.LENGTH_LONG).show();
            } else {
                final String content = queContent.getText().toString();
                final Map<String, Object> userInfo = new HashMap<String, Object>();
                userInfo.put("userId", Login.userId);
                final String user = "userId=" + Login.userId + "&content=" + content;
                ConnectServer.Connect(NetUtil.PATH_USER_INSERTQUE, user,mHanlder);
            }
        }

    }
}
