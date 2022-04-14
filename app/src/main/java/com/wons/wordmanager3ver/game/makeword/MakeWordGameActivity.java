package com.wons.wordmanager3ver.game.makeword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
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
import com.wons.wordmanager3ver.databinding.ActivityMakeWordGameBinding;
import com.wons.wordmanager3ver.game.dialogUtils.CallBackGameDialog;
import com.wons.wordmanager3ver.game.dialogUtils.DialogOfGame;
import com.wons.wordmanager3ver.game.dialogUtils.EnumGameStart;
import com.wons.wordmanager3ver.game.gameCode.GameCode;

import java.util.ArrayList;

public class MakeWordGameActivity extends AppCompatActivity {
    private ActivityMakeWordGameBinding binding;
    private MakeSpellViewModel viewModel;
    private final int NON = -1;
    private final int FINISH = 1;
    private final int NOT_YET = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMakeWordGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MakeSpellViewModel.class);

        viewModel.initData(GameCode.START);
        viewModel.startGame(GameCode.START);
        onClick();
        setGameView();
    }

    private void onClick() {

        binding.btnDelete.setOnClickListener(v -> {
            viewModel.onclickBackBtn();
            setGameView();
        });

        binding.btnFinish.setOnClickListener(v -> {
            finish();
        });

        binding.btnReset.setOnClickListener(v -> {
            viewModel.onClickReplaceBtn();
            setGameView();
        });
    }

    private void setGameView() {
        binding.tvWordKorean.setText("단어의 뜻 - \n" + viewModel.getWordKorean());
        LinearLayout layoutWord = binding.layoutWord;
        LinearLayout layoutSpell = binding.layoutSpell;

        layoutSpell.removeAllViews();
        layoutWord.removeAllViews();

        ArrayList<String> showWordArr = viewModel.getShowWordArr();
        ArrayList<String> choiceWordArr = viewModel.getChoiceWordArr();

        for (String s : showWordArr) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.word_spell, null);
            TextView tv_spell = view.findViewById(R.id.tv_spell);
            Log.e("inflate", "passed");
            if (s.equals(" ")) {
                LinearLayout layout = view.findViewById(R.id.container);
                layout.setBackgroundResource(R.drawable.back);
                tv_spell.setTextColor(Color.parseColor("#FFFFFFFF"));
            } else if (s.equals("^")) {
                tv_spell.setTextColor(Color.parseColor("#F2F2F2"));
            } else {
                tv_spell.setText(s.trim().toUpperCase());
            }
            layoutWord.addView(view);
        }

        for(String s : choiceWordArr) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.word_spell, null, true);
            TextView tv_spell = view.findViewById(R.id.tv_spell);

            tv_spell.setText(s.trim().toUpperCase());
            tv_spell.setOnClickListener(v -> {
                int resultCode = viewModel.inputSpell(((TextView)v).getText().toString());
                switch (resultCode) {
                    case NON : {
                        setGameView();
                        Log.e("InputSpell","Error");
                        break;
                    }

                    case FINISH: {
                        setGameView();
                        //todo 뷰모델에서 체크후 다이로그 띄우기
                        int resultGameCode = viewModel.checkWord();
                        switch (resultGameCode) {
                            case GameCode.GAME_OVER: {
                                Toast.makeText(MakeWordGameActivity.this, "Over", Toast.LENGTH_SHORT).show();
                                AlertDialog dialog = new DialogOfGame().getDialogWhenGameOver(MakeWordGameActivity.this, new CallBackGameDialog() {
                                    @Override
                                    public void callBack(EnumGameStart enumGameStart) {
                                        startGameByCode(enumGameStart);
                                    }
                                });
                                dialog.show();
                                break;
                            }

                            case GameCode.GAME_WIN: {
                                Toast.makeText(MakeWordGameActivity.this, "Win", Toast.LENGTH_SHORT).show();
                                AlertDialog dialog = new DialogOfGame().getDialogWhenCorrect(MakeWordGameActivity.this, new CallBackGameDialog() {
                                    @Override
                                    public void callBack(EnumGameStart enumGameStart) {
                                        startGameByCode(enumGameStart);
                                    }
                                }, viewModel.getBaseWord(), viewModel.getWordKorean());

                                dialog.show();
                                break;
                            }
                        }
                        break;
                    }

                    case NOT_YET: {
                        setGameView();
                        break;
                    }
                }
            });

            layoutSpell.addView(view);
        }
    }

    private void startGameByCode(EnumGameStart gameStart) {

        switch (gameStart) {
            case CLOSE: {
                finish();
                break;
            }

            case RESTART_SAME_WORD: {
                viewModel.onClickReplaceBtn();
                setGameView();
                Log.e("startSame", "Passed");
                break;
            }

            case RESTART_OTHER_WORD: {
                viewModel.startGame(GameCode.RESTART_OTHER_WORD);
                Log.e("startOther", "Passed");
                setGameView();
                break;
            }
        }
    }

}
