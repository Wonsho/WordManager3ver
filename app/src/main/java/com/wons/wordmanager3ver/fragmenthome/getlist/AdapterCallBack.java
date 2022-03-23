package com.wons.wordmanager3ver.fragmenthome.getlist;

import com.wons.wordmanager3ver.datavalues.WordList;

import java.util.ArrayList;

public interface AdapterCallBack {
    void callBack(boolean check);
    void callBackIndex(ArrayList<WordList> wordLists);
}
