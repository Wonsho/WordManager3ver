package com.wons.wordmanager3ver.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.databinding.ActivityHangManBinding;
import com.wons.wordmanager3ver.databinding.MykeyboardBinding;
import com.wons.wordmanager3ver.game.dialogUtils.CallBackGameDialog;
import com.wons.wordmanager3ver.game.dialogUtils.DialogOfGame;
import com.wons.wordmanager3ver.game.dialogUtils.EnumGameStart;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;

public class HangManActivity extends AppCompatActivity {

    private ActivityHangManBinding binding;
    private GameViewModel viewModel;
    private MykeyboardBinding keyBoardBinding;
    public static final int RESTART = 0;
    public static final int START = 1;
    private final int GAME_OVER = 1;
    private final int GAME_WIN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHangManBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(GameViewModel.class);

        keyBoardBinding = MykeyboardBinding.inflate(getLayoutInflater(), binding.keyboard, true);
        keyBoardSetting();
        viewModel.setHangman(START);
        setGameView();

    }

    private void keyBoardSetting() {

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
        if(code == GAME_OVER) {
            AlertDialog alertDialog = new DialogOfGame().getDialogWhenGameOver(
                    HangManActivity.this, new CallBackGameDialog() {
                        @Override
                        public void callBack(EnumGameStart enumGameStart) {
                            switch (enumGameStart) {
                                case CLOSE: {
                                    finish();
                                    return;
                                }
                                case RESTART_OTHER_WORD: {
                                    // todo 다른 단어로 재실행
                                    break;
                                }
                                case RESTART_SAME_WORD: {
                                    //todo 같은 단어로 재실행
                                    break;
                                }
                            }
                        }
                    });
            alertDialog.show();
        }
        if(code == GAME_WIN) {

        }
    }

    private void inputKeyBoard(String spell) {
        if(spell.isEmpty() || spell.equals("")) {
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

        StringBuilder stringBuilder = new StringBuilder();

        ArrayList<String> strings = hangMan.wordToArr;

        for (int i = 0; i < strings.size(); i++) {
            if (i == strings.size() - 1) {
                stringBuilder.append(strings.get(i));
            } else {
                stringBuilder.append(strings.get(i) + " ");
            }
        }

        binding.tvWordTitle.setText(stringBuilder.toString());
        Log.e("title", hangMan.word_title);
    }

    private void resetGame(EnumGameStart enumGameStart) {

        switch (enumGameStart) {
            case CLOSE: {
                finish();
                return;
            }
            case RESTART_SAME_WORD: {

                break;
            }

            case RESTART_OTHER_WORD: {

                break;
            }

        }
    }

}
