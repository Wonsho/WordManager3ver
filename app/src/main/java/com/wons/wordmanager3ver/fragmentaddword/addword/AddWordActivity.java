package com.wons.wordmanager3ver.fragmentaddword.addword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.databinding.ActivityAddWordBinding;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordInfo;
import com.wons.wordmanager3ver.datavalues.WordList;
import com.wons.wordmanager3ver.fragmentaddword.addword.adapter.AddWordAdapter;
import com.wons.wordmanager3ver.fragmentaddword.addword.dialogIutils.AddWordCallbackGetString;
import com.wons.wordmanager3ver.fragmentaddword.addword.dialogIutils.AddWordDialogs;

import java.util.ArrayList;

public class AddWordActivity extends AppCompatActivity {
    static final int NON = 0;
    static final int SAME_WORD_IN_DB = 1;
    static final int SAME_WORD_IN_LIST = 2;
    private ActivityAddWordBinding binding;
    private AddWordViewModel viewModel;
    private int listCode;
    private AlertDialog dialog;
    private AlertDialog dialogForRename;
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

        dialog = new AddWordDialogs().getDialogDForAddWord(AddWordActivity.this, new AddWordCallbackGetString() {
            @Override
            public void callback() {
                dialog.dismiss();
            }

            @Override
            public void callback(ArrayList<String> words) {
                if(viewModel.getWordListMutableLiveData().getValue().getWordCountInt() == 20) {
                    Toast.makeText(getApplicationContext(), "단어는 20개까지 추가 할 수 있습니다", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    return;
                } else {
                    int resultCode = viewModel.checkWord(words);
                    Log.e("resultCode" , String.valueOf(resultCode));
                    switch (resultCode) {
                        case NON : {
                            viewModel.insertWord(words);
                            Toast.makeText(getApplicationContext(), "저장되었습니다", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case SAME_WORD_IN_DB: {
                            showDialogForAskAddWord();
                            break;
                        }
                        case SAME_WORD_IN_LIST: {
                            Toast.makeText(getApplicationContext(), "중복 단어가 리스트에 있습니다",Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            }
        });

        binding.btnAddWord.setOnClickListener( v-> {
            dialog.show();
        });
        setListView();
    }

    private void showDialogForAskAddWord() {
        //todo 리스트안에 단어가 존재하여 단어추가를 덮어 씌울껀지 묻는 다이로그 띄우기
    }


    private void setListView() {
        if(binding.lvWord.getAdapter() == null) {
            binding.lvWord.setAdapter(new AddWordAdapter());
        }
        ((TextView)binding.tvWordCount).setText(String.valueOf(viewModel.getWordListMutableLiveData().getValue().getWordCountInt()));
        ((AddWordAdapter)binding.lvWord.getAdapter()).setWords(viewModel.getWords(), viewModel.getWordInfo());
        if(binding.lvWord.getAdapter().getCount() != 0) {
            binding.tvInfo.setVisibility(View.GONE);
        } else {
            binding.tvInfo.setVisibility(View.VISIBLE);
        }
        ((AddWordAdapter)binding.lvWord.getAdapter()).notifyDataSetChanged();
    }
}