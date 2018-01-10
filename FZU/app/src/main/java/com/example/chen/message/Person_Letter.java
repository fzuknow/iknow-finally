package com.example.chen.message;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import com.example.chen.fzu.R;

import java.util.ArrayList;


public class Person_Letter extends Fragment {
    private ListView listview;
    private ArrayList<String> list;
    public int clickPosition = -1;
    private ImageView imageView;
    private Person_Letter_Detail person_letter_detail;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person__letter, null);


        listview = (ListView) view.findViewById(R.id.listview);
        list = new ArrayList<>();
        list.add("123");
        list.add("456");
//        for (int i = 0; i < Question_Answer.quelength; i++) {
//            list.add(que[i][0]);
//        }
        adapter = new MyAdapter();
        listview.setAdapter(adapter);


        listview.setTextFilterEnabled(true);
        //为该SearchView组件设置事件监听器


        return view;
    }

    private MyAdapter adapter;

    class MyAdapter extends BaseAdapter implements View.OnClickListener {


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
                convertView = View.inflate(getActivity(), R.layout.person_letter_item, null);
                vh = new MyViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (MyViewHolder) convertView.getTag();
            }

            vh.tv_test.setText(list.get(position));

            if (clickPosition == position) {
                if (vh.selectorImg.isSelected()) {
                    vh.selectorImg.setSelected(false);
                    vh.ll_hide.setVisibility(View.GONE);
                    Log.e("listview", "if执行");
                    clickPosition = -1;
                } else {
                    vh.selectorImg.setSelected(true);
                    vh.ll_hide.setVisibility(View.VISIBLE);

                    Log.e("listview", "else执行");
                }

            } else {
                vh.ll_hide.setVisibility(View.GONE);
                vh.selectorImg.setSelected(false);

                Log.e("listview", "状态改变");
            }

            vh.hide_1.setOnClickListener(this);

            vh.selectorImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    clickPosition = position;
                    notifyDataSetChanged();

                }
            });
            return convertView;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.hide_1:

                    Intent intent = new Intent(getActivity(),Person_Letter_Detail.class);
                    startActivity(intent);
                    break;

            }
        }


        class MyViewHolder {
            View itemView;
            TextView tv_test;
            TextView hide_1;
            ImageView selectorImg;
            LinearLayout ll_hide;

            public MyViewHolder(View itemView) {
                this.itemView = itemView;
                tv_test = (TextView) itemView.findViewById(R.id.tv_test);
                selectorImg = (ImageView) itemView.findViewById(R.id.checkbox);
                hide_1 = (TextView) itemView.findViewById(R.id.hide_1);
                ll_hide = (LinearLayout) itemView.findViewById(R.id.ll_hide);
            }
        }
    }
}
