package com.wons.wordmanager3ver.fragmentaddword.addword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.databinding.ActivityAddWordBinding;
import com.wons.wordmanager3ver.datavalues.WordList;
import com.wons.wordmanager3ver.fragmentaddword.addword.adapter.AddWordAdapter;

public class AddWordActivity extends AppCompatActivity {

    private ActivityAddWordBinding binding;
    private AddWordViewModel viewModel;
    private int listCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddWordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(AddWordViewModel.class);
        listCode = getIntent().getIntExtra("listCode",-1);
        viewModel.setLiveData(listCode);
        viewModel.getWordListMutableLiveData().observe(this, wordList -> {
            viewModel.updateWordList(wordList);
        });

        ((TextView)binding.tvLanguage).setText(viewModel.getWordListMutableLiveData().getValue().listName);
        binding.btnBack.setOnClickListener( v -> {
            finish();
        });

        setListView();
    }

    public void setListView() {
        if(binding.lvWord.getAdapter() == null) {
            binding.lvWord.setAdapter(new AddWordAdapter());
        }

        if(binding.lvWord.getAdapter().getCount() != 0) {
            binding.tvInfo.setVisibility(View.GONE);
        } else {
            binding.tvInfo.setVisibility(View.VISIBLE);
        }
        ((AddWordAdapter)binding.lvWord.getAdapter()).setWords(viewModel.getWords(), viewModel.getWordInfo());
        ((AddWordAdapter)binding.lvWord.getAdapter()).notifyDataSetChanged();
    }
}