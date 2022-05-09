package com.wons.wordmanager3ver.todayword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.wons.wordmanager3ver.databinding.ActivityTodayWordBinding;

public class TodayWordActivity extends AppCompatActivity {
    private ActivityTodayWordBinding binding;
    private TodayWordViewModel viewModel;
    private int listCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTodayWordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(TodayWordViewModel.class);
        listCode = getIntent().getIntExtra("listCode", -1);
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        setView();
    }

    private void setView() {
        if(binding.lvMyWordList.getAdapter() == null) {
            binding.lvMyWordList.setAdapter(new TodayWordAdapter());
        }

        ((TodayWordAdapter)binding.lvMyWordList.getAdapter()).setAdapterData(
                viewModel.getWordTitleArr(listCode),
                viewModel.getWordInfoMap(viewModel.getWordTitleArr(listCode))
        );

        binding.tvListCountInResult.setText(viewModel.getWordListWordQuantity(listCode));
        binding.tvTitle.setText(viewModel.getListName(listCode));

        ((TodayWordAdapter)binding.lvMyWordList.getAdapter()).notifyDataSetChanged();
    }
}