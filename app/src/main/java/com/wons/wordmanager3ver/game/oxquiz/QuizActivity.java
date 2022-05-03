package com.wons.wordmanager3ver.game.oxquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.wons.wordmanager3ver.databinding.ActivityQuizBinding;

import java.util.ArrayList;

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
            builder.setTitle("알림");
            builder.setMessage("정답입니다\n"+ viewModel.getWordTitle() +"\n"+ viewModel.getWordKorean() + " 입니다");
            builder.setOnDismissListener(listener -> {
                if (nowIndex + 1 == viewModel.getWordQuantity()) {
                    showFinishDialog();
                }
            });
            builder.setNegativeButton("확인", null);
            builder.show();
        }

        if (!b) {
            AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
            builder.setTitle("알림");
            builder.setMessage("틀렸습니다 \n" + viewModel.getWordTitle() +"\n"+ viewModel.getWordKorean() + " 입니다");
            builder.setOnDismissListener(listener -> {
                if (nowIndex + 1 == viewModel.getWordQuantity()) {
                    showFinishDialog();
                }
            });
            builder.setNegativeButton("확인", null);
            builder.show();
        }

    }

    private void showFinishDialog() {
        AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
        builder.setTitle("퀴즈 끝");
        builder.setMessage("정답률 : " + viewModel.getCorrectCount() + "/" + viewModel.getWordQuantity());
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
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
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
            check++;
            return;
        }
        super.onBackPressed();
    }
}