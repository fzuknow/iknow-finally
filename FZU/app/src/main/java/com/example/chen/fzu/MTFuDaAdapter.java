package com.example.chen.fzu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/12/27/027.
 */

public class MTFuDaAdapter extends BaseAdapter {
    private List<MTFuDa> mtfdList;
    private View view;
    private Context mContext;
    private ViewHolder viewHolder;

    public MTFuDaAdapter(Context mContext, List<MTFuDa> mtfdList) {
        this.mContext = mContext;
        this.mtfdList=mtfdList;
    }
    public int getCount() {
        return mtfdList.size();
    }

    @Override
    public Object getItem(int position) {
        return mtfdList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.fragment_real_time,
                    null);
            viewHolder = new ViewHolder();
            viewHolder.mtfdTitle = (TextView) view.findViewById(R.id.mtfdtitle);
            viewHolder.mtfdTime = (TextView) view.findViewById(R.id.mtfdtime);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mtfdTitle.setText(mtfdList.get(position).getMtfdTitle());
        viewHolder.mtfdTime.setText(mtfdList.get(position).getMtfdTime());
        return view;
    }
    class ViewHolder {
        TextView mtfdTitle;
        TextView mtfdTime;
    }

}
