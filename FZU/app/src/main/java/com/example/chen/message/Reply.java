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


public class Reply extends Fragment {


    public static ListView replylistview;
    public static ArrayList<String> replylist;
    public int clickPosition = -1;
    private ImageView imageView;
    private Person_Letter_Detail person_letter_detail;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reply, null);


        replylistview = (ListView) view.findViewById(R.id.listview);

        replyadapter = new MyAdapter();
        replylistview.setAdapter(replyadapter);


        replylistview.setTextFilterEnabled(true);
        //为该SearchView组件设置事件监听器


        return view;
    }

    public static MyAdapter replyadapter;

    class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return replylist.size();
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
                convertView = View.inflate(getActivity(), R.layout.reply_item, null);
                vh = new MyViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (MyViewHolder) convertView.getTag();
            }

            vh.tv_test.setText(replylist.get(position));


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
