package com.wons.wordmanager3ver.testword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.wons.wordmanager3ver.databinding.ActivityTestBinding;

public class TestActivity extends AppCompatActivity {
    private ActivityTestBinding binding;
    private TestActivityViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(TestActivityViewModel.class);

    }
}