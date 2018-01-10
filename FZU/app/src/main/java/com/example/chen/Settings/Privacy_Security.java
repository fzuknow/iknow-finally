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

public class Privacy_Security extends AppCompatActivity implements View.OnClickListener{
    TextView OldPass,NewPass,ConfirmPass;
    Button update;
    String result;
    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message message) {
            result = (String) message.obj;
            JSONObject resultJson = JSONObject.parseObject(result);
            String finalResult = resultJson.getString("result");
            System.out.println("结果是：" + finalResult);
            if (finalResult.equals("密码修改成功")) {
                //修改成功
                Intent intent = new Intent(Privacy_Security.this, Setting.class);
                startActivity(intent);
                finish();
            } else{
                Toast.makeText(Privacy_Security.this, "密码修改失败！！", Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy__security);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        TextView title=(TextView)findViewById(R.id.titlebar_title_tv);
        title.setText("隐私与安全");
        //返回按钮监听,返回到设置页面
        ImageButton back=(ImageButton)findViewById(R.id.backImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Privacy_Security.this,Setting.class);
                startActivity(intent);
                finish();
            }
        });
        update=(Button)findViewById(R.id.ModifyPass);
        OldPass=(TextView)findViewById(R.id.EnterOld);
        NewPass=(TextView)findViewById(R.id.EnterNew);
        ConfirmPass=(TextView)findViewById(R.id.ConfirmEnter);
        update.setOnClickListener(this);
    }
    //重写onKeyDown方法,对按键(不一定是返回按键)监听
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            Privacy_Security.this.finish();//结束当前Activity
            Intent intent=new Intent(Privacy_Security.this,Setting.class);
            startActivity(intent);
            finish();
        }
        return false;
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.ModifyPass){
            //获取旧密码
            String oldPass = OldPass.getText().toString();
            // 获取新密码
            String newPass = NewPass.getText().toString();
            //确认密码
            String confirmPass=ConfirmPass.getText().toString();
            final Map<String, Object> userInfo = new HashMap<String, Object>();
            userInfo.put("oldPass", oldPass);
            userInfo.put("newPass", newPass);
            final String user = "userId="+ Login.userId+"&oldPass=" + oldPass + "&newPass=" + newPass;
            System.out.println(user);
            if(newPass.equals(confirmPass)==false){
                Toast.makeText(Privacy_Security.this,"两次密码输入不一致",Toast.LENGTH_LONG).show();
            }else if(OldPass.getText().length()<6){
                Toast.makeText(Privacy_Security.this,"密码长度过短",Toast.LENGTH_LONG).show();
            }else if(OldPass.getText().length()>16){
                Toast.makeText(Privacy_Security.this,"密码长度过长",Toast.LENGTH_LONG).show();
            } else{
                ConnectServer.Connect(NetUtil.PATH_USER_UpdatePass, user, mHanlder);
            }
        }
    }
}
