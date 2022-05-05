package com.wons.wordmanager3ver.gamefragment;

import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.datavalues.TodayWordList;

import java.util.ArrayList;
import java.util.Arrays;

public class GameFragmentViewModel extends ViewModel {
    private MyDao dao = MainViewModel.dao;

    public int getWordCountOfTodayWordList(TodayWordList todayWordList) {
        return dao.getAllWordByLanguageByListCode(todayWordList.getListLanguageCode(), todayWordList.getListCode()).length;
    }

    public ArrayList<TodayWordList> getTodayWordLists() {
        return new ArrayList<>(Arrays.asList(dao.getAllTodayListByLanguageCode(MainViewModel.getUserInfo().getLanguageCode())));
    }
}
