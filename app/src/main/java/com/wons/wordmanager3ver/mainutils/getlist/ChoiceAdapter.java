package com.wons.wordmanager3ver.mainutils.getlist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.wons.wordmanager3ver.databinding.ListChoiceListBinding;
import com.wons.wordmanager3ver.datavalues.WordList;

import java.util.ArrayList;

public class ChoiceAdapter extends BaseAdapter {
    private ArrayList<WordList> wordLists;
    private ArrayList<WordList> selectedWordList;
    private AdapterCallBack callBack;
    //todo 콜백 (selected WordList)

    ChoiceAdapter(AdapterCallBack callBack) {
        wordLists = new ArrayList<>();
        selectedWordList = new ArrayList<>();
        this.callBack = callBack;
    }
    @Override
    public int getCount() {
        return wordLists.size();
    }

    @Override
    public Object getItem(int i) {
        return wordLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ListChoiceListBinding binding;
        binding = ListChoiceListBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        binding.tvListTitle.setText(wordLists.get(i).listName);
        binding.tvListGrade.setText(String.valueOf(wordLists.get(i).getListGradeInt()) + "점");
        if(wordLists.get(i).getListGradeInt() == -1) {
            binding.tvListGrade.setText("데이터 없음");
        }
        binding.tvWordCount.setText(String.valueOf(wordLists.get(i).getWordCountInt()));
        binding.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(wordLists.get(i).getWordCountInt() == 0) {
                    binding.cb.setChecked(false);
                    Toast.makeText(viewGroup.getContext(), "단어가 없는 단어장 입니다", Toast.LENGTH_LONG).show();
                    return;
                }
                if(b) {
                    // 체크
                    Log.e("check", String.valueOf(b));
                    selectedWordList.add(wordLists.get(i));
                    callBack.callBack(true);

                } else {
                    selectedWordList.remove(wordLists.get(i));
                    callBack.callBack(false);
                    Log.e("check", String.valueOf(b));
                }
            }
        });
        return binding.getRoot();
    }

    public void setWordLists(ArrayList<WordList> wordLists) {
        this.wordLists = wordLists;
    }

    public ArrayList<WordList> getSelectedWordList() {
        return this.selectedWordList;
    }
}
