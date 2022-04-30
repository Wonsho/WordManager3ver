package com.wons.wordmanager3ver.datavalues;

import android.content.Context;
import android.widget.Toast;

public class MY {
    private int i = 0;
    private Context c;

    public MY(Context c) {
        this.c = c;
    }

    public void doIt() {
        i++;
        if(i == 5) {
            show();
        }

    }

    private void show() {
        Toast.makeText(c, "만든이 : 정원호 2022-05-01 작성함", Toast.LENGTH_SHORT).show();
    }
}
