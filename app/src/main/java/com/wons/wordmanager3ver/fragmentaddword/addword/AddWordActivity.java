package com.wons.wordmanager3ver.fragmentaddword.addword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.databinding.ActivityAddWordBinding;
import com.wons.wordmanager3ver.databinding.DialogAddWordBinding;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordInfo;
import com.wons.wordmanager3ver.datavalues.WordList;
import com.wons.wordmanager3ver.fragmentaddword.addword.adapter.AddWordAdapter;
import com.wons.wordmanager3ver.fragmentaddword.addword.dialogIutils.ActionCallback;
import com.wons.wordmanager3ver.fragmentaddword.addword.dialogIutils.AddWordCallbackGetString;
import com.wons.wordmanager3ver.fragmentaddword.addword.dialogIutils.AddWordDialogs;
import com.wons.wordmanager3ver.fragmentaddword.addword.dialogIutils.EnumAction;
import com.wons.wordmanager3ver.fragmentaddword.memo.MemoActivity;
import com.wons.wordmanager3ver.fragmentaddword.sameword.CheckSameWordActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class AddWordActivity extends AppCompatActivity {
    private ActivityAddWordBinding binding;
    private AddWordViewModel viewModel;
    private int listCode;
    private AlertDialog dialogForAddWord;
    private AlertDialog dialogForRename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddWordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(AddWordViewModel.class);
        listCode = getIntent().getIntExtra("listCode", -1);
        viewModel.setLiveData(listCode);
        viewModel.getWordListMutableLiveData().observe(this, wordList -> {
            viewModel.updateWordList(wordList);
        });

        dialogForAddWord = makeDialogForAddWord();

        ((TextView) binding.tvLanguage).setText(viewModel.getWordListMutableLiveData().getValue().listName);

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        binding.btnAddWord.setOnClickListener(v -> {
            if (viewModel.getWordCount() != 20) {
                dialogForAddWord.show();
            } else {
                Toast.makeText(getApplicationContext(), "단어장에 단어는 20개만 저장가능 합니다", Toast.LENGTH_LONG).show();
            }
        });

        setWordListView();
    }


    private AlertDialog makeDialogForAddWord() {
        AlertDialog alertDialog = new AddWordDialogs().getDialogDForAddWord(AddWordActivity.this, new AddWordCallbackGetString() {
            @Override
            public void callback() {
                dialogForAddWord.dismiss();
            }

            @Override
            public void callback(ArrayList<String> words) {
                int resultCode = viewModel.getResultCodeWhenAddWord(words);

                switch (resultCode) {
                    case AddWordViewModel.NON: {
                        if (viewModel.getWordCount() == 20) {
                            Toast.makeText(getApplicationContext(), "단어장에 단어는 20개만 저장가능 합니다", Toast.LENGTH_LONG).show();
                            dialogForAddWord.dismiss();
                            return;
                        }
                        viewModel.insertWord(words);
                        Toast.makeText(getApplicationContext(), "저장 되었습니다", Toast.LENGTH_SHORT).show();
                        setWordListView();
                        break;
                    }

                    case AddWordViewModel.SAME_WORD_IN_DB: {
                        showCheckActivity(words);
                        break;
                    }

                    case AddWordViewModel.SAME_WORD_IN_LIST: {
                        Toast.makeText(getApplicationContext(), "중복되는 단어가 단어장 안에 있습니다", Toast.LENGTH_LONG).show();
                        break;
                    }
                }

            }
        });
        return alertDialog;
    }

    private void setDialogForRename(Word word) {
        HashMap<String, WordInfo> map = viewModel.getWordInfo();
        AlertDialog alertDialog = new AddWordDialogs().getDialogDForAddWord(
                AddWordActivity.this, new AddWordCallbackGetString() {
                    @Override
                    public void callback() {
                        dialogForRename.dismiss();
                    }

                    @Override
                    public void callback(ArrayList<String> words) {
                        dialogForRename.dismiss();

                    }
                }
        );
        //todo 다이로그에 바인딩으로 넘기기

        DialogAddWordBinding binding;
        binding = DialogAddWordBinding.inflate(getLayoutInflater(), null, false);
        binding.tv.setText("단어 수정하기");
        binding.btnAdd.setText("수정");
        binding.etWordTitle.setText(word.getWordTitle());
        binding.etWordKorean.setText(map.get(word.getWordTitle()).wordKorean);
        dialogForRename = alertDialog;
        dialogForRename.setView(binding.getRoot());

    }


    private void setWordListView() {
        if (binding.lvWord.getAdapter() == null) {
            binding.lvWord.setAdapter(new AddWordAdapter(new ActionCallback() {
                @Override
                public void callbackAction(EnumAction action, Word word) {
                    switch (action) {
                        case RENAME: {
                            setDialogForRename(word);
                            dialogForRename.show();
                            break;
                        }
                        case DELETE: {
                            showDialogForDelete(word);
                            break;
                        }

                        case MEMO: {
                            Intent intent = new Intent(getApplicationContext(), MemoActivity.class);
                            intent.putExtra("wordTitle", word.getWordTitle());
                            intent.putExtra("languageCode", viewModel.getWordListMutableLiveData().getValue().getLanguageCode());
                            startActivity(intent);
                            break;
                        }
                        case SOUND: {
                            // todo TTS(언어코드 가져와서 객체 생성)
                            break;
                        }
                    }
                }
            }));
        }
        ((AddWordAdapter) binding.lvWord.getAdapter()).setWords(viewModel.getAllWordInList(), viewModel.getWordInfo());
        if (binding.lvWord.getAdapter().getCount() == 0) {
            binding.tvInfo.setVisibility(View.VISIBLE);
        } else {
            binding.tvInfo.setVisibility(View.GONE);
        }
        viewModel.setLiveDataCount(viewModel.getAllWordInList());
        binding.tvWordCount.setText(String.valueOf(viewModel.getWordCount()));
        ((AddWordAdapter) binding.lvWord.getAdapter()).notifyDataSetChanged();
    }

    private void showDialogForDelete(Word word) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage(word.getWordTitle() + " 단어를 삭제하시겠습니까?");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                viewModel.deleteWord(word);
                onRestart();
            }
        });
        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }



    private void showCheckActivity(ArrayList<String> words) {
        Intent intent = new Intent(getApplicationContext(), CheckSameWordActivity.class);
        intent.putExtra("wordTitle", words.get(AddWordViewModel.WORD_TITLE));
        intent.putExtra("wordKorean", words.get(AddWordViewModel.WORD_KOREAN));
        intent.putExtra("languageCode", viewModel.getWordListMutableLiveData().getValue().getLanguageCode());
        intent.putExtra("listCode", viewModel.getWordListMutableLiveData().getValue().getListCodeInt());
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setWordListView();
    }
}