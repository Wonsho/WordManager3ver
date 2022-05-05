package com.wons.wordmanager3ver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.wons.wordmanager3ver.databinding.ActivityMainBinding;
import com.wons.wordmanager3ver.datavalues.EnumSetting;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.datavalues.UserInfo;
import com.wons.wordmanager3ver.datavalues.UserRecommendWordListSettingValue;
import com.wons.wordmanager3ver.datavalues.WordList;
import com.wons.wordmanager3ver.mainutils.adapter.TodayListAdapter;
import com.wons.wordmanager3ver.mainutils.dialogutils.CallBackInHomeFragment;
import com.wons.wordmanager3ver.mainutils.dialogutils.DialogUtilsInHomeFragment;
import com.wons.wordmanager3ver.mainutils.getlist.ChoiceListActivity;
import com.wons.wordmanager3ver.gamefragment.GameFragment;
import com.wons.wordmanager3ver.infoactivity.InfoActivity;
import com.wons.wordmanager3ver.studyfragment.StudyFragment;
import com.wons.wordmanager3ver.testfragment.TestFragment;
import com.wons.wordmanager3ver.addwordactivity.WordListActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.buildDataBase(getApplicationContext());
        setUserInfo();
        onClick();
        setTodayWordList();
        getSupportFragmentManager().beginTransaction().replace(binding.frame.getId(), new GameFragment()).commit();
        binding.menu.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String s = String.valueOf(tab.getText());
                switch (s) {
                    case "시험보기": {
                        tab.setText("시험");
                        getSupportFragmentManager().beginTransaction().replace(binding.frame.getId(), new TestFragment()).commit();
                        break;
                    }

                    case "공부하기": {
                        tab.setText("공부");
                        getSupportFragmentManager().beginTransaction().replace(binding.frame.getId(), new StudyFragment()).commit();
                        break;
                    }

                    case "게임하기" : {
                        tab.setText("게임");
                        getSupportFragmentManager().beginTransaction().replace(binding.frame.getId(), new GameFragment()).commit();
                        break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                String s = String.valueOf(tab.getText());

                switch (s) {
                    case "시험": {
                        tab.setText("시험보기");

                        break;
                    }

                    case "공부": {
                        tab.setText("공부하기");

                        break;
                    }

                    case "게임" : {
                        tab.setText("게임하기");

                        break;
                    }
               }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void onClick() {

        binding.btnSetting.setOnClickListener(v -> {
            if(MainViewModel.getAllWordListByLanguageCode(MainViewModel.getUserInfo().getLanguageCode()).size() == 0) {
                Toast.makeText(getApplicationContext(), "저장된 단어장이 없습니다", Toast.LENGTH_LONG).show();
                return;
            }
            AlertDialog alertDialog = new DialogUtilsInHomeFragment().getDialogForTodayWordList(MainActivity.this,
                    viewModel.getSetting(EnumSetting.USER_RECOMMEND_TODAY_LIST_COUNT.settingCodeId).settingValue,
                    viewModel.getSetting(EnumSetting.USER_RECOMMEND_STYLE.settingCodeId).settingValue, new CallBackInHomeFragment() {
                        @Override
                        public void callback(int setting, int listCount) {
                            viewModel.updateSetting(EnumSetting.USER_RECOMMEND_STYLE.settingCodeId, setting);
                            viewModel.updateSetting(EnumSetting.USER_RECOMMEND_TODAY_LIST_COUNT.settingCodeId, listCount);
                        }
                    });
            alertDialog.show();
        });

        binding.btnReplace.setOnClickListener(v -> {
            if(MainViewModel.getAllWordListByLanguageCode(MainViewModel.getUserInfo().getLanguageCode()).size() == 0) {
                Toast.makeText(getApplicationContext(), "저장된 단어장이 없습니다", Toast.LENGTH_LONG).show();
                return;
            }
            if(viewModel.getTodayWordList(MainViewModel.getUserInfo().getLanguageCode()).size() != 0) {
                ArrayList<TodayWordList> todayWordLists = viewModel.getTodayWordList(MainViewModel.getUserInfo().getLanguageCode());
                for(TodayWordList list : todayWordLists) {
                    if(!list.passOrNo) {
                        break;
                    }  else {
                        ArrayList<TodayWordList> todayWordLists1 = viewModel.getTodayWordList(MainViewModel.getUserInfo().getLanguageCode());
                        for(TodayWordList list1 : todayWordLists1) {
                            viewModel.deleteTodayList(list1);
                        }
                        changeTodayWordList();
                        return;
                    }
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("알림");
                builder.setMessage("완료되지 못한 단어장이 존재합니다\n그래도 새로고침 하시겠습니까?");
                builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ArrayList<TodayWordList> todayWordLists = viewModel.getTodayWordList(MainViewModel.getUserInfo().getLanguageCode());
                        for(TodayWordList list : todayWordLists) {
                            viewModel.deleteTodayList(list);
                        }
                        changeTodayWordList();
                    }
                });
                builder.setPositiveButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create().show();
            } else {
                changeTodayWordList();
            }
        });


        binding.btnAddList.setOnClickListener(v -> {
            startActivity(new Intent(this, WordListActivity.class));
        });
        binding.btnUserInfo.setOnClickListener(v -> {
            startActivity(new Intent(this, InfoActivity.class));
        });
    }


    private void setUserInfo() {
        UserInfo userInfo = MainViewModel.getUserInfo();
        int level = userInfo.getLv();
        int exp = userInfo.getExpInt();
        binding.progressCircular.setProgress(exp);
        binding.tvUserLevel.setText(String.valueOf(level));
    }

    @Override
    public void onStart() {
        super.onStart();
        setUserInfo();
        setTodayWordList();
    }

    private void setTodayWordList() {
        if (binding.lvList.getAdapter() == null) {
            binding.lvList.setAdapter(new TodayListAdapter());
        }
        ArrayList<TodayWordList> todayWordLists = new ArrayList<>();
        HashMap<Integer, WordList> wordList = new HashMap<>();
        try {
            Log.e("set", "pass");
            todayWordLists = viewModel.getTodayWordList(MainViewModel.getUserInfo().getLanguageCode());
            wordList = viewModel.getTodayWordList(todayWordLists);

        } catch (Exception e) {
            Log.e("set C", "pass");
            Toast.makeText(getApplicationContext(), "변경사항이 있어\n 오늘의 단어장이 초기화 되었습니다", Toast.LENGTH_SHORT).show();
            viewModel.updateSetting(EnumSetting.USER_RECOMMEND_TODAY_LIST_COUNT.settingCodeId,1);
            ArrayList<TodayWordList> todayWordLists1 = viewModel.getTodayWordList(MainViewModel.getUserInfo().getLanguageCode());
            for(TodayWordList todayWordList : todayWordLists1) {
                viewModel.deleteTodayList(todayWordList);
            }
            viewModel.setRecommendSettingReset();
            todayWordLists = new ArrayList<>();
            wordList = new HashMap<>();
        }

        ((TodayListAdapter)binding.lvList.getAdapter()).setTodayWordLists(todayWordLists, wordList);
        ((TodayListAdapter)binding.lvList.getAdapter()).notifyDataSetChanged();

        if(MainViewModel.getAllWordListByLanguageCode(MainViewModel.getUserInfo().getLanguageCode()).size() == 0) {
            binding.tv1.setText("            아직 만든 단어장이 없습니다. \n하단 탭을 눌러 단어장을 먼저 만들어 주세요.");
            setVisible(true);
            binding.tv1.setVisibility(View.VISIBLE);
        } else if(binding.lvList.getAdapter().getCount() == 0) {
            binding.tv1.setVisibility(View.VISIBLE);
            binding.tv1.setText("공부할 단어장 리스트가 비어있습니다\n            새로고침을 해주세요");
            setVisible(true);
        } else {
            setVisible(false);
            binding.tv1.setVisibility(View.GONE);
        }
    }

    private void changeTodayWordList() {
        int recommendValue = viewModel.getSetting(EnumSetting.USER_RECOMMEND_STYLE.settingCodeId).settingValue;

        if(recommendValue == UserRecommendWordListSettingValue.USER_RECOMMEND_STYLE_RECOMMEND.recommendStyleCodeInt) {

            ArrayList<Integer> integers = new ArrayList<>();

            while(true) {
                if(integers.size() == viewModel.getSetting(EnumSetting.USER_RECOMMEND_TODAY_LIST_COUNT.settingCodeId).settingValue) {
                    break;
                }

                int randomNum = (int)(Math.random()*MainViewModel.getAllWordListByLanguageCode(MainViewModel.getUserInfo().getLanguageCode()).size());

                if(!integers.contains(randomNum)) {
                    integers.add(randomNum);
                }

            }
            ArrayList<TodayWordList> todayWordLists = viewModel.getRandomTodayWordList(integers);
            for(TodayWordList todayWordList : todayWordLists) {
                viewModel.insertTodayWordList(todayWordList);
            }
            setTodayWordList();
            Toast.makeText(getApplicationContext(), "새로고침 되었습니다", Toast.LENGTH_SHORT).show();
        }
        if(recommendValue == UserRecommendWordListSettingValue.USER_RECOMMEND_STYLE_CHOICE.recommendStyleCodeInt) {
            Intent intent = new Intent(getApplicationContext(), ChoiceListActivity.class);
            intent.putExtra("maxCount", viewModel.getSetting(EnumSetting.USER_RECOMMEND_TODAY_LIST_COUNT.settingCodeId).settingValue);
            intent.putExtra("languageCode", MainViewModel.getUserInfo().getLanguageCode());
            startActivity(intent);
        }
    }


    public void setVisible(boolean check) {
        if(check) {
            binding.cardStudy.setVisibility(View.GONE);
        } else {
            binding.cardStudy.setVisibility(View.VISIBLE);
        }
    }


}