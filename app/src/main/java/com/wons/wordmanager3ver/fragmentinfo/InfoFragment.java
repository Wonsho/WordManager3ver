package com.wons.wordmanager3ver.fragmentinfo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.databinding.FragmentInfoBinding;
import com.wons.wordmanager3ver.datavalues.EnumGrade;
import com.wons.wordmanager3ver.datavalues.EnumLanguage;
import com.wons.wordmanager3ver.datavalues.FlagUserLevelData;
import com.wons.wordmanager3ver.datavalues.UserInfo;
import com.wons.wordmanager3ver.fragmentinfo.adapter.InfoViewModel;
import com.wons.wordmanager3ver.fragmentinfo.adapter.MyWayAdapter;

import java.util.ArrayList;

public class InfoFragment extends Fragment {

    private FragmentInfoBinding binding;
    private InfoViewModel viewModel;
    private UserInfo nowUserInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        viewModel = new InfoViewModel();
        nowUserInfo = viewModel.getNowUserInfo();
        setWayList();
        setLanguageSpinner();
        setExp();
        setStartDate();
        setTitleLanguage();
        setUserLevel();
        setCountWordList();
        setCountWord();
        setUserLevel();
        setUserGrade();
        return binding.getRoot();
    }

    private void setUserGrade() {
        int grade = viewModel.getUserGrade(nowUserInfo.getLanguageCode());
        if(grade == -1) {
            binding.tvUserGrade.setText(String.valueOf("데이터 없음"));
        } else {
            binding.tvUserGrade.setText(String.valueOf(grade));
        }
    }

    private void setCountWord() {
        binding.tvCountWord.setText(String.valueOf(viewModel.getWordQuantity(nowUserInfo.getLanguageCode())));
    }

    private void setCountWordList() {
        binding.tvCountWordList.setText(String.valueOf(viewModel.getWordListQuantity(nowUserInfo.getLanguageCode())));
    }

    private void setUserLevel() {
        binding.tvUserLevel.setText(String.valueOf(nowUserInfo.getLv()));
    }

    private void setTitleLanguage() {
        int languageCode = MainViewModel.getUserInfo().getLanguageCode();
        EnumLanguage[] enumLanguages = EnumLanguage.values();
        for(EnumLanguage enumLanguage : enumLanguages) {
            if(enumLanguage.languageCodeInt == languageCode) {
                binding.tvLanguage.setText(enumLanguage.getLanguage());
            }
        }
    }

    private void setStartDate() {
        binding.tvStartDate.setText(nowUserInfo.getStartDay());
    }

    private void setExp() {
        binding.progressExp.setProgress(nowUserInfo.getExpInt());
    }

    private void setLanguageSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.language, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerLanguage.setAdapter(adapter);
        int language = MainViewModel.getUserInfo().getLanguageCode();
        binding.spinnerLanguage.setSelection(language);
        binding.spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                int languageCode = i;
                EnumLanguage[] enumLanguages = EnumLanguage.values();
                for(EnumLanguage enumLanguage : enumLanguages) {
                    if(enumLanguage.languageCodeInt == languageCode) {
                        MainViewModel.changeLanguageSetting(enumLanguage.languageCodeInt);
                        setTitleLanguage();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setWayList() {
        if (binding.lvMyWay.getAdapter() == null) {
            binding.lvMyWay.setAdapter(new MyWayAdapter());
        }
        ((MyWayAdapter) binding.lvMyWay.getAdapter()).setUserData(viewModel.getFlagUserData(nowUserInfo.getLanguageCode()));
        ((MyWayAdapter) binding.lvMyWay.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        this.viewModel = null;
        super.onDestroy();
    }
}