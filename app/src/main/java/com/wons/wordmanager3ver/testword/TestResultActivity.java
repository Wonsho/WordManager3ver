package com.wons.wordmanager3ver.testword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.wons.wordmanager3ver.databinding.ActivityTestResultBinding;
import com.wons.wordmanager3ver.testword.adapter.ResultAdapter;

import javax.xml.transform.Result;

public class TestResultActivity extends AppCompatActivity {

    private ActivityTestResultBinding binding;
    private TestResultActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(TestResultActivityViewModel.class);

        viewModel.setIndexOfLiveData();
        viewModel.indexOfLiveData.observe(this, index -> {
            setActiveView();
        });

        viewModel.setTodayWordLists();
        viewModel.setTestWordMap();
        viewModel.setWordInfoData();
        viewModel.initIndexOfLiveData();
        setViewInit();

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        binding.btnChecked.setOnClickListener(v -> {
            finish();
        });

        binding.btnLeft.setOnClickListener(v -> {
            if(viewModel.indexOfLiveData.getValue() == 0) {
                return;
            }
            viewModel.indexOfLiveData.setValue(viewModel.indexOfLiveData.getValue() - 1);
        });

        binding.btnRight.setOnClickListener(v -> {
            if(viewModel.indexOfLiveData.getValue() + 1 == viewModel.getWordListSize()) {
                return;
            }
            viewModel.indexOfLiveData.setValue(viewModel.indexOfLiveData.getValue() + 1);
        });
    }


    @SuppressLint("SetTextI18n")
    private void setActiveView() {
        //todo 라이브 데이터 옵져브
        binding.tvListName.setText(viewModel.getListTitle());
        binding.tvListCount.setText("[" + (viewModel.indexOfLiveData.getValue() + 1) + "/" + viewModel.getWordListSize() + "]");
        setResultListView();
    }

    private void setResultListView() {
        if (binding.lvWord.getAdapter() == null) {
            binding.lvWord.setAdapter(new ResultAdapter());
        }
        ((ResultAdapter) binding.lvWord.getAdapter()).setArrayList(viewModel.getTestResultArr());
        ((ResultAdapter) binding.lvWord.getAdapter()).setWordInfoHashMap(viewModel.wordInfoHashMap);
        ((ResultAdapter) binding.lvWord.getAdapter()).notifyDataSetChanged();
    }


    private void setViewInit() {
        binding.tvLanguage.setText(viewModel.getLanguage());// 언어
        binding.tvListCountInResult.setText(String.valueOf(viewModel.getWordListSize()));// 단어장 갯수
        binding.tvWordCount.setText(String.valueOf(viewModel.getWordCount()));// 단어 개수
        binding.tvCorrectCount.setText(viewModel.getCorrectWordCount() + "/" + viewModel.getWordCount());// 맞은 단어 개수
        binding.tvPass.setText(viewModel.getTestResult());// 합격 여부
    }
}