package com.wons.wordmanager3ver.studyfragment.studyword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wons.wordmanager3ver.databinding.ActivityStudyBinding;
import com.wons.wordmanager3ver.datavalues.EnumLanguage;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordList;
import com.wons.wordmanager3ver.tool.Tools;

public class StudyActivity extends AppCompatActivity {
    private ActivityStudyBinding binding;
    private StudyActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(StudyActivityViewModel.class);
        setLanguageTitle();
        setOnclick();
        init();

    }

    private void setOnclick() {

        // 백 버튼
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        //리스트 왼쪽 버튼
        binding.btnLeft.setOnClickListener(v -> {
            Log.e("Button", "onc");
            String[] strArr = binding.tvListCount.getText().toString().split("/");
            String indexString = strArr[0].trim().substring(1).trim();
            if(Integer.parseInt(indexString) == 1) {
                Log.e("Button", "onc2");
                return;
            } else {
                viewModel.wordListMutableLiveData.setValue(viewModel.getWordLists().get(Integer.parseInt(indexString) - 2));
            }
        });

        //리스트 오른쪽 버튼
        binding.btnRight.setOnClickListener(v -> {
            String[] strArr = binding.tvListCount.getText().toString().split("/");
            String indexString = strArr[0].trim().substring(1).trim();
            if(Integer.parseInt(indexString) == viewModel.getWordLists().size()) {
                return;
            } else {
                viewModel.wordListMutableLiveData.setValue(viewModel.getWordLists().get(Integer.parseInt(indexString)));
            }
        });


        //단어 오른쪽 버튼
        binding.btnWordRight.setOnClickListener(v -> {
            String[] wordArr = binding.tvCountOfWord.getText().toString().trim().split("/");
            if(Integer.parseInt(wordArr[0].trim()) == viewModel.getSelectedWords().size()) {
                return;
            } else {
                viewModel.wordMutableLiveData.setValue(viewModel.getSelectedWords().get(
                        Integer.parseInt(wordArr[0])
                ));
            }
        });

        //단어 소리 버튼
        binding.btnWordSound.setOnClickListener(v -> {
            new Tools().speech(binding.tvWordTitle.getText().toString().trim());
        });

        //단어 왼쪽 버튼
        binding.btnWordLeft.setOnClickListener(v -> {
            String[] wordArr = binding.tvCountOfWord.getText().toString().trim().split("/");
            if(Integer.parseInt(wordArr[0].trim()) == 1) {
                Log.e("word", "Passed");
                return;
            } else {
                viewModel.wordMutableLiveData.setValue(viewModel.getSelectedWords().get(
                        Integer.parseInt(wordArr[0]) - 2
                ));
            }
        });


    }

    private void setLanguageTitle() {
        String languageTitle = EnumLanguage.ENGLISH.getLanguage();
        binding.tvLanguage.setText(languageTitle);
    }

    private void init() {
        viewModel.setStudyData();
        viewModel.setWordListMutableLiveData();
        viewModel.setWordMutableLiveData();
        viewModel.wordMutableLiveData.observe(this, word -> {
            //todo 단어 셋팅
            setWord(word);
        });
        viewModel.wordListMutableLiveData.observe(this, wordList -> {
            //todo 단어장 셋팅
            setWordList(wordList);
            String listName = wordList.listName;
            viewModel.wordMutableLiveData.setValue(viewModel.getWordMap().get(listName).get(0));
        });
        viewModel.wordListMutableLiveData.setValue(viewModel.getWordLists().get(0));
        
        //todo 초반 단어장 셋팅
    }

    @SuppressLint("SetTextI18n")
    private void setWordList(WordList wordList) {
        binding.tvListName.setText(wordList.listName);
        binding.tvListCount.setText("[" + (viewModel.getWordLists().indexOf(wordList) + 1) + "/" + viewModel.getWordLists().size() + "]");
    }

    private void setWord(Word word) {
        binding.tvWordTitle.setText(word.getWordTitle());
        binding.tvCountOfWord.setText(String.valueOf(viewModel.getSelectedWords().indexOf(word) + 1 + "/" + viewModel.getSelectedWords().size()));
        binding.tvKorean.setText(viewModel.getWordInfoByWord(word).wordKorean);
        String percentage = String.valueOf(viewModel.getWordInfoByWord(word).getCorrectPercentage()) + "%";
        if(percentage.equals("-1%")) {
            percentage = "데이터 없음";
        }
        String memo = viewModel.getWordInfoByWord(word).getWordMemo();
        binding.tvWordCorrectPercentage.setText(percentage);
        if (memo.equals("")) {
            binding.m.setVisibility(View.GONE);
            binding.line4.setVisibility(View.GONE);
        } else {
            binding.m.setVisibility(View.VISIBLE);
            binding.line4.setVisibility(View.VISIBLE);
        }
        binding.tvMemo.setText(memo);

    }

}