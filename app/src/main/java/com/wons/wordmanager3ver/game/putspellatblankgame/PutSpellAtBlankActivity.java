package com.wons.wordmanager3ver.game.putspellatblankgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wons.wordmanager3ver.databinding.ActivityPutSpellAtBlankBinding;
import com.wons.wordmanager3ver.game.GameCode;

import java.util.ArrayList;

public class PutSpellAtBlankActivity extends AppCompatActivity {
    private ActivityPutSpellAtBlankBinding binding;
    private PutSpellBlankViewModel viewModel;
    private int check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPutSpellAtBlankBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(PutSpellBlankViewModel.class);
        onclick();
        viewModel.startGame(GameCode.START);
        setGameView();
    }

    private void onclick() {

        binding.btnFinish.setOnClickListener(v -> {
            finish();
        });

        binding.btnWordBack.setOnClickListener(v -> {
            check = 0;
            viewModel.onclickBack();
            setGameView();
        });

        binding.btnReset.setOnClickListener(v -> {
            check = 0;
            viewModel.onclickReset();
            setGameView();
        });

    }

    private void setGameView() {
        PutSpellGameData data = viewModel.getGameData();
        String wordKorean = data.word_korean;
        ArrayList<String> showWordArr = data.showWordArr;
        ArrayList<String> spellMenu = data.spellMenuArr;
        LinearLayout layoutShowWord = binding.containerWordTitle;
        LinearLayout layoutSpellMenu = binding.containerWordSpell;

        Log.e("wordSize", String.valueOf(showWordArr.size()));

        for(String s : showWordArr) {
            Log.e("WordIndex", s);
        }

        Log.e("spellSize" , String.valueOf(spellMenu.size()));

        for(String s : spellMenu) {
            Log.e("Spell Index", s);
        }


        layoutSpellMenu.removeAllViews();
        layoutShowWord.removeAllViews();



        binding.tvWordKorean.setText(wordKorean);


    }


    @Override
    public void onBackPressed() {

        if (check == 0) {
            check += 1;
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
            return;
        }
        super.onBackPressed();
    }
}