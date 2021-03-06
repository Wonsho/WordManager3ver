package com.wons.wordmanager3ver.addwordactivity.addword;

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
import com.wons.wordmanager3ver.addwordactivity.addword.adapter.AddWordAdapter;
import com.wons.wordmanager3ver.addwordactivity.addword.dialogIutils.ActionCallback;
import com.wons.wordmanager3ver.addwordactivity.addword.dialogIutils.AddWordCallbackGetString;
import com.wons.wordmanager3ver.addwordactivity.addword.dialogIutils.AddWordDialogs;
import com.wons.wordmanager3ver.addwordactivity.addword.dialogIutils.EnumAction;
import com.wons.wordmanager3ver.addwordactivity.memo.MemoActivity;
import com.wons.wordmanager3ver.addwordactivity.sameword.CheckSameWordActivity;

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
//            if (viewModel.getWordCount() != 20) {
                dialogForAddWord.setCancelable(false);
                dialogForAddWord.show();
//            } else {
//                Toast.makeText(getApplicationContext(), "???????????? ????????? 20?????? ???????????? ?????????", Toast.LENGTH_LONG).show();
//            }
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
//                        if (viewModel.getWordCount() == 20) {
//                            Toast.makeText(getApplicationContext(), "???????????? ????????? 20?????? ???????????? ?????????", Toast.LENGTH_LONG).show();
//                            dialogForAddWord.dismiss();
//                            return;
//                        }
                        viewModel.insertWord(words);
                        Toast.makeText(getApplicationContext(), "?????? ???????????????", Toast.LENGTH_SHORT).show();
                        setWordListView();
                        break;
                    }

                    case AddWordViewModel.SAME_WORD_IN_DB: {
                        showCheckActivity(words, -1,-1);
                        break;
                    }

                    case AddWordViewModel.SAME_WORD_IN_LIST: {
                        Toast.makeText(getApplicationContext(), "???????????? ????????? ????????? ?????? ????????????", Toast.LENGTH_LONG).show();
                        break;
                    }
                }

            }
        }, binding);
        return alertDialog;
    }

    private void setDialogForRename(Word word) { // ????????? ??????
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
                                    //todo ???????????? ????????? ??????
                                    if(viewModel.checkChangedWordInDB(words, word)) {
                                        //todo ????????? ????????? DB??? ??????
                                        Log.e("hasWordInDB", "passed");
                                        showCheckActivity(words, RENAME, word.getWordId());
                                        dialogForRename.dismiss();
                                    } else {
                                        //todo ????????? ????????? DB??? ??????
                                        Log.e("hasNONWordInDB", "passed");
                                        viewModel.updateWordAndNewWordInfo(words, word);
                                        onRestart();
                                        dialogForRename.dismiss();
                                    }

                                } else {
                                    //todo ???????????? ????????? ??????
                                    if(viewModel.checkChangedWordInDB(words, word)) {
                                        showCheckActivity(words,RENAME_AND_DELETE_WORD_INFO, word.getWordId());  //todo DB??? ????????? ????????? ??????
                                        dialogForRename.dismiss();
                                    } else {
                                        viewModel.updateWordAndWordInfo(words, word);   //todo DB??? ????????? ????????? ??????
                                        onRestart();
                                        dialogForRename.dismiss();
                                    }

                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "???????????? ???????????? ?????? ?????????", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, binding);

        binding.tv.setText("?????? ????????????");
        binding.btnAdd.setText("??????");
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
                            dialogForRename.setCancelable(false);
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
        binding.tvWordCount.setText(String.valueOf(viewModel.getWordCount()));
        ((AddWordAdapter) binding.lvWord.getAdapter()).notifyDataSetChanged();
    }

    private void showDialogForDelete(Word word) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("??????");
        builder.setMessage(word.getWordTitle() + " ????????? ?????????????????????????");
        builder.setNegativeButton("???", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                viewModel.deleteWord(word);
                onRestart();
            }
        });
        builder.setPositiveButton("?????????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setCancelable(false);
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
        
        Log.e("onRestart_addWord","pass");
    }

    @Override
    public void finish() {
        viewModel.updateWordListAverage();
        super.finish();
    }

    @Override
    protected void onDestroy() {
        viewModel.updateWordListAverage();
        super.onDestroy();

    }
}