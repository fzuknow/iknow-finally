package com.example.chen.fzu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/12/22/022.
 */

public class NewsAdapter extends BaseAdapter {
    private List<News> newsList;
    private View view;
    private Context mContext;
    private ViewHolder viewHolder;

    public NewsAdapter(Context mContext, List<News> newsList) {
        this.mContext = mContext;
        this.newsList = newsList;
    }

    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.fragment_hot__topic,
                    null);
            viewHolder = new ViewHolder();
            viewHolder.articleTitle= (TextView) view.findViewById(R.id.articletitle);
            viewHolder.articleDivision = (TextView) view.findViewById(R.id.adivision);
            viewHolder.articleTime=(TextView)view.findViewById(R.id.articletime);
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.articleTitle.setText(newsList.get(position).getArticleTitle());
        viewHolder.articleDivision.setText(newsList.get(position).getArticleDivision());
        viewHolder.articleTime.setText(newsList.get(position).getArticleTime());
        return view;
    }
    class ViewHolder {
        TextView articleTitle;
        TextView articleDivision;
        TextView articleTime;
    }
}
