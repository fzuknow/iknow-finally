package com.example.chen.message;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chen.fzu.R;

import java.util.ArrayList;


public class Upvote extends Fragment {
    public static ListView listview;
    public static ArrayList<String> list;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upvote, null);


        listview = (ListView) view.findViewById(R.id.listview);

        adapter = new MyAdapter();
        listview.setAdapter(adapter);


        listview.setTextFilterEnabled(true);
        //为该SearchView组件设置事件监听器

        return view;
    }

    public static MyAdapter adapter;

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
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
                convertView = View.inflate(getActivity(), R.layout.upvote_item, null);
                vh = new MyViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (MyViewHolder) convertView.getTag();
            }

            vh.tv_test.setText(list.get(position));


            return convertView;
        }




        class MyViewHolder {
            View itemView;
            TextView tv_test;
            LinearLayout ll_hide;

            public MyViewHolder(View itemView) {
                this.itemView = itemView;
                tv_test = (TextView) itemView.findViewById(R.id.tv_test);
                ll_hide = (LinearLayout) itemView.findViewById(R.id.ll_hide);
            }
        }
    }
}
