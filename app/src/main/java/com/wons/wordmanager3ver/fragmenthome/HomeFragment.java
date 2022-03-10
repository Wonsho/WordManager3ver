package com.wons.wordmanager3ver.fragmenthome;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wons.wordmanager3ver.MainActivity;
import com.wons.wordmanager3ver.databinding.FragmentHomeBinding;
import com.wons.wordmanager3ver.fragmenthome.adapter.GameListAdapter;
import com.wons.wordmanager3ver.fragmenthome.dialogutils.CallBackInHomeFragment;
import com.wons.wordmanager3ver.fragmenthome.dialogutils.DialogUtilsInHomeFragment;
import com.wons.wordmanager3ver.fragmenthome.value.EnumGame;
import com.wons.wordmanager3ver.fragmenthome.value.GameValue;
import com.wons.wordmanager3ver.studyword.StudyActivity;
import com.wons.wordmanager3ver.testword.TestActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);


        onC();
        setGameList();
        return binding.getRoot();
    }


    private void onC() {
        binding.btnSetting.setOnClickListener(v -> {
            AlertDialog alertDialog = new DialogUtilsInHomeFragment().getDialogForTodayWordList(this.getContext(), 1,1, new CallBackInHomeFragment() {
                @Override
                public void callback(int setting, int listCount) {

                }
            });
            alertDialog.show();
        });
        binding.btnStudy.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), StudyActivity.class));
        });
        binding.btnTest.setOnClickListener( v -> {
            startActivity(new Intent(getActivity(), TestActivity.class));
        });
        binding.btnAddList.setOnClickListener(v -> {
            ((MainActivity)getActivity()).setFragmentAddWord();
        });
    }

    private void setGameList() {
        if(binding.lvGame3.getAdapter() == null) {
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
        ((GameListAdapter)binding.lvGame3.getAdapter()).setData(gameValues);
        ((GameListAdapter)binding.lvGame3.getAdapter()).notifyDataSetChanged();
    }
}