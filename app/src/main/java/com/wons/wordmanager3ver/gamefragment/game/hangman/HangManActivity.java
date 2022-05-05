package com.wons.wordmanager3ver.gamefragment.game.hangman;

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
import com.wons.wordmanager3ver.databinding.ActivityHangManBinding;
import com.wons.wordmanager3ver.databinding.MykeyboardBinding;
import com.wons.wordmanager3ver.gamefragment.game.dialogUtils.CallBackGameDialog;
import com.wons.wordmanager3ver.gamefragment.game.dialogUtils.DialogOfGame;
import com.wons.wordmanager3ver.gamefragment.game.dialogUtils.EnumGameStart;
import com.wons.wordmanager3ver.gamefragment.game.GameCode;

import java.util.ArrayList;

public class HangManActivity extends AppCompatActivity {

    private ActivityHangManBinding binding;
    private GameViewModel viewModel;
    private MykeyboardBinding keyBoardBinding;
    private int check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHangManBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(GameViewModel.class);
        keyBoardSetting(GameCode.START);
        viewModel.setHangman(GameCode.START);
        setGameView();

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

    }

    private void keyBoardSetting(int startCode) {

        if (startCode == GameCode.START && viewModel.hangman != null) {
            return;
        }

        binding.tvGameOver.setVisibility(View.GONE);

        keyBoardBinding = MykeyboardBinding.inflate(getLayoutInflater(), binding.keyboard, true);

        keyBoardBinding.btn0.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btn1.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);

        });

        keyBoardBinding.btn2.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);

        });

        keyBoardBinding.btn3.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);

        });

        keyBoardBinding.btn4.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);

        });

        keyBoardBinding.btn5.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);

        });

        keyBoardBinding.btn6.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());

            removeView(v);
        });

        keyBoardBinding.btn7.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btn8.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btn9.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnQ.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnW.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnE.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnR.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnT.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnY.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnU.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnI.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnO.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnP.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnA.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnS.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnD.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnF.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnG.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnH.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnJ.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnK.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnL.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnZ.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnX.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnC.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnV.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnB.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnN.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnM.setOnClickListener(v -> {
            inputKeyBoard(((TextView) v).getText().toString().trim());
            removeView(v);
        });

        keyBoardBinding.btnBack.setOnClickListener(v -> {
            return;
        });
    }


    private void removeView(View v) {
        ((TextView) v).setText("");
    }

    private void showDialogs(int code) {
        if (code == GameCode.GAME_OVER) {
            binding.tvGameOver.setVisibility(View.VISIBLE);
            AlertDialog alertDialog = new DialogOfGame().getDialogWhenGameOver(
                    HangManActivity.this, new CallBackGameDialog() {
                        @Override
                        public void callBack(EnumGameStart enumGameStart) {
                            resetGame(enumGameStart);
                        }
                    });
            alertDialog.setCancelable(false);
            alertDialog.show();
        }
        if (code == GameCode.GAME_WIN) {
            AlertDialog dialog = new DialogOfGame().getDialogWhenCorrect(
                    HangManActivity.this, new CallBackGameDialog() {
                        @Override
                        public void callBack(EnumGameStart enumGameStart) {
                            resetGame(enumGameStart);
                        }
                    }, viewModel.wordTitle, viewModel.getWordKorean());

            dialog.setCancelable(false);
            dialog.show();
        }
    }

    private void inputKeyBoard(String spell) {
        check = 0;
        if (spell.isEmpty() || spell.equals("")) {
            return;
        }
        HangMan hangMan = viewModel.hangman.getValue();
        hangMan.putSpell(spell);
        viewModel.hangman.setValue(hangMan);
        setGameView();
    }

    private void setGameView() {
        HangMan hangMan = viewModel.hangman.getValue();
        int count = hangMan.getCount();
        int imageId;

        switch (count) {
            case -1: {
                showDialogs(GameCode.GAME_WIN);
                return;
            }
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
                Log.e("count", String.valueOf(count));
                imageId = R.drawable.ic_num1;
            }
        }
        binding.im.setImageResource(imageId);

        if (imageId == R.drawable.ic_num7) {
            showDialogs(GameCode.GAME_OVER);
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();

        ArrayList<String> strings = hangMan.wordToArr;

        for (int i = 0; i < strings.size(); i++) {
            if (i == strings.size() - 1) {
                stringBuilder.append(strings.get(i));
            } else {
                stringBuilder.append(strings.get(i) + " ");
            }
        }

        LinearLayout layout = binding.container;
        layout.removeAllViews();
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.hangman_spell_view, null);
        String wordSpell = stringBuilder.toString();
        ((TextView) v.findViewById(R.id.tv_wordTitle)).setText(wordSpell);
        layout.addView(v);

        Log.e("title", hangMan.word_title);
    }

    private void resetGame(EnumGameStart enumGameStart) {

        switch (enumGameStart) {
            case CLOSE: {
                finish();
                return;
            }
            case RESTART_SAME_WORD: {
                viewModel.setHangman(GameCode.RESTART_SAME_WORD);
                keyBoardSetting(GameCode.RESTART_SAME_WORD);
                setGameView();
                break;
            }

            case RESTART_OTHER_WORD: {
                viewModel.setHangman(GameCode.RESTART_OTHER_WORD);
                keyBoardSetting(GameCode.RESTART_OTHER_WORD);
                setGameView();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {

        if (check == 0) {
            Toast.makeText(getApplicationContext(), "한번더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
            check++;
            return;
        }

        super.onBackPressed();
    }
}
