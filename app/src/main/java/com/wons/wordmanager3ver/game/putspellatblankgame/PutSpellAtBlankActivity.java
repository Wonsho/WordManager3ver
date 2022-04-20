package com.wons.wordmanager3ver.game.putspellatblankgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.graphics.Color;
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
        viewModel.startGame(GameCode.START);
        onClick();
        setView();
    }

    class GameResult {
        public ArrayList<String> showWordArr;
        public ArrayList<String> spellMenuArr;
    }

    private void onClick() {
        binding.btnFinish.setOnClickListener(v -> {
            finish();
        });

        binding.btnReset.setOnClickListener(v -> {
            viewModel.doResetGame();
        });

        binding.btnWordBack.setOnClickListener(v -> {
            viewModel.deleteSpell();
        });
    }

    private void setView() {
        GameResult result = viewModel.getGameResult(new GameResult());
        String wordKorean = viewModel.getGameWordKorean();


    }

    private void showDialogByResult(int gameCode) {
        class Dialog {
            int code;

            Dialog(int gameCode) {
                this.code = gameCode;
                show(code);
            }

            private void show(int code) {
                switch (code) {
                    case GameCode.GAME_OVER: {
                        showGameOverDialog();
                        break;
                    }

                    case GameCode.GAME_WIN: {
                        showGameWinDialog();
                        break;
                    }

                }
            }
            private void showGameOverDialog() {
                AlertDialog dialog = new DialogOfGame().getDialogWhenGameOver(
                        PutSpellAtBlankActivity.this,
                        new CallBackGameDialog() {
                            @Override
                            public void callBack(EnumGameStart enumGameStart) {
                                doActionByGameCode(enumGameStart);
                            }
                        });
                dialog.setOnCancelListener(listener -> {
                    finish();
                });
                dialog.show();
            }

            private void showGameWinDialog() {
                AlertDialog dialog = new DialogOfGame().getDialogWhenCorrect(
                        PutSpellAtBlankActivity.this,
                        new CallBackGameDialog() {
                            @Override
                            public void callBack(EnumGameStart enumGameStart) {
                                doActionByGameCode(enumGameStart);
                            }
                        },
                        viewModel.getGameWord(),
                        viewModel.getGameWordKorean());
                dialog.setOnCancelListener(listener -> {
                    finish();
                });
                dialog.show();
            }

            private void doActionByGameCode(EnumGameStart enumGameStart) {
                switch (enumGameStart) {
                    case RESTART_SAME_WORD: {
                        viewModel.startGame(GameCode.RESTART_SAME_WORD);
                        break;
                    }

                    case CLOSE: {
                        finish();
                        break;
                    }

                    case RESTART_OTHER_WORD: {
                        viewModel.startGame(GameCode.RESTART_OTHER_WORD);
                        break;
                    }

                }
            }
        }
        new Dialog(gameCode);
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