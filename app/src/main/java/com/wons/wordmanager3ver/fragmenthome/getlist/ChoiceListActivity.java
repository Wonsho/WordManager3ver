package com.wons.wordmanager3ver.fragmenthome.getlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.wons.wordmanager3ver.databinding.ActivityChoiceListBinding;

public class ChoiceListActivity extends AppCompatActivity {

    private ActivityChoiceListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChoiceListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}