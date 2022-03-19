package com.wons.wordmanager3ver.fragmentaddword.sameword;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.wons.wordmanager3ver.databinding.ActivityCheckSameWordBinding;
import com.wons.wordmanager3ver.datavalues.WordInfo;
import com.wons.wordmanager3ver.fragmentaddword.sameword.adapter.CheckListAdapter;

public class CheckSameWordActivity extends AppCompatActivity {

    private ActivityCheckSameWordBinding binding;
    private String wordTitle;
    private String word_korean;
    private int languageCode;
    private CheckListViewModel viewModel;
    private int listCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckSameWordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(CheckListViewModel.class);

        this.wordTitle = getIntent().getStringExtra("wordTitle");
        this.word_korean = getIntent().getStringExtra("wordKorean");
        this.languageCode = getIntent().getIntExtra("languageCode", -1);
        this.listCode = getIntent().getIntExtra("listCode", -1);
        setView();
        setList();

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        binding.btnCancel.setOnClickListener(v -> {
            finish();
        });

        binding.btnNewWord.setOnClickListener(v -> {
            viewModel.insertNewWord(languageCode,listCode, wordTitle, word_korean);
            finish();
        });

        binding.btnOriginWord.setOnClickListener(v -> {
            viewModel.insertOriginWord(listCode, languageCode, wordTitle);
            finish();
        });
    }

    private void setView() {
        binding.tvNewWordKorean.setText(word_korean);
        WordInfo info = viewModel.getWordInfo(wordTitle.toUpperCase(), languageCode);
        binding.tvOriginWordTitle.setText(info.getWordEnglish());
        binding.tvOriginWordKorean.setText(info.wordKorean);
    }

    private void setList() {
        if(binding.lvWordlist.getAdapter() == null) {
            binding.lvWordlist.setAdapter(new CheckListAdapter());
        }
        ((CheckListAdapter)binding.lvWordlist.getAdapter()).setList(viewModel.getSameWordList(wordTitle, languageCode));
        ((CheckListAdapter)binding.lvWordlist.getAdapter()).notifyDataSetChanged();
    }

}