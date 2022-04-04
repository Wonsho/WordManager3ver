package com.wons.wordmanager3ver.fragmentaddword.addword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wons.wordmanager3ver.databinding.ActivityAddWordBinding;
import com.wons.wordmanager3ver.databinding.DialogAddWordBinding;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordInfo;
import com.wons.wordmanager3ver.fragmentaddword.addword.adapter.AddWordAdapter;
import com.wons.wordmanager3ver.fragmentaddword.addword.dialogIutils.ActionCallback;
import com.wons.wordmanager3ver.fragmentaddword.addword.dialogIutils.AddWordCallbackGetString;
import com.wons.wordmanager3ver.fragmentaddword.addword.dialogIutils.AddWordDialogs;
import com.wons.wordmanager3ver.fragmentaddword.addword.dialogIutils.EnumAction;
import com.wons.wordmanager3ver.fragmentaddword.memo.MemoActivity;
import com.wons.wordmanager3ver.fragmentaddword.sameword.CheckSameWordActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class AddWordActivity extends AppCompatActivity {
    private ActivityAddWordBinding binding;
    private AddWordViewModel viewModel;
    private int listCode;
    private AlertDialog dialogForAddWord;
    private AlertDialog dialogForRename;
    public static final int RENAME = 0;
    public static final int RENAME_AND_DELETE_WORD_INFO = 1;


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

        binding.btnView.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.dict.naver.com/#/main")));
        });

        setWordListView();
    }


    private AlertDialog makeDialogForAddWord() {
        DialogAddWordBinding binding;
        binding = DialogAddWordBinding.inflate(getLayoutInflater(), null, false);
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
                        showCheckActivity(words, -1,-1);
                        break;
                    }

                    case AddWordViewModel.SAME_WORD_IN_LIST: {
                        Toast.makeText(getApplicationContext(), "중복되는 단어가 단어장 안에 있습니다", Toast.LENGTH_LONG).show();
                        break;
                    }
                }

            }
        }, binding);
        return alertDialog;
    }

    private void setDialogForRename(Word word) { // 원래의 단어
        HashMap<String, WordInfo> map = viewModel.getWordInfo();
        DialogAddWordBinding binding;
        binding = DialogAddWordBinding.inflate(getLayoutInflater(), null, false);
        AlertDialog alertDialog = new AddWordDialogs().getDialogDForAddWord(
                AddWordActivity.this, new AddWordCallbackGetString() {
                    @Override
                    public void callback() {
                        dialogForRename.dismiss();
                    }

                    @Override
                    public void callback(ArrayList<String> words) {
                        if(word.getWordTitle().trim().toUpperCase().equals(words.get(AddWordViewModel.WORD_TITLE).trim().toUpperCase())) {
                            //todo 3.4
                            if(viewModel.checkWordInfo(words, word)) {
                                dialogForRename.dismiss();
                                return;
                            } else {
                                showCheckActivity(words, RENAME, word.getWordId());
                                dialogForRename.dismiss();
                                return;
                            }

                        } else {
                            //todo 1.2
                            if(!viewModel.checkInList(word, words)) {

                                if(viewModel.checkOriginWordInDB(word)) {
                                    //todo 참조하는 단어가 있음
                                    if(viewModel.checkChangedWordInDB(words, word)) {
                                        //todo 바꾸는 단어가 DB에 있음
                                        Log.e("hasWordInDB", "passed");
                                        showCheckActivity(words, RENAME, word.getWordId());
                                        dialogForRename.dismiss();
                                    } else {
                                        //todo 바꾸는 단어가 DB에 없음
                                        Log.e("hasNONWordInDB", "passed");
                                        viewModel.updateWordAndNewWordInfo(words, word);
                                        onRestart();
                                        dialogForRename.dismiss();
                                    }

                                } else {
                                    //todo 참조하는 단어가 혼자
                                    if(viewModel.checkChangedWordInDB(words, word)) {
                                        showCheckActivity(words,RENAME_AND_DELETE_WORD_INFO, word.getWordId());  //todo DB에 바뀌는 단어가 존재
                                        dialogForRename.dismiss();
                                    } else {
                                        viewModel.updateWordAndWordInfo(words, word);   //todo DB에 바뀌는 단어가 없음
                                        onRestart();
                                        dialogForRename.dismiss();
                                    }

                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "단어장에 존재하는 단어 입니다", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, binding);

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
        binding.tvWordCount.setText(String.valueOf(viewModel.getWordCount()) + "/20");
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


    private void showCheckActivity(ArrayList<String> words, int actionCode, int wordId) {
        Intent intent = new Intent(getApplicationContext(), CheckSameWordActivity.class);
        intent.putExtra("wordTitle", words.get(AddWordViewModel.WORD_TITLE));
        intent.putExtra("wordKorean", words.get(AddWordViewModel.WORD_KOREAN));
        intent.putExtra("languageCode", viewModel.getWordListMutableLiveData().getValue().getLanguageCode());
        intent.putExtra("listCode", viewModel.getWordListMutableLiveData().getValue().getListCodeInt());
        intent.putExtra("actionCode", actionCode);
        intent.putExtra("wordId", wordId);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setWordListView();
    }
}