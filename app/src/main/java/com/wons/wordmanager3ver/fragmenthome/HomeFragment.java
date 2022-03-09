package com.wons.wordmanager3ver.fragmenthome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.databinding.FragmentHomeBinding;
import com.wons.wordmanager3ver.fragmenthome.adapter.GameListAdapter;
import com.wons.wordmanager3ver.fragmenthome.value.EnumGame;
import com.wons.wordmanager3ver.fragmenthome.value.GameValue;

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

    }

    private void setGameList() {
        if(binding.lvGame3.getAdapter() == null) {
            binding.lvGame3.setAdapter(new GameListAdapter());
        }
        ArrayList<GameValue> gameValues = new ArrayList<>();
        gameValues.add(new GameValue(EnumGame.HANGMAN_GAME));
        ((GameListAdapter)binding.lvGame3.getAdapter()).setData(gameValues);
        ((GameListAdapter)binding.lvGame3.getAdapter()).notifyDataSetChanged();
    }
}