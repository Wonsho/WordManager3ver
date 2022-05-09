package com.wons.wordmanager3ver.todayword;

import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordInfo;
import com.wons.wordmanager3ver.datavalues.WordList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TodayWordViewModel extends ViewModel {
    private MyDao dao = MainViewModel.dao;

    public String getListName(int listCode) {
        WordList wordList = dao.getSelectedWordlist(listCode);
        return wordList.listName;
    }

    public ArrayList<String> getWordTitleArr(int listCode) {
        Word[] words = dao.getAllWordByLanguageByListCode(MainViewModel.getUserInfo().getLanguageCode(), listCode);
        ArrayList<String> wordTitleArr = new ArrayList<>();

        for (Word w : words) {
            wordTitleArr.add(w.getWordTitle().trim());
        }

        return wordTitleArr;
    }

    public HashMap<String, WordInfo> getWordInfoMap(ArrayList<String> wordTitleArr) {
        HashMap<String, WordInfo> hashMap = new HashMap<>();

        for (String s : wordTitleArr) {
            WordInfo wordInfo = dao.getWordInfo(s.trim().toUpperCase(), MainViewModel.getUserInfo().getLanguageCode());
            hashMap.put(s, wordInfo);
        }

        return hashMap;
    }

    public String getWordListWordQuantity(int listCode) {
        return String.valueOf(dao.getSelectedWordlist(listCode).getWordCountInt());
    }
}
