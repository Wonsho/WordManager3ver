package com.wons.wordmanager3ver;

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

        binding.back.setOnClickListener(v -> {
            MY.doIt(getApplicationContext());
        });

        binding.btnReplace.setOnClickListener(v -> {
            if(MainViewModel.getAllWordListByLanguageCode(MainViewModel.getUserInfo().getLanguageCode()).size() == 0) {
                Toast.makeText(getApplicationContext(), "만들어진 단어장이 없습니다", Toast.LENGTH_LONG).show();
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
                builder.setTitle("알림");
                builder.setMessage("완료되지 못한 단어장이 존재합니다\n그래도 다시 고르시겠습니까?");
                builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
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
                builder.setPositiveButton("아니요", new DialogInterface.OnClickListener() {
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
            Toast.makeText(getApplicationContext(), "       변경사항이 있어\n " +
                                                        "오늘의 단어장이 초기화 되었습니다", Toast.LENGTH_SHORT).show();
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
            binding.tv1.setText("아직 만든 단어장이 없습니다. \n하단 메뉴를 눌러 단어장을 먼저 만들어 주세요.");
            binding.tvWordlist.setText("단어장 만들기");
            binding.tv1.setVisibility(View.VISIBLE);
            binding.layAddTodayList.setVisibility(View.GONE);
            binding.cardStudy.setVisibility(View.GONE);

        } else if(binding.lvList.getAdapter().getCount() == 0) {
            binding.tv1.setVisibility(View.GONE);
            binding.layAddTodayList.setVisibility(View.VISIBLE);
            binding.cardStudy.setVisibility(View.GONE);

            binding.tvWordlist.setText("단어장");
            binding.tv1.setText("공부할 단어장 리스트가 비어있습니다\n"+
                                "   상단의 새로고침을 눌러주세요");
        } else {
            binding.tv1.setVisibility(View.GONE);
            binding.layAddTodayList.setVisibility(View.GONE);
            binding.cardStudy.setVisibility(View.VISIBLE);
            binding.tvWordlist.setText("단어장");

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
                    .setTitle("알림")
                    .setMessage("사용한지 5일이 되었습니다")
                    .setPositiveButton("리뷰작성하기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //todo 리뷰
                        }
                    })
                    .setNegativeButton("닫기",null)
                    .setCancelable(false);
            builder.show();
        }

    }

    public boolean isTodayListCheck() {

        if(binding.tv1.getVisibility() == View.VISIBLE || binding.layAddTodayList.getVisibility() == View.VISIBLE) {
            Toast.makeText(getApplicationContext(), "공부할 단어장이 지정되지 않았습니다", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }


}