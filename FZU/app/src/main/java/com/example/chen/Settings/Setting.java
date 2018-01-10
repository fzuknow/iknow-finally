package com.example.chen.Settings;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chen.fzu.Home_Page;
import com.example.chen.fzu.R;
import com.example.chen.start.Login;

import java.util.Set;

public class Setting extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        TextView title=(TextView)findViewById(R.id.titlebar_title_tv);
        title.setText("设置");
        initView();
        //返回按钮监听,返回到主页
        ImageButton back=(ImageButton)findViewById(R.id.backImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Setting.this,Home_Page.class);
                startActivity(intent);
                finish();
            }
        });
    }
    //重写onKeyDown方法,对按键(不一定是返回按键)监听
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            Setting.this.finish();//结束当前Activity
            Intent intent=new Intent(Setting.this,Home_Page.class);
            startActivity(intent);
        }
        return false;
    }

    private Button button_1;
    private Button button_2;
    private Button button_3;
    private Button button_4;
    private Button button_5;
    private Button button_6;
    private Button button_7;
    private Button button_8;
    private ImageButton imbutton1;
    private ImageButton imbutton2;
    private ImageButton imbutton3;
    private ImageButton imbutton4;
    private ImageButton imbutton5;
    private ImageButton imbutton6;
    private ImageButton imbutton7;
    private ImageButton imbutton8;
private Switch off;
    private void initView() {
        //找到8个按钮
        button_1 = (Button) findViewById(R.id.account_information);
        button_2 = (Button) findViewById(R.id.Safe);
        button_3 = (Button) findViewById(R.id.noticeSet);
        button_4 = (Button) findViewById(R.id.Exit);
        button_5 = (Button) findViewById(R.id.night);
        button_6 = (Button) findViewById(R.id.About);
        button_7= (Button) findViewById(R.id.suggestion);
        button_8 = (Button) findViewById(R.id.Version);
        imbutton1=(ImageButton)findViewById(R.id.inforImageButton);
        imbutton2=(ImageButton)findViewById(R.id.safeImageButton);
        imbutton3=(ImageButton)findViewById(R.id.noticeImageButton);
        imbutton4=(ImageButton) findViewById(R.id.exitImageButton);
        off=(Switch) findViewById(R.id.On_Off3);
        imbutton6=(ImageButton)findViewById(R.id.aboutImageButton);
        imbutton7=(ImageButton)findViewById(R.id.suggesImageButton);
        imbutton8=(ImageButton) findViewById(R.id.versionImageButton);
        //设置按钮点击监听
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);
        button_6.setOnClickListener(this);
        button_7.setOnClickListener(this);
        button_8.setOnClickListener(this);
        imbutton1.setOnClickListener(this);
        imbutton2.setOnClickListener(this);
        imbutton3.setOnClickListener(this);
        imbutton4.setOnClickListener(this);
        off.setOnClickListener(this);
        imbutton6.setOnClickListener(this);
        imbutton7.setOnClickListener(this);
        imbutton8.setOnClickListener(this);


    }

    //点击事件处理
    @Override
    public void onClick(View v) {
       if(v.getId()==R.id.inforImageButton||v.getId()==R.id.account_information) {//帐号信息
           Intent intent = new Intent(Setting.this, Account_Information.class);
           startActivity(intent);
           finish();
       }else if(v.getId()==R.id.safeImageButton||v.getId()==R.id.Safe) {//隐私与安全
           Intent intent1 = new Intent(Setting.this, Privacy_Security.class);
           startActivity(intent1);
           finish();
       }else if(v.getId()==R.id.noticeImageButton||v.getId()==R.id.noticeSet) {//通知管理
           Intent intent2 = new Intent(Setting.this, Natification_Management.class);
           startActivity(intent2);
           finish();
       }else if(v.getId()==R.id.On_Off3||v.getId()==R.id.night) {//夜间模式
           Toast.makeText(this,"开发中",Toast.LENGTH_LONG).show();
       }
       else if(v.getId()==R.id.aboutImageButton||v.getId()==R.id.About) {//关于我们
            Intent intent=new Intent(Setting.this,AboutUs.class);
           startActivity(intent);
           finish();
       }else if(v.getId()==R.id.suggesImageButton||v.getId()==R.id.suggestion) {//意见反馈
           Intent intent = new Intent(Setting.this, Suggestions.class);
           startActivity(intent);
           finish();
       }else if(v.getId()==R.id.versionImageButton||v.getId()==R.id.Version) {//版本更新
//           Dialog  dialog = new Dialog(this);
//           dialog.setTitle("当前已经是最新版本");
//           dialog.show();//android 7.0以下可以显示
           AlertDialog.Builder dlg=new AlertDialog.Builder(Setting.this);
           dlg.setTitle("版本更新");
           dlg.setMessage("当前已经是最新版本！");
           dlg.show();
       }else if(v.getId()==R.id.exitImageButton||v.getId()==R.id.Exit) {//退出帐号
           Intent intent3 = new Intent(Setting.this, Login.class);
           intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
           intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           startActivity(intent3);
           finish();
       }
    }
    @Override
    public Resources getResources(){
        Resources resources=super.getResources();
        Configuration configuration=resources.getConfiguration();
        configuration.fontScale=1;
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
        return resources;
    }
}
