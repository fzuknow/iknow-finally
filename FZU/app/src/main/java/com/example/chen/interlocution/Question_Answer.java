package com.example.chen.interlocution;

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
import com.example.chen.entity.QuestAnsPraise;
import com.example.chen.entity.QuestPraise;
import com.example.chen.tools.ConnectServer;
import com.example.chen.fzu.R;
import com.example.chen.http.NetUtil;
import com.example.chen.start.Login;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//app:pstsShouldExpand="true"
// pstsShouldExpand 如果设置为true，每个选项卡都是相同的weight，即LinearLayout的权重一样，默认为false

public class Question_Answer extends Fragment {

    /**
     * PagerSlidingTabStrip的实例
     */
    private PagerSlidingTabStrip tabs;

    /**
     * 获取当前屏幕的密度
     */
    private DisplayMetrics dm;

    private Latest_Question oneFragment;
    private Base_Question twoFragment;
    /*
     *
     */
    public static ArrayList<String> list=new ArrayList<>();
    String result;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_question__answer, null);
        setOverflowShowingAlways();
        dm = getResources().getDisplayMetrics();
        ViewPager pager = (ViewPager)view. findViewById(R.id.pager);
        pager.setOffscreenPageLimit(0);//设置ViewPager的缓存界面数,默认缓存为2
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        pager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        tabs.setViewPager(pager);
        setTabsValue();

        return view;
    }

    /**
     * 调用onCreate(), 目的是刷新数据,
     * 从另一activity界面返回到该activity界面时, 此方法自动调用
     */
//    @Override
//    public void onResume() {
//        super.onResume();
//        onCreate(null);
//        System.out.println("点赞数据刷新....");
//        GetPraise();
//
//    }

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

        private final String[] titles = { "最新问题", "常规问题" };

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
                    if (oneFragment == null) {
                        oneFragment = new Latest_Question();

                    }
                    return oneFragment;
                case 1:
                    if (twoFragment == null) {
                        twoFragment = new Base_Question();

                    }
                    return twoFragment;

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
