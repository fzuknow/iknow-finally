package com.example.chen.interlocution;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.chen.entity.QuestAnsPraise;
import com.example.chen.entity.QuestPraise;
import com.example.chen.entity.Question;
import com.example.chen.start.Login;
import com.example.chen.tools.AnimationTools;
import com.example.chen.tools.ConnectServer;
import com.example.chen.fzu.R;
import com.example.chen.http.NetUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.view.View.inflate;
import static com.example.chen.start.Login.userId;


public class Latest_Question extends Fragment implements AbsListView.OnScrollListener {
    String result;
    Question question;
    public static String content, time, name, QuePraiseNum, QueCommentNum;
    public static int quelength, n;

    public static String que[][] = new String[10000][7], wealth_que[][] = new String[10000][7];
    boolean Isflag[] = new boolean[100];
    public LayoutInflater inflater;
    int length=0,lengthLast=10;
    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(android.os.Message message) {

            result = (String) message.obj;
            JSONArray ResultJson = JSONObject.parseArray(result);
            quelength = ResultJson.size();

            for (int i = 0; i < ResultJson.size(); i++) {

                JSONObject results = ResultJson.getJSONObject(i);
                String finalResult = results.getString("result");
                question = JSON.parseObject(finalResult, Question.class);
                que[i][0] = question.getContent();//第几条问题的内容
                que[i][1] = question.getReleaseDate();
                que[i][2] = question.getStudent().getStudentName().toString();

                que[i][3] = String.valueOf(question.getCommentNum());
                que[i][4] = String.valueOf(question.getPraiseNum());
                que[i][5] = String.valueOf(question.getId());
                Isflag[i] = false;
                for (int t = 0; t < praiseNum; t++) {
                    if (que_praise[t].equals(que[i][5])) {
                        Isflag[i] = true;
                        break;
                    }
                }

        }
            if(quelength<10){
                lengthLast=quelength;
            }else{
                lengthLast=10;
            }
            firstList=new ArrayList<Map<String, Object>>();
            for (int i = 0; i < lengthLast; i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("nicheng", que[i][2]);
                map.put("date", que[i][1]);
                map.put("content", que[i][0]);
                map.put("praiseNum", que[i][4]);
                map.put("commentNum", que[i][3]);
                firstList.add(map);
//                mlist.add(map);
            }
            //没有这个会产生异步显示
            adapter = new MyAdapter(getActivity(),firstList);
            adapter.notifyDataSetChanged();
            listview.setAdapter(adapter);


        }
    };
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message message) {

            result = (String) message.obj;
            JSONObject resultJson = JSON.parseObject(result);

            String finalResult = resultJson.getString("result");
          //  System.out.println(finalResult);
        }
    };

    public void QuestionRequest() {
        final Map<String, Object> userInfo = new HashMap<String, Object>();
        userInfo.put("userask", "askquestion");
        final String user = "userask=askquestion";
        ConnectServer.Connect(NetUtil.PATH_USER_QUESTION, user, mHanlder);
    }

    QuestPraise praise;
    QuestAnsPraise AnsPraise;

    public static int praiseNum,ans_praise_num,num=0;
    public static boolean flag[]=new boolean[100];
    // Fragment管理对象
    public static String que_praise[]=new String[10000];
    public static String ans_praise[]=new String[10000];
    //解析该学生点赞的所有问题
    private Handler QueHanlder = new Handler() {
        @Override
        public void handleMessage(android.os.Message message) {

            result = (String) message.obj;
            JSONArray ResultJson = JSONObject.parseArray(result);
            praiseNum=ResultJson.size();

            for (int i = 0; i < ResultJson.size(); i++) {

                JSONObject results = ResultJson.getJSONObject(i);
                String finalResult = results.getString("result");
                praise = JSON.parseObject(finalResult, QuestPraise.class);
                que_praise[i]=String.valueOf(praise.getQuestionId());
            }
        }
    };

    //解析该学生点赞的所有评论ID
    private Handler ComHanlder = new Handler() {
        @Override
        public void handleMessage(android.os.Message message) {

            result = (String) message.obj;
            JSONArray ResultJson = JSONObject.parseArray(result);
            ans_praise_num=ResultJson.size();

            for (int i = 0; i < ResultJson.size(); i++) {

                JSONObject results = ResultJson.getJSONObject(i);
                String finalResult = results.getString("result");
                AnsPraise = JSON.parseObject(finalResult, QuestAnsPraise.class);
                ans_praise[i]=String.valueOf(AnsPraise.getQuestAnswerId());
            }
        }
    };
    public void GetPraise()
    {
        final Map<String, Object> userInfo = new HashMap<String, Object>();
        userInfo.put("studentId", Login.userId);
        final String user = "studentId="+Login.userId;
        ConnectServer.Connect(NetUtil.Path_Get_Praise,user,QueHanlder);//请求该学生点赞的所有问题
        ConnectServer.Connect(NetUtil.Path_Get_AnsPraise,user,ComHanlder);//请求该学生点赞的所有评论
    }

    public static ListView listview;
    private ArrayList<String> list;
    public static ArrayList<Map<String, Object>> mlist;
    public ArrayList<Map<String, Object>> firstList=new ArrayList<Map<String, Object>>();
    public ArrayList<Map<String, Object>> nextList=new ArrayList<Map<String, Object>>();
    public boolean isLoading=false;//表示是否正处于加载状态
    private SearchView searchView;
    public static int clickPosition = -1;
    private TextView ReleaseDate;
    int t = 1;
    public View loadmoreView;
    public int last_index;
    public int total_index;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //注意View对象的重复使用，以便节省资源
        GetPraise();
        QuestionRequest();
        View view = inflater.inflate(R.layout.fragment_latest__question, container, false);
        this.inflater=LayoutInflater.from(getActivity());
        loadmoreView=inflater.inflate(R.layout.load_more,null);//获得刷新视图
        loadmoreView.setVisibility(View.VISIBLE);//设置刷新新视图默认情况是不可见的
        searchView = (SearchView) view.findViewById(R.id.searchView);
        listview = (ListView) view.findViewById(R.id.listview);
        listview.setOnScrollListener(this);
        listview.addFooterView(loadmoreView,null,false);

        listview.setAdapter(adapter);
        mlist = new ArrayList<Map<String, Object>>();
        ReleaseDate = (TextView) view.findViewById(R.id.date);

        listview.setTextFilterEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 当搜索内容改变时触发该方法

            //为listView设置一个过滤器，将searchView的文字以参数传进来
            public boolean onQueryTextChange(String newText) {
                ListAdapter adapter = listview.getAdapter();
                if (adapter instanceof Filterable) {
                    Filter filter = ((Filterable) adapter).getFilter();
                    if (TextUtils.isEmpty(newText)) {
                        filter.filter(null);
                        listview.clearTextFilter();
                        mlist = new ArrayList<Map<String,Object>>();
                        final String user = "userask=askquestion";
                        ConnectServer.Connect(NetUtil.PATH_USER_QUESTION, user, mHanlder);
                        listview.setAdapter(adapter);
                    } else {
                        filter.filter(newText);
                        loadComplete();
                    }
                }
//这种方法会出现黑色的悬浮框
//                if (TextUtils.isEmpty(newText)) {
//                    // //使用用户输入的内容对ListView的列表项进行过滤
//
//                    listview.clearTextFilter();
//                    //adapter.notifyDataSetChanged();
//                    System.out.println("pppp");
//
//                } else {
//                    listview.setFilterText(newText);
//                }
                return false;
            }
        });
        return view;
    }
    /**
     * 调用onCreate(), 目的是刷新数据,
     * 从另一activity界面返回到该activity界面时, 此方法自动调用
     */
    @Override
    public void onResume() {
        super.onResume();
        onCreate(null);
        GetPraise();
        QuestionRequest();

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        last_index = firstVisibleItem+visibleItemCount;
        total_index = totalItemCount;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(last_index == total_index && (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE))
        {
            //表示此时需要显示刷新视图界面进行新数据的加载(要等滑动停止)
            int j=quelength/10;
            if(!isLoading&&lengthLast<=quelength)
            {
                //不处于加载状态的话对其进行加载
                isLoading = true;
                //设置刷新界面可见
                System.out.println("....");
                loadmoreView.setVisibility(View.VISIBLE);
                for (int i = length; i < lengthLast; i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("nicheng", que[i][2]);
                    map.put("date", que[i][1]);
                    map.put("content", que[i][0]);
                    map.put("praiseNum", que[i][4]);
                    map.put("commentNum", que[i][3]);
                    nextList.add(map);
//                    mlist.add(map);
                }
                onLoad();
                length += 10;
                lengthLast += 10;
                if (length / 10 == j - 1) {
                    lengthLast = quelength;
                }
            }
        }
    }
    /**
     * 刷新加载
     */
    public void onLoad()
    {
        try {
            //模拟耗时操作
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(adapter == null)
        {
            adapter = new MyAdapter(getActivity(),firstList);
            listview.setAdapter(adapter);
        }else
        {
            adapter.updateView(nextList);
            if(lengthLast==quelength){
                loadComplete();//刷新结束
            }else{
                isLoading=false;
            }
        }
//
    }
    /**
     * 加载完成
     */
    public void loadComplete()
    {
        loadmoreView.setVisibility(View.GONE);//设置刷新界面不可见
        isLoading = false;//设置正在刷新标志位false
        getActivity().invalidateOptionsMenu();
        listview.removeFooterView(loadmoreView);//如果是最后一页的话，则将其从ListView中移出
    }
    public static MyAdapter adapter;

    class MyAdapter extends BaseAdapter implements Filterable {
        private Filter filter;
        private final Object mLock = new Object();
        public MyAdapter(Context context, ArrayList<Map<String, Object>> list){
            mlist=list;
           inflater=LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public Object getItem(int position) {
            return mlist.get(position);
        }

        @Override
        public long getItemId(int position) {

            return 0;
        }
        public void updateView(ArrayList<Map<String, Object>> nowList)
        {
            mlist = nowList;
            this.notifyDataSetChanged();//强制动态刷新数据进而调用getView方法
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final MyViewHolder vh;


            if (convertView == null) {

                convertView = inflate(getActivity(), R.layout.item, null);
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

            if (Isflag[position] == true) {
                vh.praise.setImageDrawable(getResources().getDrawable(R.drawable.praiser));
                vh.praise.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            } else {
                vh.praise.setImageDrawable(getResources().getDrawable(R.drawable.praisew));
                vh.praise.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }
            vh.Comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickPosition = position;
                    Map<String, Object> k = mlist.get(position);
                    content = k.get("content").toString();
                    System.out.println(k);
                    time = k.get("date").toString();
                    name = k.get("nicheng").toString();

                    Intent intent = new Intent(getActivity(), Comment.class);
                    startActivity(intent);
                }
            });


            //刷新adapter的时候，getview重新执行，此时对在点击中标记的position做处理
            if (clickPosition == position) {//当条目为刚才点击的条目时
                if (vh.praise.isSelected()) {
                    clickPosition = -1;
                    vh.praise.setSelected(false);
                } else {
                    vh.praise.setSelected(true);
                }

            } else {
                vh.praise.setSelected(false);
            }
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
                    Intent intent = new Intent(getActivity(), Question_Detail.class);
                    startActivity(intent);

                }
            });
            vh.praise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickPosition = position;//记录点击的position
                    notifyDataSetChanged();
                    Map<String, Object> k = mlist.get(position);

                    if (Isflag[position] == true) {
                        Isflag[position] = false;
                        n = Integer.parseInt(k.get("praiseNum").toString());
                        n--;
                        que[position][4] = String.valueOf(n);
                        mlist.get(position).put("praiseNum", que[position][4]);
                    } else {
                        Isflag[position] = true;
                        n = Integer.parseInt(k.get("praiseNum").toString());
                        n++;
                        que[position][4] = String.valueOf(n);
                        mlist.get(position).put("praiseNum", que[position][4]);
                    }
                    updateItem(clickPosition);
                    AnimationTools.scale(vh.praise);
                    final Map<String, Object> userInfo = new HashMap<String, Object>();
                    userInfo.put("studentId", userId);
                    userInfo.put("questionId", que[clickPosition][5]);
                    userInfo.put("IsPraise", Isflag[position]);
                    final String user = "studentId=" + userId + "&questionId=" + que[clickPosition][5] + "&IsPraise=" + Isflag[position];
                    ConnectServer.Connect(NetUtil.PATH_USER_UpdatePra, user, handler);

                }
            });
            return convertView;
        }

        @Override
        public Filter getFilter() {
            if (filter == null) {
                filter = new ProjectNameFilter();
            }
            return filter;
        }

        //   项目名称过滤器
        private class ProjectNameFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence prefix) {
                // 持有过滤操作完成之后的数据。该数据包括过滤操作之后的数据的值以及数量。 count:数量 values包含过滤操作之后的数据的值
                FilterResults results = new FilterResults();
                if (mlist == null) {
                    synchronized (mLock) {
                        mlist = new ArrayList<Map<String, Object>>();
                    }
                }
                if (TextUtils.isEmpty(prefix)) {
                    synchronized (mLock) {
                        results.values = mlist;
                        results.count = mlist.size();
                    }
                } else {
                    //做正式的筛选
                    //过滤首尾空白，小写过滤
                    String presixString = prefix.toString().trim().toLowerCase(Locale.US);
                    ArrayList<Map<String, Object>> newvalues = new ArrayList<Map<String, Object>>();
                    for (Map<String, Object> f : mlist) {
                        if (f.get("content").toString().toLowerCase(Locale.US).contains(presixString)) {
                            newvalues.add(f);
                        }
                        results.values = newvalues;
                        results.count = newvalues.size();
                    }

                }
                return results;
            }

            @Override
            /**
             * 更新list列表
             */
            protected void publishResults(CharSequence prefix, FilterResults results) {
                mlist = (ArrayList<Map<String, Object>>) results.values;
                notifyDataSetChanged();
            }
        }


        class MyViewHolder {
            View itemView;
            TextView data;
            TextView title;
            TextView tv_test;
            TextView PraiseNum, CommentNum;
            ImageView selectorImg;
            ImageButton praise, Comment;

            public MyViewHolder(View itemView) {
                this.itemView = itemView;
                tv_test = (TextView) itemView.findViewById(R.id.tv_test);
                praise = (ImageButton) itemView.findViewById(R.id.praiseImage);
                selectorImg = (ImageView) itemView.findViewById(R.id.checkbox);
                Comment = (ImageButton) itemView.findViewById(R.id.CommentImage);
            }
        }
    }
    //局部刷新
    private void updateItem(int position) {
        /**第一个可见的位置**/
        int firstVisiblePosition = listview.getFirstVisiblePosition();
        /**最后一个可见的位置**/
        int lastVisiblePosition = listview.getLastVisiblePosition();

        /**在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            /**获取指定位置view对象**/
            View view = listview.getChildAt(position - firstVisiblePosition);
            adapter.getView(position, view, listview);
        }

    }
}
