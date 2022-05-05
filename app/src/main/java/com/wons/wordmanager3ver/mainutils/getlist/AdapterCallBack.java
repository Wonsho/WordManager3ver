package com.wons.wordmanager3ver.mainutils.getlist;

import com.wons.wordmanager3ver.datavalues.WordList;

import java.util.ArrayList;

public interface AdapterCallBack {
    void callBack(boolean check);
    void callBackIndex(ArrayList<WordList> wordLists);
}
