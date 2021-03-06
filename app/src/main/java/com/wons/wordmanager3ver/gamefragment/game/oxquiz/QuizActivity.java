package com.wons.wordmanager3ver.gamefragment.game.oxquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.wons.wordmanager3ver.databinding.ActivityQuizBinding;

public class QuizActivity extends AppCompatActivity {
    private ActivityQuizBinding binding;
    private QuizGameViewModel viewModel;
    private int check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(QuizGameViewModel.class);
        viewModel.initData();
        viewModel.liveIndex.observe(this, index -> {
            if (index == viewModel.getWordQuantity()) {
                Log.e("Finish", "pass");
                return;
            }
            setView();
        });
        onClick();
        setView();
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);
    }

    private void setView() {
        binding.tvWordCount.setText(String.valueOf(viewModel.getWordQuantity()));
        binding.tvNowCount.setText(String.valueOf(viewModel.getNowCount() + 1));
        QuizObject quizObject = viewModel.getNowQuiz();

        binding.tvWordTitle.setText(quizObject.word_title);
        binding.tvWordKorean.setText(quizObject.word_korean);
    }

    private void onClick() {

        binding.btnFinish.setOnClickListener(v -> {
            finish();
        });

        binding.btnO.setOnClickListener(v -> {
            boolean b = viewModel.check(true);
            showDialog(b);
            viewModel.setNextIndex();
            check = 0;
        });

        binding.btnX.setOnClickListener(v -> {
            boolean b = viewModel.check(false);
            showDialog(b);
            viewModel.setNextIndex();
            check = 0;
        });
    }

    private void showDialog(boolean b) {
        int nowIndex = viewModel.getNowCount();

        if (b) {
            AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
            builder.setTitle("??????");
            builder.setMessage("???????????????\n"+ viewModel.getWordTitle() +"\n"+ viewModel.getWordKorean() + " ?????????");
            builder.setOnDismissListener(listener -> {
                if (nowIndex + 1 == viewModel.getWordQuantity()) {
                    showFinishDialog();
                }
            });
            builder.setNegativeButton("??????", null);
            builder.show();
        }

        if (!b) {
            AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
            builder.setTitle("??????");
            builder.setMessage("??????????????? \n" + viewModel.getWordTitle() +"\n"+ viewModel.getWordKorean() + " ?????????");
            builder.setOnDismissListener(listener -> {
                if (nowIndex + 1 == viewModel.getWordQuantity()) {
                    showFinishDialog();
                }
            });
            builder.setNegativeButton("??????", null);
            builder.show();
        }

    }

    private void showFinishDialog() {
        AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
        builder.setTitle("?????? ???");
        builder.setMessage("????????? : " + viewModel.getCorrectCount() + "/" + viewModel.getWordQuantity());
        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alertDialog = builder.create();

        alertDialog.setOnDismissListener(listener -> {
            finish();
        });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (check == 0) {
            Toast.makeText(getApplicationContext(), "?????? ??? ????????? ???????????????", Toast.LENGTH_SHORT).show();
            check++;
            return;
        }
        super.onBackPressed();
    }
}