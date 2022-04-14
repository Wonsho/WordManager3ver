package com.wons.wordmanager3ver.game.oxquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.wons.wordmanager3ver.databinding.ActivityQuizBinding;

public class QuizActivity extends AppCompatActivity {
    private ActivityQuizBinding binding;
    private QuizGameViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(QuizGameViewModel.class);
        viewModel.initData();
        viewModel.liveIndex.observe(this, index -> {
            if (index == viewModel.getWordQuantity()) {
                //todo 몇개 맞았는지 다이로그 띄우기 -> OX 누를때마다 즉각적인 피드백 ㄱ
                Log.e("Finish", "pass");
                showFinishDialog();
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
        });

        binding.btnX.setOnClickListener(v -> {
            boolean b = viewModel.check(false);
            showDialog(b);
            viewModel.setNextIndex();
        });
    }

    private void showDialog(boolean b) {
        if (b) {
            AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
            builder.setTitle("알림");
            builder.setMessage("정답입니다");
            builder.show();
        }
        if (!b) {
            AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
            builder.setTitle("알림");
            builder.setMessage("틀렸습니다 \n" + "정답은 "+viewModel.getWordKorean() + " 입니다");
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

        alertDialog.setOnDismissListener( listener -> {
            finish();
        });
        alertDialog.show();
    }
}