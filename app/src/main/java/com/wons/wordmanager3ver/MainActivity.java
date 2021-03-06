package com.wons.wordmanager3ver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;
import com.wons.wordmanager3ver.databinding.ActivityMainBinding;
import com.wons.wordmanager3ver.datavalues.MY;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.datavalues.UserInfo;
import com.wons.wordmanager3ver.datavalues.WordList;
import com.wons.wordmanager3ver.mainutils.adapter.TodayListAdapter;
import com.wons.wordmanager3ver.mainutils.getlist.ChoiceListActivity;
import com.wons.wordmanager3ver.gamefragment.GameFragment;
import com.wons.wordmanager3ver.infoactivity.InfoActivity;
import com.wons.wordmanager3ver.studyfragment.StudyFragment;
import com.wons.wordmanager3ver.testfragment.TestFragment;
import com.wons.wordmanager3ver.addwordactivity.WordListActivity;
import com.wons.wordmanager3ver.todayword.TodayWordActivity;

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
        isCheckUsedCount();
        getSupportFragmentManager().beginTransaction().replace(binding.frame.getId(), new GameFragment()).commit();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });

        binding.menu.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String s = String.valueOf(tab.getText());
                switch (s) {
                    case "????????????": {
                        tab.setText("??????");
                        getSupportFragmentManager().beginTransaction().replace(binding.frame.getId(), new TestFragment()).commit();
                        break;
                    }

                    case "????????????": {
                        tab.setText("??????");
                        getSupportFragmentManager().beginTransaction().replace(binding.frame.getId(), new StudyFragment()).commit();
                        break;
                    }

                    case "????????????" : {
                        tab.setText("??????");
                        getSupportFragmentManager().beginTransaction().replace(binding.frame.getId(), new GameFragment()).commit();
                        break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                String s = String.valueOf(tab.getText());

                switch (s) {
                    case "??????": {
                        tab.setText("????????????");

                        break;
                    }

                    case "??????": {
                        tab.setText("????????????");

                        break;
                    }

                    case "??????" : {
                        tab.setText("????????????");

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

        binding.back.setOnClickListener(v -> {
            MY.doIt(getApplicationContext());
        });

        binding.btnReplace.setOnClickListener(v -> {
            if(MainViewModel.getAllWordListByLanguageCode(MainViewModel.getUserInfo().getLanguageCode()).size() == 0) {
                Toast.makeText(getApplicationContext(), "???????????? ???????????? ????????????", Toast.LENGTH_LONG).show();
                return;
            }
            if(viewModel.getTodayWordList(MainViewModel.getUserInfo().getLanguageCode()).size() != 0) {
                ArrayList<TodayWordList> todayWordLists = viewModel.getTodayWordList(MainViewModel.getUserInfo().getLanguageCode());
                boolean check = true;

                for(TodayWordList list : todayWordLists) {
                    if(!list.passOrNo) {
                        check = false;
                        break;
                    }
                }

                if(check) {
                    viewModel.setWordInfoTestResultToReset();
                    ArrayList<TodayWordList> todayWordLists1 = viewModel.getTodayWordList(MainViewModel.getUserInfo().getLanguageCode());
                    for(TodayWordList list1 : todayWordLists1) {
                        viewModel.deleteTodayList(list1);
                    }
                    changeTodayWordList();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("??????");
                builder.setMessage("???????????? ?????? ???????????? ???????????????\n????????? ?????? ??????????????????????");
                builder.setNegativeButton("???", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        viewModel.setWordInfoTestResultToReset();
                        ArrayList<TodayWordList> todayWordLists = viewModel.getTodayWordList(MainViewModel.getUserInfo().getLanguageCode());
                        for(TodayWordList list : todayWordLists) {
                            viewModel.deleteTodayList(list);
                        }
                        changeTodayWordList();
                    }
                });
                builder.setPositiveButton("?????????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create().show();
            } else {
                viewModel.setWordInfoTestResultToReset();
                changeTodayWordList();
            }
        });


        binding.btnAddList.setOnClickListener(v -> {
            startActivity(new Intent(this, WordListActivity.class));
        });
        binding.btnUserInfo.setOnClickListener(v -> {
            startActivity(new Intent(this, InfoActivity.class));
        });
        binding.layAddTodayList.setOnClickListener(v -> {
            changeTodayWordList();
        });
        binding.lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, TodayWordActivity.class);
                TodayWordList todayWordList = ((TodayListAdapter)binding.lvList.getAdapter()).getItem(i);
                intent.putExtra("listCode", todayWordList.getListCode());
                startActivity(intent);
            }
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

    @SuppressLint("SetTextI18n")
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
            viewModel.setWordInfoTestResultToReset(-1);
            Toast.makeText(getApplicationContext(), "       ??????????????? ??????\n " +
                                                        "????????? ???????????? ????????? ???????????????", Toast.LENGTH_SHORT).show();
            ArrayList<TodayWordList> todayWordLists1 = viewModel.getTodayWordList(MainViewModel.getUserInfo().getLanguageCode());
            for(TodayWordList todayWordList : todayWordLists1) {
                viewModel.deleteTodayList(todayWordList);
            }
            todayWordLists = new ArrayList<>();
            wordList = new HashMap<>();
        }

        ((TodayListAdapter)binding.lvList.getAdapter()).setTodayWordLists(todayWordLists, wordList);
        ((TodayListAdapter)binding.lvList.getAdapter()).notifyDataSetChanged();

        if(MainViewModel.getAllWordListByLanguageCode(MainViewModel.getUserInfo().getLanguageCode()).size() == 0) {
            binding.tv1.setText("?????? ?????? ???????????? ????????????. \n?????? ????????? ?????? ???????????? ?????? ????????? ?????????.");
            binding.tvWordlist.setText("????????? ?????????");
            binding.tv1.setVisibility(View.VISIBLE);
            binding.layAddTodayList.setVisibility(View.GONE);
            binding.cardStudy.setVisibility(View.GONE);

        } else if(binding.lvList.getAdapter().getCount() == 0) {
            binding.tv1.setVisibility(View.GONE);
            binding.layAddTodayList.setVisibility(View.VISIBLE);
            binding.cardStudy.setVisibility(View.GONE);

            binding.tvWordlist.setText("?????????");
            binding.tv1.setText("????????? ????????? ???????????? ??????????????????\n"+
                                "   ????????? ??????????????? ???????????????");
        } else {
            binding.tv1.setVisibility(View.GONE);
            binding.layAddTodayList.setVisibility(View.GONE);
            binding.cardStudy.setVisibility(View.VISIBLE);
            binding.tvWordlist.setText("?????????");

        }
    }

    private void changeTodayWordList() {
            Intent intent = new Intent(getApplicationContext(), ChoiceListActivity.class);
            intent.putExtra("languageCode", MainViewModel.getUserInfo().getLanguageCode());
            startActivity(intent);
    }


    private void isCheckUsedCount() {

        if(viewModel.checkUsedCount()) {
            //todo show dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("??????")
                    .setMessage("???????????? 5?????? ???????????????")
                    .setPositiveButton("??????????????????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //todo ??????
                        }
                    })
                    .setNegativeButton("??????",null)
                    .setCancelable(false);
            builder.show();
        }

    }

    public boolean isTodayListCheck() {

        if(binding.tv1.getVisibility() == View.VISIBLE || binding.layAddTodayList.getVisibility() == View.VISIBLE) {
            Toast.makeText(getApplicationContext(), "????????? ???????????? ???????????? ???????????????", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }


}