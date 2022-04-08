package com.wons.wordmanager3ver.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.databinding.ActivityHangManBinding;
import com.wons.wordmanager3ver.databinding.MykeyboardBinding;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;

public class HangManActivity extends AppCompatActivity {

    private ActivityHangManBinding binding;
    private GameViewModel viewModel;
    private MykeyboardBinding keyBoardBinding;
    public static final int RESTART = 0;
    public static final int START = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHangManBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(GameViewModel.class);

        keyBoardBinding = MykeyboardBinding.inflate(getLayoutInflater(), binding.keyboard, true);
        keyBoardSetting();
        startGame(START);
    }

    private void keyBoardSetting() {

        keyBoardBinding.btn0.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());
        });

        keyBoardBinding.btn1.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btn2.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btn3.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btn4.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btn5.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btn6.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btn7.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btn8.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btn9.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnQ.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnW.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnE.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnR.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnT.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnY.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnU.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnI.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnO.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnP.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnA.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnS.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnD.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnF.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnG.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnH.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnJ.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnK.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnL.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnZ.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnX.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnC.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnV.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnB.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnN.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());

        });

        keyBoardBinding.btnM.setOnClickListener(v -> {
            setGameView(((TextView) v).getText().toString().trim());
        });

        keyBoardBinding.btnBack.setOnClickListener(v -> {

        });
    }

    private void startGame(int startCode) {
        viewModel.choiceWord(startCode);
    }

    private void setGameView(String spell) {
        HangMan hangMan = viewModel.hangman.getValue();
        int count = hangMan.putSpell(spell);
        if (count == -1) {
            //todo 이김, 끝
        }
        if (count == 7) {
            //todo 끝남
        }
        setImage(count);
        ArrayList<String> wordToArr = hangMan.wordToArr;
        setWordText(wordToArr);
        viewModel.hangman.setValue(hangMan);
    }

    private void setImage(int count) {
        int imageId;

        switch (count) {
            case 0: {
                imageId = R.drawable.ic_num1;
                break;
            }
            case 1: {
                imageId = R.drawable.ic_num2;
                break;
            }

            case 2: {
                imageId = R.drawable.ic_num3;
                break;
            }

            case 3: {
                imageId = R.drawable.ic_num4;
                break;
            }

            case 4: {
                imageId = R.drawable.ic_num5;
                break;
            }

            case 5: {
                imageId = R.drawable.ic_num6;
                break;
            }

            case 6: {
                imageId = R.drawable.ic_num7;
                break;
            }
            default: {
                return;
            }
        }
        binding.im.setImageResource(imageId);
    }

    private void setWordText(ArrayList<String> arr) {
        StringBuilder builder = new StringBuilder();
        for (String str : arr) {
            if (str.equals(arr.get(arr.size() - 1))) {
                builder.append(str);
            } else {
                builder.append(str + " ");
            }
        }
        binding.tvWordTitle.setText(builder.toString());
    }
}
