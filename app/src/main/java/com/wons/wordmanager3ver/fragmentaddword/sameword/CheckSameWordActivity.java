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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckSameWordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(CheckListViewModel.class);

        this.wordTitle = getIntent().getStringExtra("wordTitle");
        this.word_korean = getIntent().getStringExtra("wordKorean");
        this.languageCode = getIntent().getIntExtra("languageCode", -1);
        setView();
        setList();

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        binding.btnCancel.setOnClickListener(v -> {
            finish();
        });

        binding.btnNewWord.setOnClickListener(v -> {

            finish();
        });

        binding.btnOriginWord.setOnClickListener(v -> {

            finish();
        });
    }

    private void setView() {
        binding.tvNewWordTitle.setText(wordTitle);
        binding.tvNewWordMean.setText(word_korean);
        WordInfo info = viewModel.getWordInfo(wordTitle.toUpperCase());
        binding.tvOriginWordTitle.setText(info.getWordEnglish());
        binding.tvOriginWordMean.setText(info.wordKorean);
    }

    private void setList() {
        if(binding.lvWordlist.getAdapter() == null) {
            binding.lvWordlist.setAdapter(new CheckListAdapter());
        }
        ((CheckListAdapter)binding.lvWordlist.getAdapter()).setList(viewModel.getSameWordList(wordTitle, languageCode));
        ((CheckListAdapter)binding.lvWordlist.getAdapter()).notifyDataSetChanged();
    }

}