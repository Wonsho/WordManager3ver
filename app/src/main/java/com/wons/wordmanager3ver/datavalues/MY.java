package com.wons.wordmanager3ver.datavalues;

import android.content.Context;
import android.widget.Toast;

public class MY {
    private static int i = 0;
    private static Context c;

    public static void doIt(Context _c) {
        i++;
        if(c == null) {
            c = _c;
        }
        if(i == 10) {
            i = 0;
            show();
        }

    }

    private static void show() {
        Toast.makeText(c, "만든이 : 정원호 2022-05-01 작성함", Toast.LENGTH_SHORT).show();
    }
}
