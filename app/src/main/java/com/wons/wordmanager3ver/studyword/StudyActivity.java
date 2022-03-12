package com.wons.wordmanager3ver.studyword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.wons.wordmanager3ver.databinding.ActivityStudyBinding;
import com.wons.wordmanager3ver.datavalues.EnumLanguage;
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

        //todo 뷰모델에서 데이터 가공 후 버튼을 누를때 뷰모델을 참조하영 셋뷰 해줌
    }

    private void setLanguageTitle() {
        String languageTitle = EnumLanguage.ENGLISH.getLanguage();
        binding.tvLanguage.setText(languageTitle);
    }

    private void setWordView() {
        ArrayList<WordList> wordLists = new ArrayList<>();

    }
}