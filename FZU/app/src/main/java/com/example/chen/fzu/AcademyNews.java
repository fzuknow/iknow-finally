package com.example.chen.fzu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.chen.entity.Academy;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AcademyNews extends Fragment {
    private List<Academy> AcademyList;
    private Handler mHanlder;
    private ListView listview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //注意View对象的重复使用，以便节省资源
        View view = inflater.inflate(R.layout.article_listview, container, false);
        //// 显示通知的ListView
        AcademyList = new ArrayList<>();
        listview = (ListView) view.findViewById(R.id.news_lv);
        getAcademy();
        mHanlder=new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {

                if (msg.what == 1) {
                    AcademyAdapter adapter = new  AcademyAdapter(getActivity(), AcademyList);
                    listview.setAdapter(adapter);
                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Academy academy = AcademyList.get(position);
                            Intent intent;
                            intent = new Intent(getActivity(), AcademyDisplay.class);
                            intent.putExtra("Academy_Url", academy.getAcademyUrl());
                            startActivity(intent);
                        }
                    });
                }
            }
        };
        return view;
    }
    private void getAcademy()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //爬取网址,10页
                    for(int i=1;i<=5;i++) {
                        org.jsoup.nodes.Document doc = Jsoup.connect("http://news.fzu.edu.cn/html/xydt/" + Integer.toString(i) + ".html").get();
                        Elements titleLinks = doc.select("div.list_main_content").select("li");//解析标题的地址和名称
                        Elements timeLinks = doc.select("span.list_time");//时间
                        //Log.e("title", Integer.toString(titleLinks.size()));
                        for (int j = 0; j < timeLinks.size(); j++) {
                            String title = titleLinks.get(j).select("a").text();
                            String url = titleLinks.get(j).select("a").attr("href");
                            String time = timeLinks.get(j).text();
                            Academy academy = new Academy(title, url,time);
                            AcademyList.add(academy);
                        }
                    }
                    android.os.Message msg = new android.os.Message();
                    msg.what = 1;
                    mHanlder.sendMessage(msg);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }
}
