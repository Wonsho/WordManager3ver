package com.wons.wordmanager3ver.testfragment.testword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.wons.wordmanager3ver.databinding.ActivityTestResultBinding;
import com.wons.wordmanager3ver.testfragment.testword.adapter.ResultAdapter;

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
            binding.tvListName.setText(viewModel.getListTitle());

        });

        binding.btnRight.setOnClickListener(v -> {
            if(viewModel.indexOfLiveData.getValue() + 1 == viewModel.getWordListSize()) {
                return;
            }
            viewModel.indexOfLiveData.setValue(viewModel.indexOfLiveData.getValue() + 1);
            binding.tvListName.setText(viewModel.getListTitle());

        });
    }


    @SuppressLint("SetTextI18n")
    private void setActiveView() {
        //todo ????????? ????????? ?????????
//        binding.tvListName.setText(viewModel.getListTitle());
        binding.tvListCount.setText("[" + (viewModel.indexOfLiveData.getValue() + 1) + "/" + viewModel.getWordListSize() + "]");
        setResultListView();
    }

    private void setResultListView() {
        if (binding.lvWord.getAdapter() == null) {
            binding.lvWord.setAdapter(new ResultAdapter());
        }
        if(viewModel.getTestResultArr().size() == 0) {
            binding.tvTest.setVisibility(View.VISIBLE);
        } else {
            binding.tvTest.setVisibility(View.GONE);

        }
        ((ResultAdapter) binding.lvWord.getAdapter()).setArrayList(viewModel.getTestResultArr());
        ((ResultAdapter) binding.lvWord.getAdapter()).setWordInfoHashMap(viewModel.wordInfoHashMap);
        ((ResultAdapter) binding.lvWord.getAdapter()).notifyDataSetChanged();
    }


    private void setViewInit() {
        viewModel.updateWordData();
        binding.tvLanguage.setText(viewModel.getLanguage());// ??????
        binding.tvListCountInResult.setText(String.valueOf(viewModel.getWordListSize()));// ????????? ??????
        binding.tvWordCount.setText(String.valueOf(viewModel.getWordCount()));// ?????? ??????
        binding.tvCorrectCount.setText(viewModel.getCorrectWordCount() + "/" + viewModel.getWordCount());// ?????? ?????? ??????
        viewModel.getTestResult();
        binding.tvListName.setText(viewModel.getListTitle());
    }
}