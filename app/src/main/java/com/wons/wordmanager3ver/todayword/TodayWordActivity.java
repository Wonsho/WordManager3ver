package com.wons.wordmanager3ver.todayword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.wons.wordmanager3ver.databinding.ActivityTodayWordBinding;

public class TodayWordActivity extends AppCompatActivity {
    private ActivityTodayWordBinding binding;
    private TodayWordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTodayWordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(TodayWordViewModel.class);
    }
}