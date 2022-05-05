package com.wons.wordmanager3ver.gamefragment.game.putspellatblankgame;

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
import com.wons.wordmanager3ver.gamefragment.game.GameCode;
import com.wons.wordmanager3ver.gamefragment.game.dialogUtils.CallBackGameDialog;
import com.wons.wordmanager3ver.gamefragment.game.dialogUtils.DialogOfGame;
import com.wons.wordmanager3ver.gamefragment.game.dialogUtils.EnumGameStart;

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
            check = 0;
            viewModel.doResetGame();
            setView();
        });

        binding.btnWordBack.setOnClickListener(v -> {
            check = 0;
            viewModel.deleteSpell();
            setView();
        });
    }

    private void setView() {
        GameResult result = viewModel.getGameResult(new GameResult());
        String wordKorean = viewModel.getGameWordKorean();
        binding.tvWordKorean.setText(wordKorean);

        LinearLayout layoutShowWord = binding.containerWordTitle;
        LinearLayout layoutSpellMenu = binding.containerWordSpell;

        layoutShowWord.removeAllViews();
        layoutSpellMenu.removeAllViews();

        ArrayList<String> showWordArr = result.showWordArr;
        ArrayList<Integer> integers = viewModel.getIndexArr();

        for (int i = 0; i < showWordArr.size(); i++) {
            View v;

            if (integers.contains(i)) {
                v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.text_card_view, null);
                TextView tv = v.findViewById(R.id.tv_card_spell);
                if (showWordArr.get(i).equals("^")) {
                    tv.setText("  ");
                } else {
                    tv.setText(showWordArr.get(i));
                }
            } else {
                v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.text_spell, null);
                TextView tv = v.findViewById(R.id.tv_spelling);

                if (showWordArr.get(i).equals(" ")) {
                    tv.setText("  ");
                } else {
                    tv.setText(showWordArr.get(i));
                }
            }
            v.setPadding(10,0,0,0);
            layoutShowWord.addView(v);
        }

        ArrayList<String> spellMenuArr = result.spellMenuArr;

        for (String s : spellMenuArr) {
            View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.text_card_view, null);
            TextView tv = v.findViewById(R.id.tv_card_spell);
            tv.setText(s);
            tv.setOnClickListener(textView -> {
                check = 0;
                int resultCode = viewModel.inputSpell(s);
                setView();

                if (resultCode == GameCode.GAME_WIN || resultCode == GameCode.GAME_OVER) {
                    showDialogByResult(resultCode);
                }
            });
            v.setPadding(30,0,0,0);
            layoutSpellMenu.addView(v);
        }


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
                dialog.setCancelable(false);
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
                dialog.setCancelable(false);
                dialog.show();
            }

            private void doActionByGameCode(EnumGameStart enumGameStart) {
                switch (enumGameStart) {
                    case RESTART_SAME_WORD: {
                        Log.e("GameCode", "SameWord");
                        viewModel.startGame(GameCode.RESTART_SAME_WORD);
                        setView();

                        break;
                    }

                    case CLOSE: {
                        Log.e("GameCode", "CLOSE");
                        finish();
                        break;
                    }

                    case RESTART_OTHER_WORD: {
                        Log.e("GameCode", "RESTART_OTHER_WORD");
                        viewModel.startGame(GameCode.RESTART_OTHER_WORD);
                        setView();
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