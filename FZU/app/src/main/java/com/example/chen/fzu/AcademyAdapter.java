package com.example.chen.fzu;

/**
 * Created by laixl on 2018/1/3.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chen.entity.Academy;

import java.util.List;

/**
 * Created by Administrator on 2018/1/1/001.
 */

public class AcademyAdapter extends BaseAdapter {
    private List<Academy> AcademyList;
    private View view;
    private Context mContext;
    private AcademyAdapter.ViewHolder viewHolder;

    public AcademyAdapter(Context mContext, List<Academy> AcademyList) {
        this.mContext = mContext;
        this.AcademyList=AcademyList;
    }
    public int getCount() {
        return AcademyList.size();
    }

    @Override
    public Object getItem(int position) {
        return AcademyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.fragment_my_academy,
                    null);
            viewHolder = new AcademyAdapter.ViewHolder();
            viewHolder.AcademyTitle = (TextView) view.findViewById(R.id.academytitle);
            viewHolder.AcademyTime = (TextView) view.findViewById(R.id.academytime);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (AcademyAdapter.ViewHolder) view.getTag();
        }
        viewHolder.AcademyTitle.setText(AcademyList.get(position).getAcademyTitle());
        viewHolder.AcademyTime.setText(AcademyList.get(position).getAcademyTime());
        return view;
    }
    class ViewHolder {
        TextView AcademyTitle;
        TextView AcademyTime;
    }

}
