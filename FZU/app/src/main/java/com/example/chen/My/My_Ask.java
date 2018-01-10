package com.example.chen.My;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.chen.entity.Question;
import com.example.chen.tools.ConnectServer;
import com.example.chen.fzu.R;
import com.example.chen.http.NetUtil;
import com.example.chen.start.Login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.chen.interlocution.Latest_Question.mlist;
import static com.example.chen.start.Login.userId;

public class My_Ask extends AppCompatActivity {
    private ListView listview;
    private ArrayList<String> list;
    String result;
    Question question;
    private ImageButton back;
    public static String content, time, name, QuePraiseNum, QueCommentNum;
    public static int clickPosition = -1;
    int MyQueLength;
    public static String Myque[][] = new String[10000][7];

    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(android.os.Message message) {

            result = (String) message.obj;
            JSONArray ResultJson = JSONObject.parseArray(result);
            MyQueLength = ResultJson.size();

            for (int i = 0; i < ResultJson.size(); i++) {

                JSONObject results = ResultJson.getJSONObject(i);
                String finalResult = results.getString("result");
                question = JSON.parseObject(finalResult, Question.class);
                System.out.println("结果是：" + finalResult);
                Myque[i][0] = question.getContent();//第几条问题的内容
                Myque[i][1] = question.getReleaseDate();
                Myque[i][2] = Login.userName;

                Myque[i][3] = String.valueOf(question.getCommentNum());
                Myque[i][4] = String.valueOf(question.getPraiseNum());
                Myque[i][5] = String.valueOf(question.getId());
                System.out.println("内容：" + Myque[i][2]);
            }
            for (int i = 0; i < MyQueLength; i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("nicheng", Myque[i][2]);
                map.put("date", Myque[i][1]);
                map.put("content", Myque[i][0]);
                map.put("praiseNum", Myque[i][4]);
                map.put("commentNum", Myque[i][3]);
                mlist.add(map);
            }
            //没有这个会产生异步显示
            listview.setAdapter(adapter);
        }
    };

    public void MyQuestion() {
        final Map<String, Object> userInfo = new HashMap<String, Object>();
        userInfo.put("userId", userId);
        final String user = "userId=" + userId;
        final String use = "studentId="+Login.userId;
        ConnectServer.Connect(NetUtil.PATH_USER_MyQuestion, user, mHanlder);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__ask);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        TextView title = (TextView) findViewById(R.id.titlebar_title_tv);
        title.setText("我的提问");
        MyQuestion();
        back = (ImageButton) findViewById(R.id.backImage);
        //返回到我的界面
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(My_Ask.this,Myself.class);
                startActivity(intent);
                finish();
            }
        });
        listview = (ListView) findViewById(R.id.listview);
        mlist = new ArrayList<Map<String, Object>>();
        adapter = new MyAdapter();
        adapter.notifyDataSetChanged();
        listview.setAdapter(adapter);
        listview.setTextFilterEnabled(true);
    }
    public static MyAdapter adapter;

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {

            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final MyViewHolder vh;


            if (convertView == null) {
                convertView = View.inflate(My_Ask.this, R.layout.item_myask, null);
                vh = new MyViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (MyViewHolder) convertView.getTag();
            }
            vh.title = (TextView) convertView.findViewById(R.id.title);
            vh.data = (TextView) convertView.findViewById(R.id.date);
            vh.CommentNum = (TextView) convertView.findViewById(R.id.Comment);
            vh.PraiseNum = (TextView) convertView.findViewById(R.id.Praise);
            vh.title.setText((String) mlist.get(position).get("nicheng"));
            vh.data.setText((String) mlist.get(position).get("date"));
            vh.tv_test.setText((String) mlist.get(position).get("content"));
            vh.CommentNum.setText((String) mlist.get(position).get("commentNum"));
            vh.PraiseNum.setText((String) mlist.get(position).get("praiseNum"));

            vh.tv_test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickPosition = position;
                    Map<String, Object> k = mlist.get(position);
                    content = k.get("content").toString();
                    System.out.println(k);
                    time = k.get("date").toString();
                    name = k.get("nicheng").toString();
                    QuePraiseNum = k.get("praiseNum").toString();
                    QueCommentNum = k.get("commentNum").toString();
                    Intent intent = new Intent(My_Ask.this, My_Question_Detail.class);
                    startActivity(intent);

                }
            });
            return convertView;
        }

    }
    class MyViewHolder {
        View itemView;
        TextView data;
        TextView title;
        TextView tv_test;
        TextView PraiseNum, CommentNum;
        public MyViewHolder(View itemView) {
            this.itemView = itemView;
            tv_test = (TextView) itemView.findViewById(R.id.tv_test);
        }
    }
    //重写onKeyDown方法,对按键(不一定是返回按键)监听
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            Intent intent=new Intent(My_Ask.this,Myself.class);
            startActivity(intent);
            finish();
        }
        return false;
    }
}

