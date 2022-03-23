package com.wons.wordmanager3ver.fragmenthome;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wons.wordmanager3ver.MainActivity;
import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.databinding.FragmentHomeBinding;
import com.wons.wordmanager3ver.datavalues.EnumLanguage;
import com.wons.wordmanager3ver.datavalues.EnumSetting;
import com.wons.wordmanager3ver.datavalues.Setting;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.datavalues.UserInfo;
import com.wons.wordmanager3ver.datavalues.UserRecommendWordListSettingValue;
import com.wons.wordmanager3ver.fragmenthome.adapter.GameListAdapter;
import com.wons.wordmanager3ver.fragmenthome.adapter.TodayListAdapter;
import com.wons.wordmanager3ver.fragmenthome.dialogutils.CallBackInHomeFragment;
import com.wons.wordmanager3ver.fragmenthome.dialogutils.DialogUtilsInHomeFragment;
import com.wons.wordmanager3ver.fragmenthome.getlist.ChoiceListActivity;
import com.wons.wordmanager3ver.fragmenthome.value.EnumGame;
import com.wons.wordmanager3ver.fragmenthome.value.GameValue;
import com.wons.wordmanager3ver.studyword.StudyActivity;
import com.wons.wordmanager3ver.testword.TestActivity;

import java.util.ArrayList;
import java.util.HashSet;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        onC();
        setGameList();
        setUserInfo();
        setTodayWordList();
        return binding.getRoot();
    }


    private void onC() {

        binding.btnSetting.setOnClickListener(v -> {
            AlertDialog alertDialog = new DialogUtilsInHomeFragment().getDialogForTodayWordList(this.getContext(),
                    MainViewModel.getSetting(EnumSetting.USER_RECOMMEND_TODAY_LIST_COUNT.settingCodeId).settingValue,
                    MainViewModel.getSetting(EnumSetting.USER_RECOMMEND_STYLE.settingCodeId).settingValue, new CallBackInHomeFragment() {
                @Override
                public void callback(int setting, int listCount) {
                    MainViewModel.updateSetting(EnumSetting.USER_RECOMMEND_STYLE.settingCodeId, setting);
                    MainViewModel.updateSetting(EnumSetting.USER_RECOMMEND_TODAY_LIST_COUNT.settingCodeId, listCount);
                }
            });
            alertDialog.show();
        });

        binding.btnStudy.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), StudyActivity.class));
        });

        binding.btnTest.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), TestActivity.class));
        });

        binding.btnAddList.setOnClickListener(v -> {
            ((MainActivity) getActivity()).setFragmentAddWord();
        });

        binding.btnReplace.setOnClickListener(v -> {
            if(MainViewModel.getAllWordListByLanguageCode(MainViewModel.getUserInfo().getLanguageCode()).size() == 0) {
                Toast.makeText(getActivity(), "저장된 단어장이 없습니다", Toast.LENGTH_LONG).show();
                return;
            }
            if(MainViewModel.getTodayWordList(MainViewModel.getUserInfo().getLanguageCode()).size() != 0) {
                ArrayList<TodayWordList> todayWordLists = MainViewModel.getTodayWordList(MainViewModel.getUserInfo().getLanguageCode());
                for(TodayWordList list : todayWordLists) {
                    if(!list.passOrNo) {
                        break;
                    }  else {
                        ArrayList<TodayWordList> todayWordLists1 = MainViewModel.getTodayWordList(MainViewModel.getUserInfo().getLanguageCode());
                        for(TodayWordList list1 : todayWordLists1) {
                            MainViewModel.deleteTodayList(list1);
                        }
                        changeTodayWordList();
                        return;
                    }
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("알림");
                builder.setMessage("완료되지 못한 단어장이 존재합니다\n그래도 새로고침 하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ArrayList<TodayWordList> todayWordLists = MainViewModel.getTodayWordList(MainViewModel.getUserInfo().getLanguageCode());
                        for(TodayWordList list : todayWordLists) {
                            MainViewModel.deleteTodayList(list);
                        }
                        changeTodayWordList();
                    }
                });
                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create().show();
            } else {
                changeTodayWordList();
            }
        });
    }

    private void changeTodayWordList() {
        int recommendValue = MainViewModel.getSetting(EnumSetting.USER_RECOMMEND_STYLE.settingCodeId).settingValue;

        if(recommendValue == UserRecommendWordListSettingValue.USER_RECOMMEND_STYLE_RECOMMEND.recommendStyleCodeInt) {

            ArrayList<Integer> integers = new ArrayList<>();

            while(true) {
                if(integers.size() == MainViewModel.getSetting(EnumSetting.USER_RECOMMEND_TODAY_LIST_COUNT.settingCodeId).settingValue) {
                    break;
                }

                int randomNum = (int)(Math.random()*MainViewModel.getAllWordListByLanguageCode(MainViewModel.getUserInfo().getLanguageCode()).size());

                if(!integers.contains(randomNum)) {
                    integers.add(randomNum);
                }

            }
            ArrayList<TodayWordList> todayWordLists = MainViewModel.getRandomTodayWordList(integers);
            for(TodayWordList todayWordList : todayWordLists) {
                MainViewModel.insertTodayWordList(todayWordList);
            }
            setTodayWordList();
            Toast.makeText(getActivity(), "새로고침 되었습니다", Toast.LENGTH_SHORT).show();
        }
        if(recommendValue == UserRecommendWordListSettingValue.USER_RECOMMEND_STYLE_CHOICE.recommendStyleCodeInt) {
            Intent intent = new Intent(getActivity(), ChoiceListActivity.class);
            intent.putExtra("maxCount", MainViewModel.getSetting(EnumSetting.USER_RECOMMEND_TODAY_LIST_COUNT.settingCodeId).settingValue);
            intent.putExtra("languageCode", MainViewModel.getUserInfo().getLanguageCode());
            startActivity(intent);
        }
    }

    private void setTodayWordList() {
        if (binding.lvList.getAdapter() == null) {
            binding.lvList.setAdapter(new TodayListAdapter());
        }
        ArrayList<TodayWordList> todayWordLists = MainViewModel.getTodayWordList(MainViewModel.getUserInfo().getLanguageCode());
        ((TodayListAdapter)binding.lvList.getAdapter()).setTodayWordLists(todayWordLists);
        ((TodayListAdapter)binding.lvList.getAdapter()).notifyDataSetChanged();

        //todo 단어장이 없을경우 안내문 띄우고
        // 단어장이 있으나 오늘의 단어장을 초기화 하지 않을 경우 안내문 띄움
        // 둘다 아니면 뷰를 없엠*/
       if(MainViewModel.getAllWordListByLanguageCode(MainViewModel.getUserInfo().getLanguageCode()).size() == 0) {
           binding.tv1.setText("아직 만든 단어장이 없습니다.\n단어장을 먼저 만들어 주세요.");
           binding.tv1.setVisibility(View.VISIBLE);
           binding.btnAddList.setVisibility(View.VISIBLE);
       } else if(binding.lvList.getAdapter().getCount() == 0) {
           binding.tv1.setVisibility(View.VISIBLE);
           binding.tv1.setText("오늘의 단어장이 비어있습니다\n새로고침을 해주세요");
           binding.btnAddList.setVisibility(View.GONE);
       } else {
           binding.tv1.setVisibility(View.GONE);
           binding.btnAddList.setVisibility(View.GONE);
       }
    }


    private void setGameList() {
        if (binding.lvGame3.getAdapter() == null) {
            binding.lvGame3.setAdapter(new GameListAdapter());
        }
        ArrayList<GameValue> gameValues = new ArrayList<>();
        gameValues.add(new GameValue(EnumGame.HANGMAN_GAME));
        gameValues.add(new GameValue(EnumGame.HANGMAN_GAME));
        gameValues.add(new GameValue(EnumGame.HANGMAN_GAME));
        gameValues.add(new GameValue(EnumGame.HANGMAN_GAME));
        gameValues.add(new GameValue(EnumGame.HANGMAN_GAME));
        gameValues.add(new GameValue(EnumGame.HANGMAN_GAME));
        gameValues.add(new GameValue(EnumGame.HANGMAN_GAME));
        gameValues.add(new GameValue(EnumGame.HANGMAN_GAME));
        gameValues.add(new GameValue(EnumGame.HANGMAN_GAME));
        gameValues.add(new GameValue(EnumGame.HANGMAN_GAME));
        ((GameListAdapter) binding.lvGame3.getAdapter()).setData(gameValues);
        ((GameListAdapter) binding.lvGame3.getAdapter()).notifyDataSetChanged();
    }


    private void setUserInfo() {

        UserInfo userInfo = MainViewModel.getUserInfo();
        int languageCode = userInfo.getLanguageCode();
        int exp = userInfo.getExpInt();
        int userLevel = userInfo.getLv();

        EnumLanguage[] enumLanguages = EnumLanguage.values();
        for (EnumLanguage enumLanguage : enumLanguages) {
            if (enumLanguage.languageCodeInt == languageCode) {
                binding.tvLanguage.setText(enumLanguage.getLanguage());
            }
        }
        binding.progressBar.setProgress(exp);
        binding.tvLvValue.setText(String.valueOf(userLevel));
    }

    @Override
    public void onStart() {
        super.onStart();
        setTodayWordList();
    }
}