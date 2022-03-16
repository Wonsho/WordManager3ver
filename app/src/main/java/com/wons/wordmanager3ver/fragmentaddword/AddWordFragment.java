package com.wons.wordmanager3ver.fragmentaddword;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.ConditionVariable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.databinding.FragmentAddWordBinding;
import com.wons.wordmanager3ver.datavalues.EnumLanguage;
import com.wons.wordmanager3ver.datavalues.WordList;
import com.wons.wordmanager3ver.fragmentaddword.adapter.WordListAdapter;
import com.wons.wordmanager3ver.fragmentaddword.addword.AddWordActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AddWordFragment extends Fragment {

    private FragmentAddWordBinding binding;
    private AlertDialog dialogForAddList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddWordBinding.inflate(inflater, container, false);
        setLanguageTitle();
        onClick();
        setSearchList();
        setWordlist();
        dialogForAddList = makeDialogForAddList();
        dialogForAddList.setContentView(R.layout.dialog_add_list);
        return binding.getRoot();
    }

    private void onClick() {
        //todo 검색 버튼 Et 가 비어 있으면 실행 불가
        binding.btnSearch.setOnClickListener(v -> {
            if (!binding.etSearch.getText().toString().isEmpty()) {
                // 비어 있지 않으면
                //todo 프로그래스 3초간 보여주고 카드뷰 띄워주기
            }
        });

        binding.btnAddList.setOnClickListener(v -> {
            dialogForAddList.show();
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

    private void setSearchList() {
        if (binding.lvSearch.getAdapter() == null) {

        }
    }

    private void setWordlist() {
        if (binding.lvMyWordList.getAdapter() == null) {
            binding.lvMyWordList.setAdapter(new WordListAdapter());
        }

        ((WordListAdapter) binding.lvMyWordList.getAdapter()).setLists(MainViewModel.getAllWordListByLanguageCode(
                MainViewModel.getUserInfo().getLanguageCode())
        );

        if (binding.lvMyWordList.getAdapter().getCount() == 0) {
            binding.tvWordList.setVisibility(View.VISIBLE);
        } else {
            binding.tvWordList.setVisibility(View.GONE);
        }

        ((WordListAdapter) binding.lvMyWordList.getAdapter()).notifyDataSetChanged();
    }

    private AlertDialog makeDialogForAddList() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_list, null);
        TextView btn_cancel = view.findViewById(R.id.btn_cancel);
        TextView btn_add = view.findViewById(R.id.btn_add);
        EditText et_listName = view.findViewById(R.id.et_wordTitle);

        btn_cancel.setOnClickListener(v -> {
            et_listName.setText("");
            dialogForAddList.dismiss();
        });

        btn_add.setOnClickListener(v -> {
            Date time = new Date();
            SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
            String date = fm.format(time);
            if (!et_listName.getText().toString().isEmpty()) {
                if (MainViewModel.checkSameWordList(MainViewModel.getUserInfo().getLanguageCode(), et_listName.getText().toString().trim()) == 0) {
                    MainViewModel.insertWordList(new WordList(et_listName.getText().toString().trim(),
                            MainViewModel.getUserInfo().getLanguageCode(), date));
                    setWordlist();
                    et_listName.setText("");
                    dialogForAddList.dismiss();
                } else {
                    Toast.makeText(getContext(), "중복되는 단어장이 존재합니다", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "이름을 적어주세요", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setView(view);
        return builder.create();
    }
}