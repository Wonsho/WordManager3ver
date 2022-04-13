package com.wons.wordmanager3ver.fragmenthome;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
import com.wons.wordmanager3ver.datavalues.WordList;
import com.wons.wordmanager3ver.fragmenthome.adapter.GameListAdapter;
import com.wons.wordmanager3ver.fragmenthome.adapter.TodayListAdapter;
import com.wons.wordmanager3ver.fragmenthome.dialogutils.CallBackInHomeFragment;
import com.wons.wordmanager3ver.fragmenthome.dialogutils.DialogUtilsInHomeFragment;
import com.wons.wordmanager3ver.fragmenthome.getlist.ChoiceListActivity;
import com.wons.wordmanager3ver.fragmenthome.value.EnumGame;
import com.wons.wordmanager3ver.fragmenthome.value.GameValue;
import com.wons.wordmanager3ver.game.HangManActivity;
import com.wons.wordmanager3ver.game.makeword.MakeWordGameActivity;
import com.wons.wordmanager3ver.studyword.StudyActivity;
import com.wons.wordmanager3ver.testword.TestActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeFragmentViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(HomeFragmentViewModel.class);
        onC();
        setGameList();
        setUserInfo();
        setTodayWordList();
        return binding.getRoot();
    }


    private void onC() {

        binding.btnSetting.setOnClickListener(v -> {
            if(MainViewModel.getAllWordListByLanguageCode(MainViewModel.getUserInfo().getLanguageCode()).size() == 0) {
                Toast.makeText(getActivity(), "저장된 단어장이 없습니다", Toast.LENGTH_LONG).show();
                return;
            }
            AlertDialog alertDialog = new DialogUtilsInHomeFragment().getDialogForTodayWordList(this.getContext(),
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

        binding.btnStudy.setOnClickListener(v -> {
            if(binding.lvList.getAdapter().getCount() == 0) {
                Toast.makeText(getActivity(), "지정된 오늘의 단어장이 없습니다", Toast.LENGTH_SHORT).show();
                return;
            }

            ArrayList<TodayWordList> todayWordLists = ((TodayListAdapter)binding.lvList.getAdapter()).getTodayWordLists();
            for(TodayWordList todayWordList : todayWordLists) {
                if(viewModel.getWordCountOfTodayWordList(todayWordList) == 0) {
                    Toast.makeText(getActivity(), "단어가 없는 단어장이 있습니다", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            startActivity(new Intent(getActivity(), StudyActivity.class));
        });

        binding.btnTest.setOnClickListener(v -> {
            boolean check = true;
            if(binding.lvList.getAdapter().getCount() == 0) {
                Toast.makeText(getActivity(), "지정된 오늘의 단어장이 없습니다", Toast.LENGTH_SHORT).show();
                return;
            }

            ArrayList<TodayWordList> todayWordLists = ((TodayListAdapter)binding.lvList.getAdapter()).getTodayWordLists();
            for(TodayWordList todayWordList : todayWordLists) {
                check = todayWordList.passOrNo;
                if(viewModel.getWordCountOfTodayWordList(todayWordList) == 0) {
                    Toast.makeText(getActivity(), "단어가 없는 단어장이 있습니다", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if(check) {
                Toast.makeText(getActivity(), "모든 시험을 합격하셨습니다\n새로고침 해주세요", Toast.LENGTH_SHORT).show();
                return;
            }

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
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("알림");
                builder.setMessage("완료되지 못한 단어장이 존재합니다\n그래도 새로고침 하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ArrayList<TodayWordList> todayWordLists = viewModel.getTodayWordList(MainViewModel.getUserInfo().getLanguageCode());
                        for(TodayWordList list : todayWordLists) {
                            viewModel.deleteTodayList(list);
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

        binding.lvGame3.setOnItemClickListener((adapterView, view, i, l) -> {
            GameValue gameValue = (GameValue) ((GameListAdapter) binding.lvGame3.getAdapter()).getItem(i);

            if(gameValue.gameCode == EnumGame.HANGMAN_GAME.gameCodeInt) {
                startActivity(new Intent(getActivity(), HangManActivity.class));
            }

            if(gameValue.gameCode == EnumGame.MAKE_WORD_BY_SPELLING.gameCodeInt) {
                startActivity(new Intent(getActivity(), MakeWordGameActivity.class));
            }

        });
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
            Toast.makeText(getActivity(), "새로고침 되었습니다", Toast.LENGTH_SHORT).show();
        }
        if(recommendValue == UserRecommendWordListSettingValue.USER_RECOMMEND_STYLE_CHOICE.recommendStyleCodeInt) {
            Intent intent = new Intent(getActivity(), ChoiceListActivity.class);
            intent.putExtra("maxCount", viewModel.getSetting(EnumSetting.USER_RECOMMEND_TODAY_LIST_COUNT.settingCodeId).settingValue);
            intent.putExtra("languageCode", MainViewModel.getUserInfo().getLanguageCode());
            startActivity(intent);
        }
    }

    private void setTodayWordList() {
        if (binding.lvList.getAdapter() == null) {
            binding.lvList.setAdapter(new TodayListAdapter());
        }
        ArrayList<TodayWordList> todayWordLists = new ArrayList<>();
        HashMap<Integer, WordList> wordList = new HashMap<>();
        try {
            todayWordLists = viewModel.getTodayWordList(MainViewModel.getUserInfo().getLanguageCode());
            wordList = viewModel.getTodayWordList(todayWordLists);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "변경사항이 있어\n 오늘의 단어장이 초기화 되었습니다", Toast.LENGTH_SHORT).show();
            ArrayList<TodayWordList> todayWordLists1 = viewModel.getTodayWordList(MainViewModel.getUserInfo().getLanguageCode());
            for(TodayWordList todayWordList : todayWordLists1) {
                viewModel.deleteTodayList(todayWordList);
            }
            viewModel.setRecommendSettingReset();
        }

        ((TodayListAdapter)binding.lvList.getAdapter()).setTodayWordLists(todayWordLists, wordList);
        ((TodayListAdapter)binding.lvList.getAdapter()).notifyDataSetChanged();

       if(MainViewModel.getAllWordListByLanguageCode(MainViewModel.getUserInfo().getLanguageCode()).size() == 0) {
           binding.tv1.setText("아직 만든 단어장이 없습니다.\n단어장을 먼저 만들어 주세요.");
           setVisible(true);
           binding.tv1.setVisibility(View.VISIBLE);
           binding.btnAddList.setVisibility(View.VISIBLE);
       } else if(binding.lvList.getAdapter().getCount() == 0) {
           binding.tv1.setVisibility(View.VISIBLE);
           binding.tv1.setText("오늘의 단어장이 비어있습니다\n새로고침을 해주세요");
           setVisible(true);
           binding.btnAddList.setVisibility(View.GONE);
       } else {
           setVisible(false);
           binding.tv1.setVisibility(View.GONE);
           binding.btnAddList.setVisibility(View.GONE);
       }
    }

    private void setVisible(boolean check) {
        if(check) {
            binding.card1.setVisibility(View.GONE);
            binding.card2.setVisibility(View.GONE);
        } else {
            binding.card1.setVisibility(View.VISIBLE);
            binding.card2.setVisibility(View.VISIBLE);
        }
    }


    private void setGameList() {
        if (binding.lvGame3.getAdapter() == null) {
            binding.lvGame3.setAdapter(new GameListAdapter());
        }
        ArrayList<GameValue> gameValues = new ArrayList<>();
        gameValues.add(new GameValue(EnumGame.HANGMAN_GAME));
        gameValues.add(new GameValue(EnumGame.MAKE_WORD_BY_SPELLING));
        gameValues.add(new GameValue(EnumGame.OX_QUIZ));
        gameValues.add(new GameValue(EnumGame.PUT_SPELL_AT_BLANK));
        gameValues.add(new GameValue(EnumGame.WRITE_WORD));
        gameValues.add(new GameValue(EnumGame.FOUR_CARD));

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
        setUserInfo();
    }
}