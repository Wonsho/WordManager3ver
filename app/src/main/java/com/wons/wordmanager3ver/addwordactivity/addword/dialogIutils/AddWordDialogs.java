package com.wons.wordmanager3ver.addwordactivity.addword.dialogIutils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.wons.wordmanager3ver.databinding.DialogAddWordBinding;
import com.wons.wordmanager3ver.tool.Tools;

import java.util.ArrayList;

public class AddWordDialogs {
    public AlertDialog getDialogDForAddWord(Context context, AddWordCallbackGetString callback, DialogAddWordBinding binding) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = binding.getRoot();

        binding.btnAdd.setOnClickListener(v -> {

            if ((!binding.etWordTitle.getText().toString().isEmpty()) && (!binding.etWordKorean.getText().toString().isEmpty())) {
                ArrayList<String> word = new ArrayList<>();
                String wordTitle = new Tools().removeOverSpace(binding.etWordTitle.getText().toString().trim());
                String wordKorean = new Tools().removeOverSpace(binding.etWordKorean.getText().toString().trim());
                boolean check = true;
                char[] cArr = wordTitle.toCharArray();

                for (char c : cArr) {
                    int value = (int)c;
                    check = true;
                    if(!((value>=97 && value <= 122) || (value >= 65 && value <=90) || c == ' ')) {
                        check = false;
                        binding.etWordTitle.setError("영문 입력 입니다");
                        break;
                    }
                }
                if(check) {
                    word.add(wordTitle);
                    word.add(wordKorean);
                    callback.callback(word);
                }
                binding.etWordTitle.setText("");
                binding.etWordKorean.setText("");

            } else if (binding.etWordTitle.getText().toString().isEmpty()) {
                binding.etWordTitle.setError("필수 입니다");
            } else if (binding.etWordKorean.getText().toString().isEmpty()) {
                binding.etWordKorean.setError("필수 입니다");
            } else {
                binding.etWordKorean.setError("필수 입니다");
                binding.etWordTitle.setError("필수 입니다");
            }
        });
        binding.btnCancel.setOnClickListener(v -> {
            binding.etWordTitle.setText("");
            binding.etWordKorean.setText("");
            callback.callback();
        });

        binding.btnView.setOnClickListener(v -> {
            String url = "https://en.dict.naver.com/#/search?query=" + binding.etWordTitle.getText().toString().trim();
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        });
        builder.setView(view);
        return builder.create();
    }
}
