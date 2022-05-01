package com.wons.wordmanager3ver.fragmentaddword.sameword;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.databinding.ActivityCheckSameWordBinding;
import com.wons.wordmanager3ver.datavalues.WordInfo;
import com.wons.wordmanager3ver.fragmentaddword.addword.AddWordActivity;
import com.wons.wordmanager3ver.fragmentaddword.sameword.adapter.CheckListAdapter;

public class CheckSameWordActivity extends AppCompatActivity {

    private ActivityCheckSameWordBinding binding;
    private String wordTitle;
    private String word_korean;
    private int languageCode;
    private CheckListViewModel viewModel;
    private int listCode;
    private int actionCode;
    private int originWordId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckSameWordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(CheckListViewModel.class);
        overridePendingTransition(R.anim.vertical_enter,R.anim.non);
        this.wordTitle = getIntent().getStringExtra("wordTitle");
        this.word_korean = getIntent().getStringExtra("wordKorean");
        this.languageCode = getIntent().getIntExtra("languageCode", -1);
        this.listCode = getIntent().getIntExtra("listCode", -1);
        this.actionCode = getIntent().getIntExtra("actionCode", -1);
        this.originWordId = getIntent().getIntExtra("wordId", -1);
        setView();
        setList();

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        binding.btnCancel.setOnClickListener(v -> {
            finish();
        });

        binding.btnNewWord.setOnClickListener(v -> {
            switch (actionCode) {
                case AddWordActivity.RENAME: {
                    viewModel.updateWordToNew(originWordId, wordTitle, word_korean);
                    finish();
                    return;
                }
                case AddWordActivity.RENAME_AND_DELETE_WORD_INFO: {
                    viewModel.updateWordAndDeleteWordInfoToNewInfo(originWordId, wordTitle, word_korean);
                    finish();
                    return;
                }
            }
            viewModel.insertNewWord(languageCode,listCode, wordTitle, word_korean);
            finish();
        });

        binding.btnOriginWord.setOnClickListener(v -> {

            switch (actionCode) {
                case AddWordActivity.RENAME: {
                    viewModel.updateWordToOrigin(originWordId, wordTitle);
                    finish();
                    return;
                }
                case AddWordActivity.RENAME_AND_DELETE_WORD_INFO: {
                    viewModel.updateWordAndDeleteWordInfoToOrigin(originWordId, wordTitle);
                    finish();
                    return;
                }
            }

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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.non,R.anim.vertical_exit);
    }
}