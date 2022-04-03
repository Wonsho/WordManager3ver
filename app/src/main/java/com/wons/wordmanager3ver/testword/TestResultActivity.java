package com.wons.wordmanager3ver.testword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.wons.wordmanager3ver.databinding.ActivityTestResultBinding;
import com.wons.wordmanager3ver.testword.adapter.ResultAdapter;

public class TestResultActivity extends AppCompatActivity {

    private ActivityTestResultBinding binding;
    private TestResultActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(TestResultActivityViewModel.class);
        viewModel.initData();
        viewModel.listIndexLiveData.observe(this, index -> {
            setListView();
        });
        binding.btnChecked.setOnClickListener(v -> {

        });

        binding.btnBack.setOnClickListener( v -> {

        });

        binding.btnLeft.setOnClickListener(v -> {

        });

        binding.btnRight.setOnClickListener(v -> {

        });

    }

    private void setResultListView() {

    }

    private void setResultText() {
        binding.tvLanguage.setText(viewModel.getLanguage());
        binding.tvListCountInResult.setText(viewModel.getListCountInResult());
        binding.tvWordCount.setText(viewModel.getWordCount());
        binding.tvCorrectCount.setText(viewModel.getCorrectWordCount());
        binding.tvPass.setText();
    }

    private void setListView() {
        if(binding.lvWord.getAdapter() == null) {
            binding.lvWord.setAdapter(new ResultAdapter());
        }
        ((ResultAdapter)binding.lvWord.getAdapter()).setArrayList(viewModel.getTestResult());
        ((ResultAdapter)binding.lvWord.getAdapter()).setWordInfoHashMap(viewModel.getMapByNowList());
        ((ResultAdapter)binding.lvWord.getAdapter()).notifyDataSetChanged();
    }
}