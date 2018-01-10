package com.example.chen.interlocution;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.chen.entity.Answer;
import com.example.chen.tools.AnimationTools;
import com.example.chen.tools.ConnectServer;
import com.example.chen.fzu.R;
import com.example.chen.http.NetUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.chen.interlocution.Latest_Question.clickPosition;
import static com.example.chen.interlocution.Latest_Question.que;
import static com.example.chen.interlocution.Latest_Question.ans_praise;
import static com.example.chen.interlocution.Latest_Question.ans_praise_num;
import static com.example.chen.interlocution.Latest_Question.flag;
import static com.example.chen.interlocution.Latest_Question.num;
import static com.example.chen.start.Login.userId;

/**
 * Created by laixl on 2017/11/13.
 */

public class Question_Detail extends AppCompatActivity {
    String result;
    Answer answer;
    public static int length;
    public static String ans[][] = new String[10000][6];
    private TextView textView, NameText, TimeText, QuePraiseNum, QueCommentNum;
    private ImageButton back;
    int n;
    private ArrayList<Map<String, Object>> mlist;

    //解析该问题的所有评论
    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(android.os.Message message) {

            result = (String) message.obj;
            JSONArray ResultJson = JSONObject.parseArray(result);
            length = ResultJson.size();
            for (int i = 0; i < ResultJson.size(); i++) {

                JSONObject results = ResultJson.getJSONObject(i);
                String finalResult = results.getString("result");
                answer = JSON.parseObject(finalResult, Answer.class);
                System.out.println("结果是：" + finalResult);
                ans[i][0] = answer.getContent();//第几条回答的内容
                ans[i][1] = answer.getAnswerDate();
                ans[i][2] = answer.getStudent().getStudentName().toString();//回答者的ID
                System.out.println("StudentName" + ans[i][2]);
                ans[i][3] = String.valueOf(answer.getQuestionId());//回答哪条问题
                ans[i][4] = String.valueOf(answer.getPraiseNum());//该回答的点赞数
                ans[i][5] = String.valueOf(answer.getId());//这条回答的ID
                System.out.println("内容：" + ans[i][0]);

                //判断该学生是否点赞过这个评论
                if (num == 0) {//因为点赞评论之后跳转到最新问题界面，但是数据不更新   如果界面可以刷新，就不需要num
                    flag[i] = false;
                    for (int t = 0; t < ans_praise_num; t++) {
                        if (ans_praise[t].equals(ans[i][5])) {
                            System.out.println("ans_praiseId=" + ans_praise[t]);
                            flag[i] = true;
                            break;
                        }
                    }
                }
            }
            num = 1;
            for (int i = 0; i < length; i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("studentId", ans[i][2]);
                map.put("answerDate", ans[i][1]);
                map.put("content", ans[i][0]);
                map.put("praiseNum", ans[i][4]);
                mlist.add(map);
            }
            MyAdapter listItem = new MyAdapter();
            listview.setAdapter(listItem);
        }
    };
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message message) {

            result = (String) message.obj;
            JSONObject resultJson = JSON.parseObject(result);
            String finalResult = resultJson.getString("result");
            System.out.println(finalResult);
        }
    };

    //获取该问题的评论
    public void GetComment() {
        final Map<String, Object> userInfo = new HashMap<String, Object>();
        userInfo.put("questionId", que[clickPosition][5]);
        final String user = "questionId=" + que[clickPosition][5];
        ConnectServer.Connect(NetUtil.Path_Question_Answer, user, mHanlder);
    }

    private ListView listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        GetComment();
        setContentView(R.layout.activity_question_detail);
        mlist = new ArrayList<Map<String, Object>>();

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        TextView title = (TextView) findViewById(R.id.titlebar_title_tv);
        title.setText("问题详情");

        //问题详情，问题内容等等
        textView = (TextView) findViewById(R.id.detail_textview);
        NameText = (TextView) findViewById(R.id.name);
        TimeText = (TextView) findViewById(R.id.time);
        QuePraiseNum = (TextView) findViewById(R.id.Praise);
        QueCommentNum = (TextView) findViewById(R.id.Comment);
        listview = (ListView) findViewById(R.id.detail_listview);
        back = (ImageButton) findViewById(R.id.backImage);
        textView.setText(Latest_Question.content);
        NameText.setText(Latest_Question.name);
        TimeText.setText(Latest_Question.time);
        QuePraiseNum.setText(Latest_Question.QuePraiseNum);
        QueCommentNum.setText(Latest_Question.QueCommentNum);
        //返回到最新问题界面
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(10);
                finish();
            }
        });
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
//
            if (convertView == null) {
                convertView = View.inflate(Question_Detail.this, R.layout.answer_item, null);
                vh = new MyViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (MyViewHolder) convertView.getTag();
            }
            vh.title.setText((String) mlist.get(position).get("studentId"));
            vh.data.setText((String) mlist.get(position).get("answerDate"));
            vh.tv_test.setText((String) mlist.get(position).get("content"));
            vh.PraiseNum.setText((String) mlist.get(position).get("praiseNum"));
            if (flag[position] == true) {
                vh.praise.setImageDrawable(getResources().getDrawable(R.drawable.praiser));
                vh.praise.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            } else {
                vh.praise.setImageDrawable(getResources().getDrawable(R.drawable.praisew));
                vh.praise.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }
            //刷新adapter的时候，getview重新执行，此时对在点击中标记的position做处理
            if (clickPosition == position) {//当条目为刚才点击的条目时
                if (vh.praise.isSelected()) {//点赞的图标被点击的时候
                    clickPosition = -1;
                    vh.praise.setSelected(false);
                } else {
                    vh.praise.setSelected(true);
                }

            } else {
                vh.praise.setSelected(false);
            }

            vh.praise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickPosition = position;//记录点击的position
                    System.out.println("位置" + clickPosition);
                    notifyDataSetChanged();
                    Map<String, Object> k = mlist.get(position);

                    if (flag[position] == true) {
                        flag[position] = false;
                        n = Integer.parseInt(k.get("praiseNum").toString());
                        n--;
                        ans[position][4] = String.valueOf(n);
                        mlist.get(position).put("praiseNum", ans[position][4]);
                    } else {
                        flag[position] = true;
                        n = Integer.parseInt(k.get("praiseNum").toString());
                        n++;
                        ans[position][4] = String.valueOf(n);
                        mlist.get(position).put("praiseNum", ans[position][4]);
                    }
                    AnimationTools.scale(vh.praise);
                    final Map<String, Object> userInfo = new HashMap<String, Object>();

                    userInfo.put("answerId", ans[clickPosition][5]);
                    userInfo.put("studentId", userId);
                    userInfo.put("IsPraise", flag[position]);
                    final String user = "answerId=" + ans[clickPosition][5] + "&studentId=" + userId + "&IsPraise=" + flag[position];
                    //更新点赞数
                    ConnectServer.Connect(NetUtil.Path_AnsPraise_Update, user, handler);

                }
            });
            return convertView;
        }

        class MyViewHolder {
            View itemView;
            TextView data;
            TextView title;
            TextView tv_test;
            TextView PraiseNum;
            ImageButton praise;

            public MyViewHolder(View itemView) {
                this.itemView = itemView;
                tv_test = (TextView) itemView.findViewById(R.id.tv_test);
                praise = (ImageButton) itemView.findViewById(R.id.praiseImage);
                title = (TextView) itemView.findViewById(R.id.title);
                data = (TextView) itemView.findViewById(R.id.date);
                PraiseNum = (TextView) itemView.findViewById(R.id.Praise);
            }
        }
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
