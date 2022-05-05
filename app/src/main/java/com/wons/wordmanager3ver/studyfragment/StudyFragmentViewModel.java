package com.wons.wordmanager3ver.studyfragment;

import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.datavalues.TodayWordList;

import java.util.ArrayList;
import java.util.Arrays;

public class StudyFragmentViewModel extends ViewModel {
    private MyDao dao = MainViewModel.dao;

    public int getWordCountOfTodayWordList(TodayWordList todayWordList) {
        return dao.getAllWordByLanguageByListCode(todayWordList.getListLanguageCode(), todayWordList.getListCode()).length;
    }

    public ArrayList<TodayWordList> getTodayWordList() {
        TodayWordList[] todayWordLists = dao.getAllTodayListByLanguageCode(MainViewModel.getUserInfo().getLanguageCode());

        return new ArrayList<>(Arrays.asList(todayWordLists));
    }

}
