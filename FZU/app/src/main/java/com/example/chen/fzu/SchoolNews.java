package com.example.chen.fzu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SchoolNews extends Fragment {
    private ListView listview;
    private List<News> newsList;
    //private SearchView searchView;
    private Handler mHanlder;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //注意View对象的重复使用，以便节省资源
        View view = inflater.inflate(R.layout.article_listview, container, false);
        //// 显示通知的ListView
        newsList = new ArrayList<>();
        listview = (ListView) view.findViewById(R.id.news_lv);
        getNews();
        mHanlder=new Handler() {
            @Override
            public void handleMessage(Message msg) {

                if (msg.what == 1) {

                    NewsAdapter adapter = new NewsAdapter(getActivity(), newsList);

                    listview.setAdapter(adapter);
                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            News news = newsList.get(position);
                            Intent intent;
                            intent = new Intent(getActivity(), NewsDisplayActivivity.class);
                            intent.putExtra("article_Url", news.getArticleUrl());
                            startActivity(intent);
                        }
                    });
                }
            }
        };
        return view;
    }
    private void getNews()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                        //爬取网址
                        org.jsoup.nodes.Document doc = Jsoup.connect("http://jwch.fzu.edu.cn/html/jwtz/").get();

                        Elements titleLinks = doc.select("div.articlelist").select("li");//解析标题的地址和名称
                        // Elements divisionLinks = doc.select("span.fi11");//部门
                        Elements timeLinks = doc.select("span.date");//时间
                       // Log.e("title", Integer.toString(titleLinks.size()));
                        for (int j = 0; j < timeLinks.size(); j++) {
                            String division = titleLinks.get(j).select("a").first().text();
                            String title = titleLinks.get(j).select("a").last().text();
                            String url = titleLinks.get(j).select("a").last().attr("href");
                            String articletime = timeLinks.get(j).text();
                            News news = new News(title, url, division, articletime);
                            newsList.add(news);
                        }
                    Message msg = new Message();
                    msg.what = 1;
                    mHanlder.sendMessage(msg);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

}).start();
    }
}
