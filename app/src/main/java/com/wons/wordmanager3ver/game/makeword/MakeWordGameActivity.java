package com.wons.wordmanager3ver.game.makeword;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.databinding.ActivityMakeWordGameBinding;
import com.wons.wordmanager3ver.databinding.TextSpellBinding;
import com.wons.wordmanager3ver.databinding.WordSpellBinding;
import com.wons.wordmanager3ver.game.dialogUtils.CallBackGameDialog;
import com.wons.wordmanager3ver.game.dialogUtils.DialogOfGame;
import com.wons.wordmanager3ver.game.dialogUtils.EnumGameStart;
import com.wons.wordmanager3ver.game.GameCode;

import java.util.ArrayList;

public class MakeWordGameActivity extends AppCompatActivity {
    private ActivityMakeWordGameBinding binding;
    private MakeSpellViewModel viewModel;
    private int backCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMakeWordGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MakeSpellViewModel.class);
        viewModel.startGame(GameCode.START);
        onclick();
        setGameView();
    }

    private void onclick() {

        binding.btnFinish.setOnClickListener(v -> {
            finish();
        });

        binding.btnDelete.setOnClickListener(v -> {
            viewModel.onClickBackBtn();
            backCount = 0;
            setGameView();
        });

        binding.btnReset.setOnClickListener(v -> {
            viewModel.onClickReset();
            backCount = 0;
            setGameView();
        });

    }

    private void setGameView() {
        int wrongSpellIndex = viewModel.gameData.check();
        int life = viewModel.gameData.getLife();
        ArrayList<String> inputSpellArr = viewModel.gameData.getInputArr();
        ArrayList<String> spellMenuArr = viewModel.gameData.getSpellMenuArr();
        String wordKorean = viewModel.gameData.getWordKorean();
        LinearLayout wordLay = binding.layoutWord;
        LinearLayout spellLay = binding.layoutSpell;
        wordLay.removeAllViews();
        spellLay.removeAllViews();
        binding.tvLife.setText(String.valueOf(life));
        binding.tvWordKorean.setText(wordKorean);

        for (int i = 0; i < inputSpellArr.size(); i++) {
            View v;
            String s = inputSpellArr.get(i);

            if (!s.equals(" ")) {
                WordSpellBinding binding = WordSpellBinding.inflate(getLayoutInflater());

                if (s.equals("^")) {
                    binding.tvSpell.setTextColor(Color.parseColor("#F2F2F2"));
                } else {
                    binding.tvSpell.setText(s);

                    if (i == wrongSpellIndex && life != 0) {
                        binding.tvSpell.setTextColor(Color.parseColor("#FF0000"));
                    }
                }
                v = binding.getRoot();
            } else {
                TextSpellBinding binding = TextSpellBinding.inflate(getLayoutInflater());
                binding.tvSpelling.setText("  ");
                v = binding.getRoot();
            }

            wordLay.addView(v);
        }

        class CallBackAction {
            void showDialogByResult(EnumGameStart enumGameStart) {

                switch (enumGameStart) {

                    case CLOSE: {
                        finish();
                        break;
                    }

                    case RESTART_OTHER_WORD: {
                        viewModel.startGame(GameCode.RESTART_OTHER_WORD);
                        setGameView();
                        break;
                    }

                    case RESTART_SAME_WORD: {
                        viewModel.startGame(GameCode.RESTART_SAME_WORD);
                        setGameView();
                        break;
                    }
                }
            }
        }

        for (String s : spellMenuArr) {
            WordSpellBinding binding = WordSpellBinding.inflate(getLayoutInflater());
            binding.tvSpell.setText(s);
            binding.getRoot().setOnClickListener(v -> {
                int resultCode = viewModel.onClickSpell(s);

                switch (resultCode) {
                    case GameCode.GAME_OVER: {
                        new DialogOfGame().getDialogWhenGameOver(
                                MakeWordGameActivity.this,
                                new CallBackGameDialog() {
                                    @Override
                                    public void callBack(EnumGameStart enumGameStart) {
                                        new CallBackAction().showDialogByResult(enumGameStart);
                                    }
                                }
                        ).show();
                        break;
                    }

                    case GameCode.GAME_WIN: {
                        new DialogOfGame().getDialogWhenCorrect(
                                MakeWordGameActivity.this,
                                new CallBackGameDialog() {
                                    @Override
                                    public void callBack(EnumGameStart enumGameStart) {
                                        new CallBackAction().showDialogByResult(enumGameStart);
                                    }
                                }
                        ,viewModel.gameData.getWordTitle(), viewModel.gameData.getWordKorean()).show();
                        break;
                    }

                    case -2: {
                        Toast.makeText(getApplicationContext(), "정답이 아닌 스펠링이 있습니다\n" +
                                                                      "  뒤로가기를 눌러주세요", Toast.LENGTH_SHORT).show();
                    }
                }
                setGameView();
            });

            spellLay.addView(binding.getRoot());
        }

    }


    @Override
    public void onBackPressed() {
        if (backCount == 0) {
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
            backCount++;
            return;
        }
        super.onBackPressed();
    }
}
