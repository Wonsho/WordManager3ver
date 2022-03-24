package com.wons.wordmanager3ver.fragmenthome.adapter;

import android.annotation.SuppressLint;
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
    private ArrayList<TodayWordList> todayWordLists;
    private HashMap<Integer, WordList> wordLists;

    public TodayListAdapter() {
        todayWordLists = new ArrayList<>();
        wordLists = new HashMap<>();
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

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ListWordListHomeBinding binding;
        if (view == null) {
            binding = ListWordListHomeBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        } else {
            binding = ListWordListHomeBinding.bind(view);
        }
        binding.tvListTitle.setText(wordLists.get(todayWordLists.get(i).getListCode()).listName);
        binding.tvListGrade.setText(EnumGrade.D.getGradeToString(wordLists.get(todayWordLists.get(i).getListCode()).getListGradeInt()));
        if (wordLists.get(todayWordLists.get(i).getListCode()).getListGradeInt() == 0) {
            binding.tvListGrade.setText("데이터 없음");
        }
        if (todayWordLists.get(i).passOrNo) {
            binding.tvPass.setVisibility(View.VISIBLE);
        } else {
            binding.tvPass.setVisibility(View.GONE);
        }
        binding.tvWordCount.setText(String.valueOf(wordLists.get(todayWordLists.get(i).getListCode()).getWordCountInt()) + "/20");

        return binding.getRoot();
    }

    public void setTodayWordLists(ArrayList<TodayWordList> todayWordList, HashMap<Integer, WordList> wordLists) {
        this.todayWordLists = todayWordList;
        this.wordLists = wordLists;
    }
}
