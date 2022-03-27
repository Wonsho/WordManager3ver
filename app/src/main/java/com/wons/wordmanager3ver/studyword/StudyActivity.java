package com.wons.wordmanager3ver.studyword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.wons.wordmanager3ver.databinding.ActivityStudyBinding;
import com.wons.wordmanager3ver.datavalues.EnumLanguage;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordList;

import java.util.ArrayList;

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

        });

        //리스트 오른쪽 버튼
        binding.btnRight.setOnClickListener(v -> {

        });


        //단어 오른쪽 버튼
        binding.btnWordRight.setOnClickListener(v -> {

        });

        //단어 소리 버튼
        binding.btnWordSound.setOnClickListener(v -> {

        });

        //단어 왼쪽 버튼
        binding.btnWordLeft.setOnClickListener(v -> {

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
        binding.tvListCount.setText(String.valueOf(viewModel.getWordLists().indexOf(wordList) + 1 + "/" + viewModel.getWordLists().size()));
    }

    private void setWord(Word word) {
        binding.tvWordTitle.setText(word.getWordTitle());
        binding.tvCountOfWord.setText(String.valueOf(viewModel.getSelectedWords().indexOf(word) + 1 + "/" + viewModel.getSelectedWords().size()));
        binding.tvKorean.setText(viewModel.getWordInfoByWord(word).wordKorean);
        binding.tvMemo.setText(viewModel.getWordInfoByWord(word).getWordMemo());
    }

}