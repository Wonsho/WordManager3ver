package com.wons.wordmanager3ver.testfragment.testword.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wons.wordmanager3ver.databinding.ListResultWordBinding;
import com.wons.wordmanager3ver.datavalues.TestWordResult;
import com.wons.wordmanager3ver.datavalues.WordInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultAdapter extends BaseAdapter {
    private ListResultWordBinding binding;
    private ArrayList<TestWordResult> arrayList;
    private HashMap<String, WordInfo> wordInfoHashMap;

    public ResultAdapter() {
        this.arrayList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            binding = ListResultWordBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        } else {
            binding = ListResultWordBinding.bind(view);
        }

        binding.tvWordTitle.setText(arrayList.get(i).getWordTitle());
        binding.tvWordKorean.setText(wordInfoHashMap.get(arrayList.get(i).getWordTitle().trim().toUpperCase()).wordKorean);
        if(arrayList.get(i).getTestResult()) {
            binding.tvPass.setText("정답");
        } else {
            binding.tvPass.setText("틀림");
        }
        return binding.getRoot();
    }

    public void setArrayList(ArrayList<TestWordResult> arrayList) {
        this.arrayList = arrayList;
    }

    public void setWordInfoHashMap(HashMap<String, WordInfo> wordInfoHashMap) {
        this.wordInfoHashMap = wordInfoHashMap;
    }
}
