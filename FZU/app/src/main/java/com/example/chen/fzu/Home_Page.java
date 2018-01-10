package com.example.chen.fzu;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.chen.My.Myself;
import com.example.chen.Settings.Setting;
import com.example.chen.interlocution.Ask_Question;
import com.example.chen.interlocution.Latest_Question;
import com.example.chen.interlocution.Question_Answer;
import com.example.chen.message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Home_Page extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__page);


        //初始化页面
        initView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("福大知道");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==10){
            FragmentTransaction fragmentTransaction=this.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.question_deatil, new Latest_Question());
            fragmentTransaction.commit();

        }
        if(requestCode==1&&resultCode==10){
            FragmentTransaction fragmentTransaction=this.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.question_ask, new Latest_Question());
            fragmentTransaction.commit();
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==R.id.sign_in){
            Toast.makeText(this, "开发中", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.fzu_map) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://ditu.amap.com/place/B024F05T6I "));
            startActivity(intent);
        } if(id==R.id.ask_question){
            Intent intent=new Intent(Home_Page.this, Ask_Question.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_myself) {
            Intent intent = new Intent(Home_Page.this, Myself.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_wealthvalue) {
            Intent intent=new Intent(Home_Page.this,Experience.class);
            startActivity(intent);

        } else if (id == R.id.nav_recyclr_bin) {
            Toast.makeText(this, "开发中", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_setting) {

            Intent intent = new Intent(Home_Page.this, Setting.class);
            startActivity(intent);

        } else if (id == R.id.nav_login_out) {
            finish();
            System.exit(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private RadioGroup radioGroup;
    private RadioButton button_1;
    private RadioButton button_2;
    private RadioButton button_3;
    //private RadioButton button_4;
    private FirstPage fragment_1;
    private Question_Answer fragment_2;
    private Message fragment_3;
    //  private Fragment_4 fragment_4;
    private List<Fragment> list;
    private FrameLayout frameLayout;

    Drawable drawable;

    //初始化页面
    private void initView() {
        frameLayout = (FrameLayout) findViewById(R.id.framelayout);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        //找到四个按钮
        button_1 = (RadioButton) findViewById(R.id.button_1);
        button_2 = (RadioButton) findViewById(R.id.button_2);
        button_3 = (RadioButton) findViewById(R.id.button_3);
//         button_4 = (RadioButton) findViewById(R.id.button_4);
//
        Drawable drawable_news = getResources().getDrawable(R.drawable.home_button_selector);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_news.setBounds(0, 0, 48, 48);
        //设置图片在文字的哪个方向
        button_1.setCompoundDrawables(null, drawable_news, null, null);
        //定义底部标签图片大小和位置
        Drawable drawable_live = getResources().getDrawable(R.drawable.question_button_selector);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_live.setBounds(0, 0, 48, 48);
        //设置图片在文字的哪个方向
        button_2.setCompoundDrawables(null, drawable_live, null, null);

        //定义底部标签图片大小和位置
        Drawable drawable_tuijian = getResources().getDrawable(R.drawable.message_button_selector);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_tuijian.setBounds(0, 0, 48, 48);
        //设置图片在文字的哪个方向
        button_3.setCompoundDrawables(null, drawable_tuijian, null, null);



        //创建Fragment对象及集合
        fragment_1 = new FirstPage();
        fragment_2 = new Question_Answer();
        fragment_3 = new Message();

        //将Fragment对象添加到list中
        list = new ArrayList<>();
        list.add(fragment_1);
        list.add(fragment_2);
        list.add(fragment_3);

        //设置RadioGroup开始时设置的按钮，设置第一个按钮为默认值
        radioGroup.check(R.id.button_1);

        //设置按钮点击监听
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);

        //初始时向容器中添加第一个Fragment对象
        addFragment(fragment_1);
    }

    @Override
    public void finish() {
        ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
        viewGroup.removeAllViews();
        super.finish();
    }
    //点击事件处理
    @Override
    public void onClick(View v) {
        //我们根据参数的id区别不同按钮
        //不同按钮对应着不同的Fragment对象页面
        if(v.getId()==R.id.button_1){
            addFragment(fragment_1);
        }else if(v.getId()==R.id.button_2){
            addFragment(fragment_2);
        }else if(v.getId()==R.id.button_3){
            addFragment(fragment_3);
        }
    }

    //向Activity中添加Fragment的方法
    public void addFragment(Fragment fragment) {

        //获得Fragment管理器
        FragmentManager fragmentManager = getSupportFragmentManager();
        //使用管理器开启事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //使用事务替换Fragment容器中Fragment对象
        fragmentTransaction.replace(R.id.framelayout,fragment);
        //提交事务，否则事务不生效
        fragmentTransaction.commit();
    }
    /**
     * 返回键响应,双击退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            exitBy2Click();      //调用双击退出函数
        }
        return false;
    }
    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }
}
