package com.example.test;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> data;
    private ArrayList<Boolean> state;
    private LayoutInflater inflate;
    private boolean visibilityFlag;

    public ListViewAdapter(Context context, ArrayList<Boolean> state, ArrayList<String> data) {
        this.context = context;
        this.data = data;
        this.state = state;
        inflate = LayoutInflater.from(context);
    }

    public void setVisibilityFlag(boolean visibilityFlag) {
        this.visibilityFlag = visibilityFlag;
        notifyDataSetChanged();
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setState(ArrayList<Boolean> state) {
        this.state = state;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = inflate.inflate(R.layout.item_layout, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = ((Holder) convertView.getTag());
        }

        final String str = data.get(position);
        if (!TextUtils.isEmpty(str)) {
            holder.tvName.setText(str);
            holder.check_box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.itemClickIsCheckedListener(str);
                    }
                }
            });

            if (visibilityFlag) {
                holder.check_box.setVisibility(View.VISIBLE);
            } else {
                holder.check_box.setVisibility(View.GONE);
            }

            if (state != null && state.size() > 0) {
                if (state.get(position)) {
                    holder.check_box.setChecked(true);
                } else {
                    holder.check_box.setChecked(false);
                }
            }
        }
        return convertView;
    }

    class Holder {
        TextView tvName;
        LinearLayout llRoot;
        CheckBox check_box;

        public Holder(View view) {
            tvName = (TextView) view.findViewById(R.id.tvName);
            llRoot = (LinearLayout) view.findViewById(R.id.llRoot);
            check_box = (CheckBox) view.findViewById(R.id.check_box);
        }
    }

    ClickListener clickListener;

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void itemClickIsCheckedListener(String str);
    }
}