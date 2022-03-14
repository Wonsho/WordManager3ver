package com.wons.wordmanager3ver.testword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.wons.wordmanager3ver.databinding.ActivityTestResultBinding;

public class TestResultActivity extends AppCompatActivity {

    private ActivityTestResultBinding binding;
    private TestResultActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(TestResultActivityViewModel.class);
    }
}