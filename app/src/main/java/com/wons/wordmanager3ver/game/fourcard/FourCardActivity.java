package com.wons.wordmanager3ver.game.fourcard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.wons.wordmanager3ver.databinding.ActivityFourCardBinding;

public class FourCardActivity extends AppCompatActivity {
    private ActivityFourCardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFourCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}