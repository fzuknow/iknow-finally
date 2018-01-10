package com.example.chen.interlocution;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.chen.entity.Base;
import com.example.chen.tools.ConnectServer;
import com.example.chen.fzu.R;
import com.example.chen.http.NetUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
public class Base_Question extends Fragment implements AbsListView.OnScrollListener{
    String result;
    Base baseQuestion;
    public View loadmoreView;
    public int last_index;
    public int total_index;
    int baseLength;
    public ArrayList<Map<String, Object>> firstList=new ArrayList<Map<String, Object>>();
    public ArrayList<Map<String, Object>> nextList=new ArrayList<Map<String, Object>>();
    public boolean isLoading=false;//表示是否正处于加载状态
    public LayoutInflater inflater;
    int length=0,lengthLast=10;
    public static String base_que[][]=new String[10000][7];
    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(android.os.Message message) {

            result = (String) message.obj;
            JSONArray ResultJson = JSONObject.parseArray(result);
            baseLength=ResultJson.size();

            for (int i = 0; i < ResultJson.size(); i++) {

                JSONObject results = ResultJson.getJSONObject(i);
                String finalResult = results.getString("result");
                baseQuestion = JSON.parseObject(finalResult, Base.class);
                base_que[i][0]=baseQuestion.getTitle();
                base_que[i][1]=baseQuestion.getContent();
            }
            for (int i = 0; i <10; i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("quedetail", base_que[i][0]);
                map.put("ansdetail", base_que[i][1]);
                firstList.add(map);
            }

            //没有这个会产生异步显示
            adapter = new MyAdapter(getActivity(),firstList);
            adapter.notifyDataSetChanged();
            listview.setAdapter(adapter);

        }
    };
    public void BaseQues()
    {
        final Map<String, Object> userInfo = new HashMap<String, Object>();
        userInfo.put("userask", "askquestion");
        final String user = "userask=askquestion";
        ConnectServer.Connect(NetUtil.PATH_Question_Base,user,mHanlder);
    }


    private ListView listview;
    private ArrayList<Map<String,Object>> mlist;
    private SearchView searchView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        BaseQues();
        View view = inflater.inflate(R.layout.fragment_base_question, null);
        this.inflater=LayoutInflater.from(getActivity());
        loadmoreView=inflater.inflate(R.layout.load_more,null);//获得刷新视图
        loadmoreView.setVisibility(View.VISIBLE);//设置刷新新视图默认情况是不可见的
        searchView = (SearchView) view.findViewById(R.id.searchView);
        listview = (ListView) view.findViewById(R.id.listview);

        mlist = new ArrayList<Map<String,Object>>();
        listview.setTextFilterEnabled(true);
        listview.setOnScrollListener(this);
        listview.addFooterView(loadmoreView,null,false);

        listview.setAdapter(adapter);
        //为该SearchView组件设置事件监听器
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                ListAdapter adapter = listview.getAdapter();
                if (adapter instanceof Filterable) {
                    Filter filter = ((Filterable) adapter).getFilter();
                    if (TextUtils.isEmpty(newText)) {
                        filter.filter(null);
                        listview.clearTextFilter();
                        mlist = new ArrayList<Map<String,Object>>();
                        final String user = "userask=askquestion";
                        ConnectServer.Connect(NetUtil.PATH_Question_Base,user,mHanlder);
                        listview.setAdapter(adapter);
                    } else {
                        filter.filter(newText);
                        loadComplete();//刷新结束
                    }
                }
                return false;
            }
        });

        return view;
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
            int j=baseLength/10;
            if(!isLoading&&lengthLast<=baseLength)
            {
                //不处于加载状态的话对其进行加载
                isLoading = true;
                //设置刷新界面可见
                loadmoreView.setVisibility(View.VISIBLE);
                for (int i = length; i < lengthLast; i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("quedetail", base_que[i][0]);
                    map.put("ansdetail", base_que[i][1]);
                    nextList.add(map);
                }
                onLoad();
                length += 10;
                lengthLast += 10;
                if (length / 10 == j - 1) {
                    lengthLast = baseLength;
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
            if(lengthLast==baseLength){
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

    public static  MyAdapter adapter;

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
                convertView = View.inflate(getActivity(), R.layout.item_base, null);
                vh = new MyViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (MyViewHolder) convertView.getTag();
            }

            vh.BaseQuestion.setText((String)mlist.get(position).get("quedetail"));
            vh.BaseAnswer.setText((String)mlist.get(position).get("ansdetail"));
            return convertView;
        }

        public Filter getFilter()
        {
            if(filter==null) {
                filter = new ProjectNameFilter();
            }
            return filter;
        }
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
                        if (f.get("quedetail").toString().toLowerCase(Locale.US).contains(presixString)) {
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
                System.out.println("更新list");
                notifyDataSetChanged();
            }
        }
        class MyViewHolder {
            View itemView;
            TextView BaseQuestion;
            TextView BaseAnswer;
            public MyViewHolder(View itemView) {
                this.itemView = itemView;
                BaseQuestion=(TextView)itemView.findViewById(R.id.que_detail);
                BaseAnswer=(TextView)itemView.findViewById(R.id.ans_detail);
            }
        }
    }

}