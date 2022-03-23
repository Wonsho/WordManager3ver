package com.wons.wordmanager3ver.fragmenthome.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wons.wordmanager3ver.databinding.ListWordListHomeBinding;
import com.wons.wordmanager3ver.datavalues.EnumGrade;
import com.wons.wordmanager3ver.datavalues.TodayWordList;

import java.util.ArrayList;

public class TodayListAdapter extends BaseAdapter {
    private ArrayList<TodayWordList> todayWordLists;

    public TodayListAdapter() {
        todayWordLists = new ArrayList<>();
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
        if(todayWordLists.get(i).passOrNo) {
            binding.tvPass.setVisibility(View.VISIBLE);
        } else {
            binding.tvPass.setVisibility(View.GONE);
        }
        binding.tvListTitle.setText(todayWordLists.get(i).listName);
        binding.tvWordCount.setText(String.valueOf(todayWordLists.get(i).listWordCount));
        String listGrade = EnumGrade.F.getGradeToString(todayWordLists.get(i).listGrade);
        if(todayWordLists.get(i).listGrade == 0) {
            listGrade = "데이터 없음";
        }
        binding.tvListGrade.setText(listGrade);
        return binding.getRoot();
    }
    public void setTodayWordLists(ArrayList<TodayWordList> todayWordLists) {
        this.todayWordLists = todayWordLists;
    }
}
