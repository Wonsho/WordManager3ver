package com.wons.wordmanager3ver.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.wons.wordmanager3ver.databinding.ActivityHangManBinding;

public class HangManActivity extends AppCompatActivity {

    private ActivityHangManBinding binding;
    private GameViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHangManBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(GameViewModel.class);


    }
}