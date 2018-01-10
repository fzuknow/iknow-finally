package com.example.chen.fzu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.chen.start.*;
/**
 * Created by laixl on 2017/11/9.
 */

public class Experience extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wealth);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        TextView title=(TextView)findViewById(R.id.titlebar_title_tv);
        title.setText("财富值");
        //返回按钮监听,返回到主页
        ImageButton back=(ImageButton)findViewById(R.id.backImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Experience.this,Home_Page.class);
                startActivity(intent);
                finish();
            }
        });
        TextView InputXp=(TextView)findViewById(R.id.xp);
        TextView Inputlv=(TextView)findViewById(R.id.lv);
        TextView InputWealthContent=(TextView)findViewById(R.id.WealthContent);
        TextView InputLvContent=(TextView)findViewById(R.id.LvContent);
        ProgressBar mprogress=(ProgressBar)findViewById(R.id.progress_horizontal);
        InputXp.setText(Login.userXp);
        Inputlv.setText("Lv."+Login.userLv);
        mprogress.setProgress(Login.xp);
        InputWealthContent.setText("财富值说明："+"\n"+"财富值可由签到以及回答悬赏问题获得，可用于悬赏问题的提问，让你的问题更快被解答。");
        InputLvContent.setText("等级说明："+"\n"+"经验值满100后等级+1"+"\n"+"lv0：0-100"+"\n"+"lv1:101-200"+"\n"+"lv2:201-300"+"\n"+"lv3:301-400"+"\n"+"...");
    }
}
