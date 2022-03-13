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
import com.wons.wordmanager3ver.fragmentinfo.adapter.MyWayAdapter;

import java.util.ArrayList;

public class InfoFragment extends Fragment {

    private FragmentInfoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);
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
        int userGrade = 1; // 총 단어의 합격 평균
        binding.tvUserGrade.setText(EnumGrade.A.getGradeToString(userGrade));

    }

    private void setCountWord() {
        int countWord = 0;
        binding.tvCountWord.setText(String.valueOf(countWord));
    }

    private void setCountWordList() {
        int countWordList = 0;
        binding.tvCountWordList.setText(String.valueOf(countWordList));
    }

    private void setUserLevel() {
        String userLevel = "1";
        binding.tvUserLevel.setText(userLevel);
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
        String startDate = "2022-03-10";
        binding.tvStartDate.setText(startDate);
    }

    private void setExp() {
        int exp = 100;
        binding.progressExp.setProgress(100);
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
        ArrayList<FlagUserLevelData> arrayList = new ArrayList<>();
        arrayList.add(new FlagUserLevelData(0, "2022-03-10", 1));
        arrayList.add(new FlagUserLevelData(0, "2022-03-11", 2));
        arrayList.add(new FlagUserLevelData(0, "2022-03-12", 3));
        arrayList.add(new FlagUserLevelData(0, "2022-03-13", 4));
        arrayList.add(new FlagUserLevelData(0, "2022-03-14", 5));
        arrayList.add(new FlagUserLevelData(0, "2022-03-15", 6));
        arrayList.add(new FlagUserLevelData(0, "2022-03-16", 7));
        ((MyWayAdapter) binding.lvMyWay.getAdapter()).setUserData(arrayList);
        ((MyWayAdapter) binding.lvMyWay.getAdapter()).notifyDataSetChanged();
    }

}