package com.wons.wordmanager3ver.studyword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.wons.wordmanager3ver.databinding.ActivityStudyBinding;

public class StudyActivity extends AppCompatActivity {
    private ActivityStudyBinding binding;
    private StudyActivityViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(StudyActivityViewModel.class);
    }
}