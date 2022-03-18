package com.wons.wordmanager3ver.fragmentaddword.addword.dialogIutils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.tool.Tools;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AddWordDialogs {
    public AlertDialog getDialogDForAddWord(Context context, AddWordCallbackGetString callback) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_word, null);
        EditText et_wordTitle = view.findViewById(R.id.et_wordTitle);
        EditText et_wordKorean = view.findViewById(R.id.et_wordKorean);
        TextView btn_cancel = view.findViewById(R.id.btn_cancel);
        TextView btn_add = view.findViewById(R.id.btn_add);

        btn_add.setOnClickListener(v -> {
            if((!et_wordTitle.getText().toString().isEmpty())&&(!et_wordKorean.getText().toString().isEmpty())) {
                ArrayList<String> word = new ArrayList<>();
                String wordTitle = new Tools().removeOverSpace(et_wordTitle.getText().toString().trim());
                String wordKorean = new Tools().removeOverSpace(et_wordKorean.getText().toString().trim());
                word.add(wordTitle);
                word.add(wordKorean);
                callback.callback(word);

            } else if (et_wordTitle.getText().toString().isEmpty()) {
                et_wordTitle.setError("적어주세요");
            } else if (et_wordKorean.getText().toString().isEmpty()) {
                et_wordKorean.setError("적어주세요");
            }  else {
                et_wordKorean.setError("적어주세요");
                et_wordTitle.setError("적어주세요");
            }
        });
        btn_cancel.setOnClickListener(v -> {
            callback.callback();
        });

        builder.setView(view);
        return builder.create();
    }
}
