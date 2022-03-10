package com.wons.wordmanager3ver.fragmentaddword;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.databinding.FragmentAddWordBinding;
import com.wons.wordmanager3ver.datavalues.EnumLanguage;

public class AddWordFragment extends Fragment {

    private FragmentAddWordBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddWordBinding.inflate(inflater, container, false);
        setLanguageTitle();
        onClick();
        setSearchList();
        setWordlist();
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

        });
    }

    private void setLanguageTitle() {
        String language = EnumLanguage.ENGLISH.getLanguage();
        binding.tvLanguage.setText(language);
    }

    private void setSearchList() {
        if (binding.lvSearch.getAdapter() == null) {

        }
    }

    private void setWordlist() {
        if(binding.lvMyWordList.getAdapter() == null) {

        }
    }
}