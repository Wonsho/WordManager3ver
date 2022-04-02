package com.wons.wordmanager3ver.testword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.wons.wordmanager3ver.databinding.ActivityTestBinding;

public class TestActivity extends AppCompatActivity {
    private ActivityTestBinding binding;
    private TestActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(TestActivityViewModel.class);
        viewModel.init();
        viewModel.liveDataOfIndex.observe(this, index -> {

            if(index == viewModel.getWordSize()) {
                return;
            }

            setView();
        });
        viewModel.setData();
        showSoftKeyboard();
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        binding.btnEnter.setOnClickListener(v -> {
            clickEnter();
        });
    }

    private void clickEnter() {
        if (binding.etWordTitle.getText().toString().isEmpty()) {
            binding.etWordTitle.setError("빈칸입니다");
            return;
        }

        viewModel.addWordResult(binding.etWordTitle.getText().toString().trim());
        int nowIndex = viewModel.getNowIndex();
        viewModel.setLiveDataOfIndex(nowIndex + 1);

        if (viewModel.getNowIndex() == viewModel.getWordSize()) {
            viewModel.insertWordResultInDB(); // 시험이 다끝나면 DB에 저장 --> 결과 액티비티 띄우기
            Intent intent = new Intent(TestActivity.this, TestResultActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        binding.etWordTitle.setText("");
        showSoftKeyboard();
    }

    @SuppressLint("SetTextI18n")
    private void setView() {
        binding.tvWordCount.setText(viewModel.getLiveDataOfIndex() + 1 + "/" + viewModel.getWordSize());
        binding.tvWordKorean.setText(viewModel.getWordKorean());
    }

    private void showSoftKeyboard() {
        View view = binding.etWordTitle;
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            boolean isShowing = imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            if (!isShowing) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }
        }
    }

}