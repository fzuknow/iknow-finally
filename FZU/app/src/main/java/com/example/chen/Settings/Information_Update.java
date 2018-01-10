package com.example.chen.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
 * Created by laixl on 2017/11/25.
 */

public class Information_Update  extends AppCompatActivity {

    String result;
    String name,sex,birth,academy,major,email,no;
    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message message) {
            result = (String) message.obj;
            JSONObject resultJson = JSONObject.parseObject(result);
            String finalResult = resultJson.getString("result");
            System.out.println("结果是：" + finalResult);
            if (finalResult.equals("个人信息修改成功")) {
                //修改成功
                Login.userName = name;
                Login.userNo = no;
                Login.userSex =sex;
                Login.userAcademy = academy;
                Login.userMajor = major;
                Login.userEmail = email;
                Login.userBirth = birth;
                Toast.makeText(Information_Update.this, "信息修改成功！！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Information_Update.this, Account_Information.class);
                startActivity(intent);
                finish();
            } else{
                Toast.makeText(Information_Update.this, "信息修改失败！！", Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_informtion_update);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }

        TextView title=(TextView)findViewById(R.id.titlebar_title_tv);
        title.setText("信息修改");
        //获取控件
        final EditText InputName = (EditText) findViewById(R.id.InputName);
        final EditText InputSex = (EditText) findViewById(R.id.InputSex);
        final EditText InputBirth = (EditText) findViewById(R.id.InputBirth);
        final EditText InputNo = (EditText) findViewById(R.id.InputNo);
        final EditText InputAcademy = (EditText) findViewById(R.id.InputAcademy);
        final EditText InputMajor = (EditText) findViewById(R.id.InputMajor);
        final EditText InputEmail = (EditText) findViewById(R.id.InputEmail);
        TextView InputTele = (TextView) findViewById(R.id.InputTelephone);

        //向控件写入数据，显示在屏幕上
        InputName.setText(Login.userName);
        InputSex.setText(Login.userSex);
        InputBirth.setText(Login.userBirth);
        InputNo.setText(Login.userNo);
        InputAcademy.setText(Login.userAcademy);
        InputMajor.setText(Login.userMajor);
        InputEmail.setText(Login.userEmail);
        InputTele.setText(Login.userTele);

        //返回按钮监听,返回到账号信息页面
        ImageButton back=(ImageButton)findViewById(R.id.backImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Information_Update.this,Account_Information.class);
                startActivity(intent);
                Information_Update.this.finish();
            }
        });
        //修改按钮控件监听
        Button sure=(Button)findViewById(R.id.modifySure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map<String, Object> userInfo = new HashMap<String, Object>();
                name=InputName.getText().toString();
                sex=InputSex.getText().toString();
                birth=InputBirth.getText().toString();
                no=InputNo.getText().toString();
                academy=InputAcademy.getText().toString();
                major=InputMajor.getText().toString();
                email=InputEmail.getText().toString();

                userInfo.put("userId", Login.userId);
                userInfo.put("name", name);
                userInfo.put("sex", sex);
                userInfo.put("birth", birth);
                userInfo.put("no", no);
                userInfo.put("academy", academy);
                userInfo.put("major", major);
                userInfo.put("email", email);
//                final String user = "userId="+Login.userId+"&name="+name;
                final String user = "userId="+Login.userId+"&name="+name+"&sex="+sex+"&birth="+birth+"&no="+no+"&academy="+academy+"&major="+major+"&email="+email;
                System.out.println(user);
                ConnectServer.Connect(NetUtil.PATH_USER_Updateinfor, user,mHanlder);
                }

        });
    }

    //重写onKeyDown方法,对按键(不一定是返回按键)监听
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            Information_Update.this.finish();
            Intent intent = new Intent(Information_Update.this, Account_Information.class);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
