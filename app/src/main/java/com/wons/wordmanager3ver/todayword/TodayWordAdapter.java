package com.wons.wordmanager3ver.todayword;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wons.wordmanager3ver.databinding.TodaywordListBinding;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.datavalues.WordInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TodayWordAdapter extends BaseAdapter {

    private ArrayList<String> wordTitle;
    private HashMap<String, WordInfo> wordInfoHashMap;

    TodayWordAdapter() {
        this.wordInfoHashMap = new HashMap<>();
        this.wordTitle = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return wordTitle.size();
    }

    @Override
    public Object getItem(int i) {
        return wordTitle.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TodaywordListBinding binding;
        if(view == null) {
           binding = TodaywordListBinding.inflate(LayoutInflater.from(viewGroup.getContext()));
        } else {
            binding = TodaywordListBinding.bind(view);
        }

        WordInfo wordInfo = wordInfoHashMap.get(wordTitle.get(i));
        binding.tvWordTitle.setText(wordTitle.get(i));
        binding.tvWordKorean.setText(wordInfo.wordKorean);
        binding.tvPercentage.setText(String.valueOf(wordInfo.getCorrectPercentage()));

        if(wordInfo.getTodayTestResult()) {
            binding.tvResult.setVisibility(View.VISIBLE);
        } else {
            binding.tvResult.setVisibility(View.GONE);
        }

        if(wordInfo.getCorrectPercentage() == -1) {
            binding.tvPercentage.setText("데이터 없음");
        }

        return binding.getRoot();
    }

    public void setAdapterData(ArrayList<String> wordTitleArr, HashMap<String, WordInfo> wordInfoHashMap) {
        this.wordTitle = wordTitleArr;
        this.wordInfoHashMap = wordInfoHashMap;
    }
}
