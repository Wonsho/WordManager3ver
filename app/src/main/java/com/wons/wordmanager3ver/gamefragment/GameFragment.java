package com.wons.wordmanager3ver.gamefragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wons.wordmanager3ver.databinding.FragmentGameBinding;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.gamefragment.game.fourcard.FourCardActivity;
import com.wons.wordmanager3ver.gamefragment.game.hangman.HangManActivity;
import com.wons.wordmanager3ver.gamefragment.game.makeword.MakeWordGameActivity;
import com.wons.wordmanager3ver.gamefragment.game.oxquiz.QuizActivity;
import com.wons.wordmanager3ver.gamefragment.game.putspellatblankgame.PutSpellAtBlankActivity;

import java.util.ArrayList;

public class GameFragment extends Fragment {

    FragmentGameBinding binding;
    GameFragmentViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(GameFragmentViewModel.class);
        setGameList();
        onC();
        return binding.getRoot();
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
        gameValues.add(new GameValue(EnumGame.FOUR_CARD));

        ((GameListAdapter) binding.lvGame3.getAdapter()).setData(gameValues);
        ((GameListAdapter) binding.lvGame3.getAdapter()).notifyDataSetChanged();
    }

    private void onC() {

        binding.lvGame3.setOnItemClickListener((adapterView, view, i, l) -> {
            GameValue gameValue = (GameValue) ((GameListAdapter) binding.lvGame3.getAdapter()).getItem(i);

            if(gameValue.gameCode == EnumGame.HANGMAN_GAME.gameCodeInt) {
                ArrayList<TodayWordList> todayWordLists = viewModel.getTodayWordLists();
                for(TodayWordList todayWordList : todayWordLists) {
                    if (viewModel.getWordCountOfTodayWordList(todayWordList) == 0) {
                        Toast.makeText(getActivity(), "단어가 없는 단어장이 있습니다", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                startActivity(new Intent(getActivity(), HangManActivity.class));
            }

            if(gameValue.gameCode == EnumGame.MAKE_WORD_BY_SPELLING.gameCodeInt) {
                ArrayList<TodayWordList> todayWordLists = viewModel.getTodayWordLists();
                for(TodayWordList todayWordList : todayWordLists) {
                    if (viewModel.getWordCountOfTodayWordList(todayWordList) == 0) {
                        Toast.makeText(getActivity(), "단어가 없는 단어장이 있습니다", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                startActivity(new Intent(getActivity(), MakeWordGameActivity.class));
            }

            if (gameValue.gameCode == EnumGame.OX_QUIZ.gameCodeInt) {
                ArrayList<TodayWordList> todayWordLists = viewModel.getTodayWordLists();
                for(TodayWordList todayWordList : todayWordLists) {
                    if (viewModel.getWordCountOfTodayWordList(todayWordList) == 0) {
                        Toast.makeText(getActivity(), "단어가 없는 단어장이 있습니다", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                startActivity(new Intent(getActivity(), QuizActivity.class));
            }

            if (gameValue.gameCode == EnumGame.PUT_SPELL_AT_BLANK.gameCodeInt) {
                ArrayList<TodayWordList> todayWordLists =viewModel.getTodayWordLists();
                for(TodayWordList todayWordList : todayWordLists) {
                    if (viewModel.getWordCountOfTodayWordList(todayWordList) == 0) {
                        Toast.makeText(getActivity(), "단어가 없는 단어장이 있습니다", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                startActivity(new Intent(getActivity(), PutSpellAtBlankActivity.class));
            }

            if (gameValue.gameCode == EnumGame.FOUR_CARD.gameCodeInt) {
                ArrayList<TodayWordList> todayWordLists = viewModel.getTodayWordLists();
                for(TodayWordList todayWordList : todayWordLists) {
                    if (viewModel.getWordCountOfTodayWordList(todayWordList) == 0) {
                        Toast.makeText(getActivity(), "단어가 없는 단어장이 있습니다", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                startActivity(new Intent(getActivity(), FourCardActivity.class));
            }

        });
    }

}