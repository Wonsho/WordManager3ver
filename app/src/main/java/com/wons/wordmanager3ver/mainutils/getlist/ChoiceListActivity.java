package com.wons.wordmanager3ver.mainutils.getlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.databinding.ActivityChoiceListBinding;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.datavalues.WordList;

import java.util.ArrayList;
import java.util.Arrays;

public class ChoiceListActivity extends AppCompatActivity {

    private ActivityChoiceListBinding binding;
    private MyDao dao;
    private int languageCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChoiceListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dao = MainViewModel.dao;
        int wordListQuantity = dao.getAllWordlistByLanguageCode(MainViewModel.getUserInfo().getLanguageCode()).length;
        binding.tvCountOfList.setText(String.valueOf(wordListQuantity));

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
        languageCode = getIntent().getIntExtra("languageCode", -1);
        binding.tvCountOfSelected.setText(String.valueOf(0));
        setListview();
        onClick();
    }


    private void onClick() {
        binding.btnAdd.setOnClickListener(v -> {
            insertTodayWordList(((ChoiceAdapter)binding.lv.getAdapter()).getSelectedWordList());
        });
    }

    private void setListview() {

        if(binding.lv.getAdapter() == null) {
            binding.lv.setAdapter(new ChoiceAdapter(new AdapterCallBack() {
                @Override
                public void callBack(boolean check) {

                    if(check) {
                        int count = Integer.parseInt(binding.tvCountOfSelected.getText().toString().trim());
                        count++;
                        binding.tvCountOfSelected.setText(String.valueOf(count));
                    } else {
                        int count = Integer.parseInt(binding.tvCountOfSelected.getText().toString().trim());
                        count--;
                        binding.tvCountOfSelected.setText(String.valueOf(count));
                    }
                }

                @Override
                public void callBackIndex(ArrayList<WordList> wordLists) {
                    insertTodayWordList(wordLists);
                }
            }));
        }
        ((ChoiceAdapter)binding.lv.getAdapter()).setWordLists(new ArrayList<>(Arrays.asList(dao.getAllWordlistByLanguageCode(languageCode))));
        ((ChoiceAdapter)binding.lv.getAdapter()).notifyDataSetChanged();
    }

    private void insertTodayWordList(ArrayList<WordList> wordLists) {
        for(WordList wordList : wordLists) {
            TodayWordList todayWordList = new TodayWordList(languageCode, wordList.getListCodeInt(), false);
            dao.insertTodayList(todayWordList);
        }
        Toast.makeText(getApplicationContext(), "지정되었습니다", Toast.LENGTH_SHORT).show();
        finish();
    }
}