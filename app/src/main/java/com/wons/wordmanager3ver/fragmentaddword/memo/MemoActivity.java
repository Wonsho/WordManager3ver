package com.wons.wordmanager3ver.fragmentaddword.memo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.databinding.ActivityMemoBinding;
import com.wons.wordmanager3ver.datavalues.WordInfo;

public class MemoActivity extends AppCompatActivity {
    private ActivityMemoBinding binding;
    private MemoViewModel viewModel;
    private String wordTitle;
    private int languageCode;
    private WordInfo wordInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMemoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MemoViewModel.class);
        wordTitle = getIntent().getStringExtra("wordTitle");
        this.languageCode = getIntent().getIntExtra("languageCode", -1);
        this.wordInfo = viewModel.getWordInfo(wordTitle, languageCode);
        initView();
        showSoftKeyboard();


        binding.btnSave.setOnClickListener( v -> {
            String memo = binding.etMemo.getText().toString().trim();
            this.wordInfo.setWordMemo(memo);
            viewModel.insertMemo(wordInfo);
            finish();
        });

        binding.btnBack.setOnClickListener( v -> {
          onBackPressed();
        });

    }

    private void initView() {
        binding.tvTitle.setText(wordInfo.getWordEnglish());
        String memo = wordInfo.getWordMemo();
        if(memo == null || memo.isEmpty()) {
            binding.etMemo.setText("");
        } else {
            binding.etMemo.setText(wordInfo.getWordMemo());
        }
    }

    @Override
    public void onBackPressed() {
        if(viewModel.checkSameData(wordInfo, binding.etMemo.getText().toString().trim())) {
            buildDialogWhenBack();
            return;
        }
        super.onBackPressed();

    }

    private void showSoftKeyboard() {
        View view = binding.etMemo;
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            boolean isShowing = imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            if (!isShowing) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }
        }
    }

    private void buildDialogWhenBack() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage("변경 사항이 있습니다 저장 후 나가시겠습니까?");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                WordInfo info = viewModel.getWordInfo(wordTitle, languageCode);
                info.setWordMemo(binding.etMemo.getText().toString().trim());
                viewModel.insertMemo(info);
                finish();
            }
        });
        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
       builder.create().show();
    }

}