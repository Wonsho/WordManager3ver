package com.wons.wordmanager3ver.fragmenthome;

import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.datavalues.Setting;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.datavalues.WordList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class HomeFragmentViewModel extends ViewModel {
    private MyDao dao = MainViewModel.dao;

    public Setting getSetting(int settingCode) {
        return dao.getSetting(settingCode);
    }

    public void updateSetting(int settingCode,int settingValue) {
        Setting setting = dao.getSetting(settingCode);
        setting.settingValue = settingValue;
        dao.updateUserSetting(setting);
    }

    public HashMap<Integer, WordList> getTodayWordList(ArrayList<TodayWordList> todayWordLists) {
        ArrayList<TodayWordList> todayWordLists1 = todayWordLists;
        HashMap<Integer, WordList> todayListMap = new HashMap<>();
        for(TodayWordList wordList : todayWordLists1) {
            WordList wordList1 =  dao.getSelectedWordlist(wordList.getListCode());
            todayListMap.put(wordList1.getListCodeInt(), wordList1);
        }

        return  todayListMap;
    }

    public void insertTodayWordList(TodayWordList wordList) {
        dao.insertTodayList(wordList);
    }

    public  void updateTodayList(TodayWordList todayWordList) {
        dao.updateTodayList(todayWordList);
    }

    public ArrayList<TodayWordList> getTodayWordList(int languageCode) {
        return new ArrayList<>(Arrays.asList(dao.getAllTodayListByLanguageCode(languageCode)));
    }

    public void deleteTodayList(TodayWordList todayWordList) {
        dao.deleteTodayList(todayWordList);
    }


    public ArrayList<TodayWordList> getRandomTodayWordList(ArrayList<Integer> integers) {
        ArrayList<TodayWordList> todayWordLists = new ArrayList<>();
        ArrayList<WordList> wordLists = new ArrayList<>(Arrays.asList(dao.getAllWordlistByLanguageCode(MainViewModel.getUserInfo().getLanguageCode())));
        for(int i = 0 ; i < integers.size() ; i++) {
            WordList wordList = wordLists.get(integers.get(i));
            todayWordLists.add(new TodayWordList(wordList.getLanguageCode(), wordList.getListCodeInt(), false));
        }
        return todayWordLists;
    }

    public int getWordCountOfTodayWordList(TodayWordList todayWordList) {
        return dao.getAllWordByLanguageByListCode(todayWordList.getListLanguageCode(), todayWordList.getListCode()).length;
    }

}
