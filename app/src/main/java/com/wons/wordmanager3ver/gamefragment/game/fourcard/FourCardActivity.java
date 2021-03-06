package com.wons.wordmanager3ver.gamefragment.game.fourcard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.material.snackbar.Snackbar;
import com.wons.wordmanager3ver.databinding.ActivityFourCardBinding;
import com.wons.wordmanager3ver.gamefragment.game.GameCode;

import java.util.ArrayList;

public class FourCardActivity extends AppCompatActivity {
    private ActivityFourCardBinding binding;
    private FourCardViewModel viewModel;
    private int check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFourCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(FourCardViewModel.class);
        viewModel.startGame(GameCode.START);
        onClick();
        setView();
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

    }

    private void onClick() {
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        binding.tv0.setOnClickListener(v -> {
            check = 0;
            checkCardAndThenSetView(v, 0);
        });

        binding.tv1.setOnClickListener(v -> {
            check = 0;
            checkCardAndThenSetView(v, 1);
        });

        binding.tv2.setOnClickListener(v -> {
            check = 0;
            checkCardAndThenSetView(v, 2);
        });

        binding.tv3.setOnClickListener(v -> {
            check = 0;
            checkCardAndThenSetView(v, 3);
        });
    }

    private void setView() {
        ArrayList<String> fourCardData = viewModel.gameData.getGameCardData();
        ArrayList<Boolean> fourCardBoolean = viewModel.gameData.getCardBoolean();
        String life = viewModel.gameData.getLife();
        String wordKorean = viewModel.gameData.getWordKorean();
        String wordSize = viewModel.gameData.getWordArrSize();
        String nowIndex = viewModel.gameData.getNowIndex();

        binding.tvCountOfList.setText(wordSize);
        binding.tvCountOfSelected.setText(nowIndex);
        binding.tvWordKorean.setText(wordKorean);
        binding.tvLife.setText(life);

        int themBlackColor = Color.parseColor("#0D0D0D");
        int themRedColor = Color.parseColor("#FF0000");

        binding.tv0.setText(fourCardData.get(0));
        binding.tv1.setText(fourCardData.get(1));
        binding.tv2.setText(fourCardData.get(2));
        binding.tv3.setText(fourCardData.get(3));

        if (fourCardBoolean.get(0)) {
            binding.tv0.setTextColor(themBlackColor);
        } else {
            binding.tv0.setTextColor(themRedColor);
        }

        if (fourCardBoolean.get(1)) {
            binding.tv1.setTextColor(themBlackColor);
        } else {
            binding.tv1.setTextColor(themRedColor);
        }

        if (fourCardBoolean.get(2)) {
            binding.tv2.setTextColor(themBlackColor);

        } else {
            binding.tv2.setTextColor(themRedColor);
        }

        if (fourCardBoolean.get(3)) {
            binding.tv3.setTextColor(themBlackColor);

        } else {
            binding.tv3.setTextColor(themRedColor);
        }


    }

    private void checkCardAndThenSetView(View v, int cardIndex) {

        if (((TextView) v).getCurrentTextColor() == Color.parseColor("#FF0000")) {
            return;
        }

        class Dialog {

            void showDialogWhenOver() {
                AlertDialog.Builder builder = new AlertDialog.Builder(FourCardActivity.this)
                        .setTitle("??????")
                        .setMessage("?????? ?????? ???????????????\n?????? ???????????????????")
                        .setNegativeButton("???", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                viewModel.startGame(GameCode.GAME_RESTART);
                                setView();
                            }
                        })
                        .setPositiveButton("?????????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });

                builder.setOnCancelListener(listener -> {
                    finish();
                });
                builder.setCancelable(false);
                builder.show();
            }

            void showDialogWhenFinish() {
                AlertDialog.Builder builder = new AlertDialog.Builder(FourCardActivity.this)
                        .setTitle("??????")
                        .setMessage("????????? ???????????????\n?????? ???????????????????")
                        .setNegativeButton("???", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                viewModel.startGame(GameCode.GAME_RESTART);
                                setView();
                            }
                        })
                        .setPositiveButton("?????????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });

                builder.setOnCancelListener(listener -> {
                    finish();
                });
                builder.setCancelable(false);
                builder.show();
            }

        }

        int resultCode = viewModel.checkWordCard(((TextView) v).getText().toString(), cardIndex);

        switch (resultCode) {

            case GameCode.GAME_FINISH: {
                new Dialog().showDialogWhenFinish();
                return;
            }

            case GameCode.GAME_OVER: {
                new Dialog().showDialogWhenOver();
                return;
            }

            case -1 : {
                Toast toast = Toast.makeText(getApplicationContext(), "??????????????? ?????? -1", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,200);
                toast.show();


            }

        }

        setView();

    }

    @Override
    public void onBackPressed() {

        if(check == 0) {
           Toast toast = Toast.makeText(getApplicationContext(), "????????? ????????? ???????????????",Toast.LENGTH_SHORT);
            check ++;
            toast.show();
            return;
        }
        super.onBackPressed();
    }
}