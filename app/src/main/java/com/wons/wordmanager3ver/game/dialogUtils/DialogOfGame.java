package com.wons.wordmanager3ver.game.dialogUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.wons.wordmanager3ver.R;

public class DialogOfGame {

    public AlertDialog getDialogWhenGameOver(Context context, CallBackGameDialog callBackGameDialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("GameOver");
        builder.setMessage("게임 오버 되었습니다\n 다시 하시겠습니까?");
        builder.setPositiveButton("같은단어로 다시", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callBackGameDialog.callBack(EnumGameStart.RESTART_SAME_WORD);
            }
        });

        builder.setNeutralButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callBackGameDialog.callBack(EnumGameStart.CLOSE);
            }
        });

        builder.setNegativeButton("다른단어로 다시", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callBackGameDialog.callBack(EnumGameStart.RESTART_OTHER_WORD);
            }
        });

        return builder.create();
    }

    public AlertDialog getDialogWhenCorrect(Context context, CallBackGameDialog callBackGameDialog, String wordTitle, String wordKorean) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("정답입니다");
        builder.setMessage(wordTitle + "\n\n" + wordKorean + "\n" + "다른 단어로 다시 하시겠습니까?");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callBackGameDialog.callBack(EnumGameStart.RESTART_OTHER_WORD);
            }
        });
        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callBackGameDialog.callBack(EnumGameStart.CLOSE);
            }
        });

        return builder.create();
    }
}
