package com.wons.wordmanager3ver.game.putspellatblankgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.databinding.ActivityPutSpellAtBlankBinding;
import com.wons.wordmanager3ver.game.GameCode;
import com.wons.wordmanager3ver.game.dialogUtils.CallBackGameDialog;
import com.wons.wordmanager3ver.game.dialogUtils.DialogOfGame;
import com.wons.wordmanager3ver.game.dialogUtils.EnumGameStart;

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
        viewModel.gameStart(GameCode.START);
    }

    private void onclick() {
        binding.btnFinish.setOnClickListener(v -> {
            finish();
        });

        binding.btnReset.setOnClickListener(v -> {
            viewModel.gameStart(GameCode.RESTART_SAME_WORD);
            setGameView();
        });

        binding.btnWordBack.setOnClickListener(v -> {
            viewModel.approachData.inputBack();
            setGameView();
        });

    }

    private void setGameView() {
        ArrayList<String> showWordArr = viewModel.approachData.getShowWord();
        ArrayList<String> spellMenuArr = viewModel.approachData.getSpellMenu();
        String wordKorean = viewModel.approachData.getWordKorean();

        LinearLayout layoutShowWord = binding.containerWordTitle;
        LinearLayout layoutWordSpell = binding.containerWordSpell;

        layoutWordSpell.removeAllViews();
        layoutShowWord.removeAllViews();

        binding.tvWordKorean.setText(wordKorean);

        for (String s : spellMenuArr) {
            View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.text_card_view, null);
            ((TextView)v.findViewById(R.id.tv_card_spell)).setText(s.toUpperCase());

        }

    }

    class Dialog {

        public void showDialogWhenWin() {
            AlertDialog dialog = new DialogOfGame().getDialogWhenCorrect(
                    PutSpellAtBlankActivity.this,
                    new CallBackGameDialog() {
                        @Override
                        public void callBack(EnumGameStart enumGameStart) {
                            doActionByCode(enumGameStart);
                        }
                    },
                    viewModel.gameLiveData.getValue().originWord,
                    viewModel.gameLiveData.getValue().originWordKorean
            );
            dialog.setOnCancelListener(listener -> {
                finish();
            });
            dialog.show();
        }

        public void showDialogWhenGameOver() {
            AlertDialog dialog = new DialogOfGame().getDialogWhenGameOver(
                    PutSpellAtBlankActivity.this,
                    new CallBackGameDialog() {
                        @Override
                        public void callBack(EnumGameStart enumGameStart) {
                            doActionByCode(enumGameStart);
                        }
                    });
            dialog.setOnCancelListener(listener -> {
                finish();
            });
            dialog.show();
        }

        private void doActionByCode(EnumGameStart enumGameStart) {
            switch (enumGameStart) {
                case CLOSE: {
                    finish();
                    break;
                }

                case RESTART_OTHER_WORD: {
                    viewModel.gameStart(GameCode.RESTART_OTHER_WORD);
                    setGameView();
                    break;
                }

                case RESTART_SAME_WORD: {
                    viewModel.gameStart(GameCode.RESTART_SAME_WORD);
                    setGameView();
                    break;
                }
            }
        }
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