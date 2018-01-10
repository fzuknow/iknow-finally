package com.example.chen.message;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.astuetz.PagerSlidingTabStrip;
import com.example.chen.entity.Answer;
import com.example.chen.entity.QuestAnsPraise;
import com.example.chen.entity.QuestPraise;
import com.example.chen.entity.Question;
import com.example.chen.entity.User;
import com.example.chen.fzu.R;
import com.example.chen.http.HttpUtil;
import com.example.chen.http.NetUtil;
import com.example.chen.start.Login;
import com.example.chen.tools.ConnectServer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.chen.message.Reply.replyadapter;
import static com.example.chen.message.Reply.replylist;
import static com.example.chen.message.Reply.replylistview;
import static com.example.chen.message.Upvote.adapter;
import static com.example.chen.message.Upvote.list;
import static com.example.chen.message.Upvote.listview;


public class Message extends Fragment {

    /**
     * PagerSlidingTabStrip的实例
     */
    private PagerSlidingTabStrip tabs;

    /**
     * 获取当前屏幕的密度
     */
    private DisplayMetrics dm;


    private Person_Letter oneFragment;
    private Reply twoFragment;
    private Upvote threeFragment;
    /*
     *
     */


    /*
     *
     */
    String result;
    QuestPraise questionPraise;
    User u;
    Question q;
    Answer questionAnswer;
    QuestAnsPraise questionAnswerPraise;

    public static int quelength;
    public static String Questioncontent[] = new String[1000000];
    public static String username[] = new String[1000000];
    public static int userID_According_questID[] = new int[1000000];
    public static String answercontent_acc_answerID[] = new String[1000000];
    //   public static int qusetID_According_questAnsID[] = new int[1000000];
    public static int userID_According_questAnsID[] = new int[1000000];

    public static String quename[] = new String[1000000];//显示
    public static String quequestioncontent[] = new String[1000000];//显示

    public static int question_praise_que[] = new int[1000000];
    public static int question_Bepraise_que[] = new int[1000000];
    public static int BepraiseQuestionID[] = new int[1000000];
    public static int ques_praise_count;

    public static String Answer_Stuname[] = new String[1000000];
    // public static int BeAnswerStuId[] = new int[1000000];
    public static int AnswerStuId[] = new int[1000000];
    public static String BeAnswerQuestion_Content[] = new String[1000000];
    public static String Answer_Content[] = new String[1000000];
    public static int BeAnswerQuestionId[] = new int[1000000];
    public static int ques_answer_count;


    /*
     查询所有点赞记录
     */

    private Handler mHanlder = new Handler() {
        @Override

        public void handleMessage(android.os.Message message) {

            ques_praise_count = 0;

            result = (String) message.obj;
            JSONArray ResultJson = JSONObject.parseArray(result);
            quelength = ResultJson.size();

            for (int i = 0; i < ResultJson.size(); i++) {

                JSONObject results = ResultJson.getJSONObject(i);
                String finalResult = results.getString("result");
                questionPraise = JSON.parseObject(finalResult, QuestPraise.class);
                question_Bepraise_que[i] = questionPraise.getBepraise_studentId();//被点赞ID.
                question_praise_que[i] = questionPraise.getStudentId();//点赞者ID
                BepraiseQuestionID[i] = questionPraise.getQuestionId();//被点赞问题ID


                if (question_Bepraise_que[i] == Integer.parseInt(Login.userId)) {
                    quename[ques_praise_count] = username[question_praise_que[i]] + "  点赞了  \n";
                    quequestioncontent[ques_praise_count] = "你的问题：" + Questioncontent[BepraiseQuestionID[i]];
                    ques_praise_count++;
                }


            }

        }
    };

    //    /*
//       查询所有回复问题记录
//     */
//
//
    private Handler mHanlder3 = new Handler() {
        @Override

        public void handleMessage(android.os.Message message) {

            ques_answer_count = 0;

            result = (String) message.obj;
            JSONArray ResultJson = JSONObject.parseArray(result);
            quelength = ResultJson.size();
            for (int i = 0; i < ResultJson.size(); i++) {
                JSONObject results = ResultJson.getJSONObject(i);
                String finalResult = results.getString("result");
                questionAnswer = JSON.parseObject(finalResult, Answer.class);

                AnswerStuId[i] = questionAnswer.getStudentId();//回答者ID
                int temp = questionAnswer.getQuestionId();
                int ques_ans_id = questionAnswer.getId();
                answercontent_acc_answerID[ques_ans_id] = questionAnswer.getContent(); //回答的内容

                userID_According_questAnsID[ques_ans_id] = AnswerStuId[i];

                if (userID_According_questID[temp] == Integer.parseInt(Login.userId)) {

                    Answer_Stuname[ques_answer_count] = username[AnswerStuId[i]] + "  回答了  \n";
                    BeAnswerQuestion_Content[ques_answer_count] = "问题：" + Questioncontent[temp] + "\n";
                    Answer_Content[ques_answer_count] = "回答内容：" + questionAnswer.getContent(); //回答的内容
                    ques_answer_count++;
                }

            }



            QuestionAnswerPraiseRequest();

            for(int j=0;j<ques_answer_count;j++){
                replylist.add(Answer_Stuname[j]+ BeAnswerQuestion_Content[j]+Answer_Content[j]);
            }

            replylistview.setAdapter(replyadapter);

        }
    };


    private Handler Hanlder_USER = new Handler() {
        @Override

        public void handleMessage(android.os.Message message) {


            result = (String) message.obj;
            JSONArray ResultJson = JSONObject.parseArray(result);
            quelength = ResultJson.size();

            for (int i = 0; i < ResultJson.size(); i++) {

                JSONObject results = ResultJson.getJSONObject(i);
                String finalResult = results.getString("result");
                u = JSON.parseObject(finalResult, User.class);

                int temp = u.getStudentId();

                username[temp] = u.getStudentName();

            }
            QuestionPraiseRequest();

        }
    };


    private Handler Hanlder_Qusetion = new Handler() {
        @Override

        public void handleMessage(android.os.Message message) {


            result = (String) message.obj;
            JSONArray ResultJson = JSONObject.parseArray(result);
            quelength = ResultJson.size();

            for (int i = 0; i < ResultJson.size(); i++) {

                JSONObject results = ResultJson.getJSONObject(i);
                String finalResult = results.getString("result");
                q = JSON.parseObject(finalResult, Question.class);

                int temp = q.getId();
                Questioncontent[temp] = q.getContent();

                int stu_id = q.getStudentId();
                userID_According_questID[temp] = stu_id;

            }

            QuestionAnswerRequest();

        }
    };


    private Handler Hanlder_QusetionAnsPraise = new Handler() {
        @Override

        public void handleMessage(android.os.Message message) {


            result = (String) message.obj;
            JSONArray ResultJson = JSONObject.parseArray(result);
            quelength = ResultJson.size();

            for (int i = 0; i < ResultJson.size(); i++) {

                JSONObject results = ResultJson.getJSONObject(i);
                String finalResult = results.getString("result");
                questionAnswerPraise = JSON.parseObject(finalResult, QuestAnsPraise.class);
                int stuID = questionAnswerPraise.getStudentId();


                int ques_ans_id = questionAnswerPraise.getQuestAnswerId();

                if (userID_According_questAnsID[ques_ans_id] == Integer.parseInt(Login.userId)) {
                    quename[ques_praise_count] = username[stuID] + "  点赞了  \n";
                    quequestioncontent[ques_praise_count] = "你的回答：" + answercontent_acc_answerID[ques_ans_id];
                    ques_praise_count++;
                }
            }

            for (int j = 0; j <Message.ques_praise_count; j++) {
                list.add(quename[j]+quequestioncontent[j]);
            }
            listview.setAdapter(adapter);

        }
    };


    /*
     查询所有点赞记录
     */

    public void QuestionPraiseRequest() {
        final Map<String, Object> userInfo = new HashMap<String, Object>();
        userInfo.put("userask", "askquestionpraise");
        final String user = "userask=askquestionpraise";
        ConnectServer.Connect(NetUtil.PATH_USER_Question_Praise, user,mHanlder);
    }

    public void UserRequest() {
        final Map<String, Object> userInfo = new HashMap<String, Object>();
        userInfo.put("userask", "askuser");
        final String user = "userask=askuser";
        ConnectServer.Connect(NetUtil.PATH_USER_AllUser, user,Hanlder_USER);
    }

    public void QuestionRequest() {
        final Map<String, Object> userInfo = new HashMap<String, Object>();
        userInfo.put("userask", "askuquestion");
        final String user = "userask=askuquestion";
        ConnectServer.Connect(NetUtil.PATH_USER_QUESTION, user,Hanlder_Qusetion);
    }



    /*
       查询所有回复问题记录
     */

    public void QuestionAnswerRequest() {
        final Map<String, Object> userInfo = new HashMap<String, Object>();
        userInfo.put("userask", "askquestionAnswer");
        final String user = "userask=askquestionAnswer";
        ConnectServer.Connect(NetUtil.PATH_USER_Question_Answer, user,mHanlder3);
    }

    /*
      查询问题回答点赞记录
     */
    public void QuestionAnswerPraiseRequest() {
        final Map<String, Object> userInfo = new HashMap<String, Object>();
        userInfo.put("userask", "askquestionAnswerPraise");
        final String user = "userask=askquestionAnswerPraise";
        ConnectServer.Connect(NetUtil.PATH_USER_QuestionAnswerPraise, user,Hanlder_QusetionAnsPraise);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        list = new ArrayList<>();
        replylist = new ArrayList<>();

        QuestionRequest();

        UserRequest();

        View view = inflater.inflate(R.layout.fragment_question__answer, null);
        setOverflowShowingAlways();
        dm = getResources().getDisplayMetrics();
        ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setOffscreenPageLimit(0);//设置ViewPager的缓存界面数,默认缓存为2
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        pager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        tabs.setViewPager(pager);
        setTabsValue();
        return view;
    }

    /**
     * 对PagerSlidingTabStrip的各项属性进行赋值。
     */
    private void setTabsValue() {
        // 设置Tab是自动填充满屏幕的
        tabs.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        tabs.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        tabs.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // 设置Tab Indicator的高度
        tabs.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, dm));
        // 设置Tab标题文字的大小
        tabs.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, dm));
        // 设置Tab Indicator的颜色
        tabs.setIndicatorColor(Color.parseColor("#d83737"));//#d83737   #d83737(绿)

        tabs.setTabBackground(0);

    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final String[] titles = {"回复","赞" };

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (twoFragment == null) {
                        twoFragment = new Reply();
                    }
                    return twoFragment;
                case 1:
                    if (threeFragment == null) {
                        threeFragment = new Upvote();
                    }
                    return threeFragment;

                default:
                    return null;
            }
        }

    }

    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(getParentFragment().getActivity());
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


