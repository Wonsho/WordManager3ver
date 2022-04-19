package com.wons.wordmanager3ver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.wons.wordmanager3ver.databinding.ActivityMainBinding;
import com.wons.wordmanager3ver.fragmentaddword.AddWordFragment;
import com.wons.wordmanager3ver.fragmenthome.HomeFragment;
import com.wons.wordmanager3ver.fragmentinfo.InfoFragment;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    private ActivityMainBinding binding;
    private AddWordFragment addWordFragment;
    private InfoFragment infoFragment;
    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("test","Test");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navigationView = findViewById(R.id.btN);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                setFragment(menuItem.getItemId());
                return true;
            }
        });
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.buildDataBase(getApplicationContext());
        addWordFragment = new AddWordFragment();
        infoFragment = new InfoFragment();
        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(binding.fragmentContain.getId(), homeFragment).commit();
    }


    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    private void setFragment(int id) {
        FrameLayout frame = binding.fragmentContain;
        switch (id) {
            case R.id.menu_add: {
                getSupportFragmentManager().beginTransaction().replace(frame.getId(), addWordFragment).commit();
                break;
            }
            case R.id.menu_home: {
                getSupportFragmentManager().beginTransaction().replace(frame.getId(), homeFragment).commit();
                break;
            }
            case R.id.menu_info: {
                getSupportFragmentManager().beginTransaction().replace(frame.getId(), infoFragment).commit();
                break;
            }
        }
    }

    public void setFragmentAddWord() {
        getSupportFragmentManager().beginTransaction().replace(binding.fragmentContain.getId(), new AddWordFragment()).commit();
        binding.btN.setSelectedItemId(R.id.menu_add);
    }
}