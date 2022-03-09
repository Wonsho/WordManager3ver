package com.wons.wordmanager3ver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.wons.wordmanager3ver.databinding.ActivityMainBinding;
import com.wons.wordmanager3ver.fragmentaddword.AddWordFragment;
import com.wons.wordmanager3ver.fragmenthome.HomeFragment;
import com.wons.wordmanager3ver.fragmentinfo.InfoFragment;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    private HomeFragment homeFragment;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigationView = findViewById(R.id.btN);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                setFragment(menuItem.getItemId());
                return true;
            }
        });
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.buildDataBase(getApplicationContext());
    }


    public void setFragment(int id) {
        FrameLayout frame = binding.fragmentContain;
       switch (id) {
            case R.id.menu_add :{
                getSupportFragmentManager().beginTransaction().replace(frame.getId(), new AddWordFragment()).commit();
                break;
            }
           case R.id.menu_home: {
               getSupportFragmentManager().beginTransaction().replace(frame.getId(), new HomeFragment()).commit();
               break;
           }
           case R.id.menu_info: {
               getSupportFragmentManager().beginTransaction().replace(frame.getId(), new InfoFragment()).commit();
               break;
           }
        }
    }
}