package com.wons.wordmanager3ver.fragmentaddword;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.databinding.FragmentAddWordBinding;

public class AddWordFragment extends Fragment {

    private FragmentAddWordBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddWordBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }
}