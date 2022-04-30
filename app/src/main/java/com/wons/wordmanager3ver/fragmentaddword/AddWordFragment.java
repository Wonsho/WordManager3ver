package com.wons.wordmanager3ver.fragmentaddword;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.ConditionVariable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.databinding.FragmentAddWordBinding;
import com.wons.wordmanager3ver.datavalues.EnumLanguage;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordList;
import com.wons.wordmanager3ver.fragmentaddword.adapter.AdapterCallback;
import com.wons.wordmanager3ver.fragmentaddword.adapter.EnumAction;
import com.wons.wordmanager3ver.fragmentaddword.adapter.WordListAdapter;
import com.wons.wordmanager3ver.fragmentaddword.addword.AddWordActivity;
import com.wons.wordmanager3ver.fragmentaddword.dialogutils.DialogAddWodCallback;
import com.wons.wordmanager3ver.fragmentaddword.dialogutils.DialogInAddWordFragments;
import com.wons.wordmanager3ver.fragmentaddword.sameword.CheckSameWordActivity;
import com.wons.wordmanager3ver.tool.Tools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AddWordFragment extends Fragment {

    private FragmentAddWordBinding binding;
    private AlertDialog dialogForAddList;
    private Dialog dialogForReName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddWordBinding.inflate(inflater, container, false);
        setLanguageTitle();
        onClick();
        setWordlist();

        dialogForAddList = new DialogInAddWordFragments().makeDialogForAddList(getContext(), new DialogAddWodCallback() {
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
                        Toast.makeText(getContext(), "추가 되었습니다", Toast.LENGTH_SHORT).show();
                        ((TextView) dialogForAddList.findViewById(R.id.et_wordTitle)).setText("");
                        dialogForAddList.dismiss();
                    } else {
                        Toast.makeText(getContext(), "중복되는 리스트가 존재합니다", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    ((TextView) dialogForAddList.findViewById(R.id.et_wordTitle)).setText("");
                    Toast.makeText(getContext(), "이름을 적어주세요", Toast.LENGTH_SHORT).show();
                }
            }
        }, EnumDo.ADD);

        return binding.getRoot();
    }

    private void onClick() {

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
            Intent intent = new Intent(getActivity(), AddWordActivity.class);
            intent.putExtra("listCode",listCode);
            startActivity(intent);
        });


    }



    private void setLanguageTitle() {
        EnumLanguage[] enumLanguages = EnumLanguage.values();
        int languageCode = MainViewModel.getUserInfo().getLanguageCode();
        for (EnumLanguage enumLanguage : enumLanguages) {
            if (languageCode == enumLanguage.languageCodeInt) {
                binding.tvLanguage.setText(enumLanguage.getLanguage());
            }
        }
    }

    private void setWordlist() {
        if (binding.lvMyWordList.getAdapter() == null) {
            binding.lvMyWordList.setAdapter(new WordListAdapter(new AdapterCallback() {
                @Override
                public void callback(WordList wordList, EnumAction action) {

                    switch (action) {
                        case DELETE: {
                            AlertDialog alertDialog = new DialogInAddWordFragments().getAlertDialogForCheck(getContext(), new DialogAddWodCallback() {
                                @Override
                                public void callBack(Boolean check) {
                                    if (check) {
                                        MainViewModel.deleteWordList(wordList);
                                        Toast.makeText(getContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                                        setWordlist();
                                    }
                                }

                                @Override
                                public void callBack(String name) {

                                }
                            }, wordList.listName);
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
         dialogForReName = new DialogInAddWordFragments().makeDialogForAddList(getContext(), new DialogAddWodCallback() {
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
                        Toast.makeText(getContext(), "변경 되었습니다", Toast.LENGTH_SHORT).show();
                        ((TextView) dialogForReName.findViewById(R.id.et_wordTitle)).setText("");
                        dialogForReName.dismiss();
                    } else {
                        Toast.makeText(getContext(), "중복되는 리스트가 존재합니다", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    ((TextView) dialogForReName.findViewById(R.id.et_wordTitle)).setText("");
                    Toast.makeText(getContext(), "이름을 적어주세요", Toast.LENGTH_SHORT).show();
                }
            }
        }, EnumDo.RENAME);
        dialogForReName.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("onstart" , "Passed");
        setWordlist();
    }
}