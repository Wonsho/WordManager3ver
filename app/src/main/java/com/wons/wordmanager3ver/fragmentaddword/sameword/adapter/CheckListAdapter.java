package com.wons.wordmanager3ver.fragmentaddword.sameword.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.databinding.ListSameWordListBinding;
import com.wons.wordmanager3ver.databinding.ListWordAddListBinding;
import com.wons.wordmanager3ver.datavalues.WordList;

import java.util.ArrayList;

public class CheckListAdapter extends BaseAdapter {
    private ArrayList<WordList> list;

    public CheckListAdapter() {
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
        @SuppressLint("ViewHolder")
        ListSameWordListBinding binding = ListSameWordListBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        if(view == null) {
            view = binding.getRoot();
        }

        binding.tvListName.setText(list.get(i).listName);
        binding.tvDate.setText(list.get(i).insertDate);
        return view;
    }

    public void setList(ArrayList<WordList> list) {
        this.list = list;
    }
}
