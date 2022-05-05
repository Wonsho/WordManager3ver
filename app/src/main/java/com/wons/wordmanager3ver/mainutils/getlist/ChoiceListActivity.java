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
    private int maxCount;
    private int languageCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChoiceListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dao = MainViewModel.dao;
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        maxCount = getIntent().getIntExtra("maxCount", -1);
        languageCode = getIntent().getIntExtra("languageCode", -1);

        if(maxCount == -1) {
            Log.e("ChoiceActivity", "Error");
            finish();
        }
        binding.tvCountOfList.setText(String.valueOf(maxCount));
        binding.tvCountOfSelected.setText(String.valueOf(0));
        setListview();
    }

    private void setListview() {
        if(binding.lv.getAdapter() == null) {
            binding.lv.setAdapter(new ChoiceAdapter(maxCount, new AdapterCallBack() {
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