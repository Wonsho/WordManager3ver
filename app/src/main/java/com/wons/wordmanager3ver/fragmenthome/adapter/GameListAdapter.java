package com.wons.wordmanager3ver.fragmenthome.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.fragmenthome.value.GameValue;

import java.util.ArrayList;

public class GameListAdapter extends BaseAdapter {
    private ArrayList<GameValue> list;
    public GameListAdapter() {
        this.list = new ArrayList<>();

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.list_game, viewGroup, false);
        }
        ImageView im = view.findViewById(R.id.mv);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_info = view.findViewById(R.id.tv_info);
        im.setImageResource(list.get(i).gamePic);
        tv_title.setText(list.get(i).gameName);
        tv_info.setText(list.get(i).gameInfo);
        return view;
    }

    public void setData(ArrayList<GameValue> values) {
        this.list = values;
    }
}
