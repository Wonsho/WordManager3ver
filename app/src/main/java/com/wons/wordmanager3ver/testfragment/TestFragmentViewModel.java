package com.wons.wordmanager3ver.testfragment;

import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.datavalues.Word;

import java.util.ArrayList;
import java.util.Arrays;

public class TestFragmentViewModel extends ViewModel {
    private MyDao dao = MainViewModel.dao;

    public int getWordCountOfTodayWordList(TodayWordList todayWordList) {
        return dao.getAllWordByLanguageByListCode(todayWordList.getListLanguageCode(), todayWordList.getListCode()).length;
    }

    public ArrayList<TodayWordList> getTodayWordList() {
        TodayWordList[] todayWordLists = dao.getAllTodayListByLanguageCode(MainViewModel.getUserInfo().getLanguageCode());

        return new ArrayList<>(Arrays.asList(todayWordLists));
    }

    public int getTodayWordQuantity() {
        TodayWordList[] todayWordLists = dao.getAllTodayListByLanguageCode(
                MainViewModel.getUserInfo().getLanguageCode()
        );

        ArrayList<Word> words = new ArrayList<Word>();

        for (TodayWordList t : todayWordLists) {
            words.addAll(Arrays.asList(dao.getAllWordByLanguageByListCode(t.getListLanguageCode(), t.getListCode())));
        }

        return words.size();
    }

    public int getTestTodayWordQuantity() {
        TodayWordList[] todayWordLists = dao.getAllTodayListByLanguageCode(
                MainViewModel.getUserInfo().getLanguageCode()
        );

        int count = 0;

        ArrayList<Word> words = new ArrayList<Word>();

        for (TodayWordList t : todayWordLists) {
            words.addAll(Arrays.asList(dao.getAllWordByLanguageByListCode(t.getListLanguageCode(), t.getListCode())));
        }

        for(Word w : words) {
            if(dao.getWordInfo(w.getWordTitle().trim().toUpperCase(), MainViewModel.getUserInfo().getLanguageCode()).getTodayTestResult()) {
            } else {
                count++;
            }
        }
        return count;
    }

}
