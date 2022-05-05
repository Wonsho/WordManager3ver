package com.wons.wordmanager3ver.infoactivity.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.datavalues.FlagUserLevelData;

import java.util.ArrayList;

public class MyWayAdapter extends BaseAdapter {
    private ArrayList<FlagUserLevelData> userData;

    public MyWayAdapter() {
        userData = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return userData.size();
    }

    @Override
    public Object getItem(int i) {
        return userData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_my_way, viewGroup, false);
        }
        TextView onderBar = view.findViewById(R.id.lineOn);
        TextView underBar = view.findViewById(R.id.lineUnder);
        ImageView circle = view.findViewById(R.id.iv_circle);
        TextView tv_userFlag = view.findViewById(R.id.tv_myInfo);
        if (i == 0) {
            onderBar.setVisibility(View.GONE);
            underBar.setVisibility(View.VISIBLE);
            circle.setImageResource(R.drawable.ic_baseline_flag_circle_24);
        } else if (i == userData.size() - 1) {
            underBar.setVisibility(View.GONE);
            onderBar.setVisibility(View.VISIBLE);
            circle.setImageResource(R.drawable.ic_baseline_flag_circle_24);
        } else {
            onderBar.setVisibility(View.VISIBLE);
            underBar.setVisibility(View.VISIBLE);
            circle.setImageResource(R.drawable.ic_baseline_circle_24);
        }
        if(i == 0) {
            tv_userFlag.setText(userData.get(i).getDate() + " 여정의 시작\n"+"Lv : "+userData.get(i).getLevelOfDate());
        } else {
            tv_userFlag.setText(userData.get(i).getDate() + " 레벨업\n"+"Lv : "+userData.get(i).getLevelOfDate());
        }
        return view;
    }

    public void setUserData(ArrayList<FlagUserLevelData> userData) {
        this.userData = userData;
    }
}
