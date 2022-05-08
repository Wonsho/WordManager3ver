package com.wons.wordmanager3ver.infoactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.databinding.ActivityInfoBinding;
import com.wons.wordmanager3ver.datavalues.EnumLanguage;
import com.wons.wordmanager3ver.datavalues.UserInfo;
import com.wons.wordmanager3ver.infoactivity.adapter.MyWayAdapter;

public class InfoActivity extends AppCompatActivity {

    private ActivityInfoBinding binding;
    private InfoViewModel viewModel;
    private UserInfo nowUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(InfoViewModel.class);
        nowUserInfo = viewModel.getNowUserInfo();
        overridePendingTransition(R.anim.right_enter,R.anim.non);
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

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

    }

    private void setUserGrade() {
        int grade = viewModel.getUserGrade(nowUserInfo.getLanguageCode());
        if(grade == -1) {
            binding.tvUserGrade.setText(String.valueOf("데이터 없음"));
        } else {
            binding.tvUserGrade.setText(String.valueOf(grade) + "점");
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
        UserInfo userInfo = viewModel.getNowUserInfo();
        binding.progressExp.setProgress(userInfo.getExpInt());
    }

    private void setLanguageSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.language, android.R.layout.simple_spinner_item);
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
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.non,R.anim.right_exit);
    }



}