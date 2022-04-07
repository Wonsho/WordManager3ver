package com.wons.wordmanager3ver.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.wons.wordmanager3ver.R;

public class MyKeyBoard extends LinearLayout {
    public MyKeyBoard(Context context) {
        super(context);
        init(context);
    }

    public MyKeyBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.mykeyboard, this, true);
    }
}
