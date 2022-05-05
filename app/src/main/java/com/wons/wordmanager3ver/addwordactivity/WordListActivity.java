package com.wons.wordmanager3ver.addwordactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.databinding.ActivityWordListBinding;
import com.wons.wordmanager3ver.datavalues.WordList;
import com.wons.wordmanager3ver.addwordactivity.adapter.AdapterCallback;
import com.wons.wordmanager3ver.addwordactivity.adapter.EnumAction;
import com.wons.wordmanager3ver.addwordactivity.adapter.WordListAdapter;
import com.wons.wordmanager3ver.addwordactivity.addword.AddWordActivity;
import com.wons.wordmanager3ver.addwordactivity.dialogutils.DialogAddWodCallback;
import com.wons.wordmanager3ver.addwordactivity.dialogutils.DialogInAddWordFragments;
import com.wons.wordmanager3ver.tool.Tools;

public class WordListActivity extends AppCompatActivity {

    private ActivityWordListBinding binding;
    private AlertDialog dialogForAddList;
    private Dialog dialogForReName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWordListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setLanguageTitle();
        onClick();
        setWordlist();

        dialogForAddList = new DialogInAddWordFragments().makeDialogForAddList(WordListActivity.this, new DialogAddWodCallback() {
            @Override
            public void callBack(Boolean check) {
                dialogForAddList.dismiss();
            }

            @Override
            public void callBack(String name) {
                if (!name.isEmpty()) {
                    if (MainViewModel.checkSameWordList(MainViewModel.getUserInfo().getLanguageCode(), name.trim()) == 0) {
                        MainViewModel.insertWordList(new WordList(name.trim(), MainViewModel.getUserInfo().getLanguageCode(), new Tools().getNoWDate()));
                        setWordlist();
                        Toast.makeText(getApplicationContext(), "추가 되었습니다", Toast.LENGTH_SHORT).show();
                        ((TextView) dialogForAddList.findViewById(R.id.et_wordTitle)).setText("");
                        dialogForAddList.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "중복되는 리스트가 존재합니다", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    ((TextView) dialogForAddList.findViewById(R.id.et_wordTitle)).setText("");
                    Toast.makeText(getApplicationContext(), "이름을 적어주세요", Toast.LENGTH_SHORT).show();
                }
            }
        }, EnumDo.ADD);


    }
    private void onClick() {
        
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        binding.btnAddList.setOnClickListener(v -> {
//            dialogForAddList.show();
            MainViewModel.insertWordList(new WordList("~", MainViewModel.getUserInfo().getLanguageCode(), new Tools().getNoWDate()));
            WordList[] wordList = MainViewModel.dao.getSelectedWordlist(MainViewModel.getUserInfo().getLanguageCode(), "~");
            WordList wordList1 = wordList[0];
            String listName = "단어장 " + String.valueOf(wordList1.listCodeInt);
            wordList1.listName = listName;
            MainViewModel.updateWordList(wordList1);
            setWordlist();
        });

        binding.lvMyWordList.setOnItemClickListener((adapterView, view, i, l) -> {
            int listCode = ((WordList)((WordListAdapter)binding.lvMyWordList.getAdapter()).getItem(i)).getListCodeInt();
            Intent intent = new Intent(getApplicationContext(), AddWordActivity.class);
            intent.putExtra("listCode",listCode);
            startActivity(intent);
        });


    }



    private void setLanguageTitle() {
//        EnumLanguage[] enumLanguages = EnumLanguage.values();
//        int languageCode = MainViewModel.getUserInfo().getLanguageCode();
//        for (EnumLanguage enumLanguage : enumLanguages) {
//            if (languageCode == enumLanguage.languageCodeInt) {
//                binding.tvLanguage.setText(enumLanguage.getLanguage());
//            }
//        }
    }

    private void setWordlist() {
        if (binding.lvMyWordList.getAdapter() == null) {
            binding.lvMyWordList.setAdapter(new WordListAdapter(new AdapterCallback() {
                @Override
                public void callback(WordList wordList, EnumAction action) {

                    switch (action) {
                        case DELETE: {
                            AlertDialog alertDialog = new DialogInAddWordFragments().getAlertDialogForCheck(WordListActivity.this, new DialogAddWodCallback() {
                                @Override
                                public void callBack(Boolean check) {
                                    if (check) {
                                        MainViewModel.deleteWordList(wordList);
                                        Toast.makeText(getApplicationContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                                        setWordlist();
                                    }
                                }

                                @Override
                                public void callBack(String name) {

                                }
                            }, wordList.listName);
                            alertDialog.setCancelable(false);
                            alertDialog.show();
                            break;
                        }
                        case RENAME: {
                            updateListName(wordList);
                            break;
                        }
                    }
                }
            }));
        }

        ((WordListAdapter) binding.lvMyWordList.getAdapter()).setLists(MainViewModel.getAllWordListByLanguageCode(
                MainViewModel.getUserInfo().getLanguageCode())
        );

        if (binding.lvMyWordList.getAdapter().getCount() == 0) {
            binding.tvWordList.setVisibility(View.VISIBLE);
        } else {
            binding.tvWordList.setVisibility(View.GONE);
        }

        int listCount = ((WordListAdapter) binding.lvMyWordList.getAdapter()).getCount();
        binding.tvListCountInResult.setText(String.valueOf(listCount));
        ((WordListAdapter) binding.lvMyWordList.getAdapter()).notifyDataSetChanged();
    }

    private void updateListName(WordList list) {
        dialogForReName = new DialogInAddWordFragments().makeDialogForAddList(WordListActivity.this, new DialogAddWodCallback() {
            @Override
            public void callBack(Boolean check) {
                dialogForReName.dismiss();
            }
            @Override
            public void callBack(String name) {
                if (!name.isEmpty()) {
                    if (MainViewModel.checkSameWordList(MainViewModel.getUserInfo().getLanguageCode(), name.trim()) == 0) {
                        list.listName = name.trim();
                        MainViewModel.updateWordList(list);
                        setWordlist();
                        Toast.makeText(getApplicationContext(), "변경 되었습니다", Toast.LENGTH_SHORT).show();
                        ((TextView) dialogForReName.findViewById(R.id.et_wordTitle)).setText("");
                        dialogForReName.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "중복되는 리스트가 존재합니다", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    ((TextView) dialogForReName.findViewById(R.id.et_wordTitle)).setText("");
                    Toast.makeText(getApplicationContext(), "이름을 적어주세요", Toast.LENGTH_SHORT).show();
                }
            }
        }, EnumDo.RENAME);
        dialogForReName.setCancelable(false);
        dialogForReName.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("onstart" , "Passed");
        setWordlist();
    }
}

