package com.example.chen.interlocution;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import static com.example.chen.interlocution.Latest_Question.clickPosition;
import static com.example.chen.interlocution.Latest_Question.mlist;
import static com.example.chen.interlocution.Latest_Question.que;

/**
 * Created by laixl on 2017/12/21.
 */

public class Comment extends AppCompatActivity{

    EditText Inputcomment;
    String result;
    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(android.os.Message message) {
            result = (String) message.obj;
            JSONObject resultJson = JSONObject.parseObject(result);
            String finalResult = resultJson.getString("result");

            if (finalResult.equals("评论成功")) {
                //插入成功
                Intent intent=new Intent(Comment.this,Question_Detail.class);
                startActivity(intent);
                finish();
                Toast.makeText(Comment.this, "发表评论成功", Toast.LENGTH_SHORT).show();
                que[clickPosition][3]=String.valueOf(Integer.parseInt(que[clickPosition][3].toString())+1);
                mlist.get(clickPosition).put("commentNum",que[clickPosition][3]);
            } else{

                Toast.makeText(Comment.this, "发表评论失败", Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Button send=(Button)findViewById(R.id.send);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        TextView title=(TextView)findViewById(R.id.titlebar_title_tv);
        title.setText("发表评论");
        //返回按钮监听,返回到最新问题页面
        ImageButton back=(ImageButton)findViewById(R.id.backImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(10);
               finish();
            }
        });

        Inputcomment=(EditText)findViewById(R.id.InputComment);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Inputcomment.getText().length() <= 0) {
                    Toast.makeText(Comment.this, "请输入评论！", Toast.LENGTH_LONG).show();
                } else if (Inputcomment.getText().length() > 50) {
                    Toast.makeText(Comment.this, "评论字数超过50字！", Toast.LENGTH_LONG).show();
                } else {
                    String content = Inputcomment.getText().toString();
                    System.out.println(content);
                    final Map<String, Object> userInfo = new HashMap<String, Object>();
                    System.out.println(content);
                    userInfo.put("studentId", Login.userId);
                    userInfo.put("content", content);
                    System.out.println(content);
                    final String user = "studentId=" + Login.userId + "&questionId=" + que[clickPosition][5] + "&content=" + content;
                    System.out.println(user);
                    ConnectServer.Connect(NetUtil.Path_Insert_Comment, user, mHanlder);
                    Toast.makeText(Comment.this, "发表评论中，请稍后....", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //重写onKeyDown方法,对按键(不一定是返回按键)监听
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            setResult(10);
          finish();
        }
        return false;
    }
}
