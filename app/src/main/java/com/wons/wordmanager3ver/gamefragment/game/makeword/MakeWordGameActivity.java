package com.wons.wordmanager3ver.gamefragment.game.makeword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.wons.wordmanager3ver.databinding.ActivityMakeWordGameBinding;
import com.wons.wordmanager3ver.databinding.TextSpellBinding;
import com.wons.wordmanager3ver.databinding.WordSpellBinding;
import com.wons.wordmanager3ver.gamefragment.game.dialogUtils.CallBackGameDialog;
import com.wons.wordmanager3ver.gamefragment.game.dialogUtils.DialogOfGame;
import com.wons.wordmanager3ver.gamefragment.game.dialogUtils.EnumGameStart;
import com.wons.wordmanager3ver.gamefragment.game.GameCode;

import java.util.ArrayList;

public class MakeWordGameActivity extends AppCompatActivity {
    private ActivityMakeWordGameBinding binding;
    private MakeSpellViewModel viewModel;
    private int backCount = 0;
    boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMakeWordGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MakeSpellViewModel.class);
        viewModel.startGame(GameCode.START);
        onclick();
        setGameView();
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);
    }

    private void onclick() {

        binding.btnFinish.setOnClickListener(v -> {
            finish();
        });

        binding.btnDelete.setOnClickListener(v -> {
            check = true;
            viewModel.onClickBackBtn();
            backCount = 0;
            setGameView();
        });

        binding.btnReset.setOnClickListener(v -> {
            check = true;
            viewModel.onClickReset();
            backCount = 0;
            setGameView();
        });

    }

    private void setGameView() {
        Log.e("setGameView", "pass");
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

                    if ((i == wrongSpellIndex && life != 0) || (i == wrongSpellIndex + 1 && life == 0)) {
                        if(check) {
                            Toast toast = Toast.makeText(getApplicationContext(), "???????????????, ?????? -1", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0,200);
                            toast.show();
                            check = false;
                        }
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
                        check = true;
                        Log.e("restart", "pass");
                        viewModel.startGame(GameCode.RESTART_OTHER_WORD);
                        setGameView();
                        break;
                    }

                    case RESTART_SAME_WORD: {
                        check = true;
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
                        AlertDialog dialog = new DialogOfGame().getDialogWhenGameOver(
                                MakeWordGameActivity.this,
                                new CallBackGameDialog() {
                                    @Override
                                    public void callBack(EnumGameStart enumGameStart) {
                                        new CallBackAction().showDialogByResult(enumGameStart);
                                    }
                                }
                        );
                        dialog.setCancelable(false);
                        dialog.show();
                        break;
                    }

                    case GameCode.GAME_WIN: {
                        AlertDialog dialog = new DialogOfGame().getDialogWhenCorrect(
                                MakeWordGameActivity.this,
                                new CallBackGameDialog() {
                                    @Override
                                    public void callBack(EnumGameStart enumGameStart) {
                                        new CallBackAction().showDialogByResult(enumGameStart);
                                    }
                                }
                        ,viewModel.gameData.getWordTitle(), viewModel.gameData.getWordKorean());
                        dialog.setCancelable(false);
                        dialog.show();
                        break;
                    }

                    case -2: {
                        Toast toast = Toast.makeText(getApplicationContext(), "????????? ?????? ???????????? ????????????\n" +
                                                                                   "    ???????????? ???????????????", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,200);
                        toast.show();
                    }
                }
                setGameView();
            });
            binding.getRoot().setPadding(20,0,0,0);

            spellLay.addView(binding.getRoot());
        }

    }


    @Override
    public void onBackPressed() {
        if (backCount == 0) {
            Toast.makeText(getApplicationContext(), "?????? ??? ????????? ???????????????", Toast.LENGTH_SHORT).show();
            backCount++;
            return;
        }
        super.onBackPressed();
    }
}
