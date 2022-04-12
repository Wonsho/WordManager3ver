package com.wons.wordmanager3ver.game.makeword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.wons.wordmanager3ver.databinding.ActivityMakeWordGameBinding;

public class MakeWordGameActivity extends AppCompatActivity {
    private ActivityMakeWordGameBinding binding;
    private MakeSpellViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMakeWordGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MakeSpellViewModel.class);

    }
}