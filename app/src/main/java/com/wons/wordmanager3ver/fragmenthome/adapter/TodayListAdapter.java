package com.wons.wordmanager3ver.fragmenthome.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wons.wordmanager3ver.databinding.ListWordListHomeBinding;
import com.wons.wordmanager3ver.datavalues.EnumGrade;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.datavalues.WordList;

import java.util.ArrayList;
import java.util.HashMap;

public class TodayListAdapter extends BaseAdapter {
    private ArrayList<TodayWordList> todayWordKay;
    private HashMap<TodayWordList,WordList> todayWordLists;

    public TodayListAdapter() {
        todayWordKay = new ArrayList<>();
        todayWordLists = new HashMap<>();
    }
    @Override
    public int getCount() {
        return todayWordLists.size();
    }

    @Override
    public Object getItem(int i) {
        return todayWordLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ListWordListHomeBinding binding;
        binding = ListWordListHomeBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        binding.tvListTitle.setText(todayWordLists.get(i).listName);
        binding.tvListGrade.setText(EnumGrade.D.getGradeToString(todayWordLists.get(i).getListGradeInt()));
        if(todayWordLists.get(i).getListGradeInt() == 0) {
            binding.tvListGrade.setText("데이터 없음");
        }

        binding.tvWordCount.setText(String.valueOf(todayWordLists.get(i).getWordCountInt()));
        return binding.getRoot();
    }
    public void setTodayWordLists(ArrayList<TodayWordList> todayWordKay, HashMap<TodayWordList,WordList> todayWordLists) {
        this.todayWordKay = todayWordKay;
        this.todayWordLists = todayWordLists;
    }
}
