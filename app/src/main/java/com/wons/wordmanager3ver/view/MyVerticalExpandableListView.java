package com.wons.wordmanager3ver.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class MyVerticalExpandableListView extends ListView {
    public MyVerticalExpandableListView(Context context) {
        super(context);
    }

    public MyVerticalExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
