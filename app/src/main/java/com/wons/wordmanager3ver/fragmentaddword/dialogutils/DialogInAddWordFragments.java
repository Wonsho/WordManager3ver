package com.wons.wordmanager3ver.fragmentaddword.dialogutils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.datavalues.WordList;
import com.wons.wordmanager3ver.fragmentaddword.EnumDo;
import com.wons.wordmanager3ver.tool.Tools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DialogInAddWordFragments {

    public AlertDialog makeDialogForAddList(Context context, DialogAddWodCallback callback, EnumDo enumDo) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_list, null);
        TextView btn_cancel = view.findViewById(R.id.btn_cancel);
        TextView btn_add = view.findViewById(R.id.btn_add);
        EditText et_listName = view.findViewById(R.id.et_wordTitle);

        if(enumDo == EnumDo.RENAME) {
            ((TextView)view.findViewById(R.id.tv)).setText("단어장 수정하기");
            ((TextView)view.findViewById(R.id.btn_add)).setText("수정");
        } else {
            ((TextView)view.findViewById(R.id.tv)).setText("단어장 만들기");
            ((TextView)view.findViewById(R.id.btn_add)).setText("추가");
        }

        btn_cancel.setOnClickListener(v -> {
            et_listName.setText("");
            callback.callBack(false);
        });

        btn_add.setOnClickListener(v -> {
            if (!et_listName.getText().toString().isEmpty()) {
                   callback.callBack(new Tools().removeOverSpace(et_listName.getText().toString().trim()));
            } else {
                callback.callBack("");
            }
        });
        builder.setView(view);
        return builder.create();
    }

    public AlertDialog getAlertDialogForCheck(Context context, DialogAddWodCallback callback, String listName) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("알림");
        builder.setMessage(listName + " 단어장을 삭제하시겠습니까? \n단어장 안에 있는 단어는 복구하실수 없습니다");
        builder.setNegativeButton("삭제하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callback.callBack(true);
            }
        });
        builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return builder.create();
    }


}
