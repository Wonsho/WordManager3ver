package com.wons.wordmanager3ver.fragmentaddword.addword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.wons.wordmanager3ver.databinding.ActivityAddWordBinding;
import com.wons.wordmanager3ver.datavalues.WordList;

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
        viewModel.getWordListMutableLiveData(listCode).observe(this, wordList1 -> {
            viewModel.updateWordList();
        });


    }
}