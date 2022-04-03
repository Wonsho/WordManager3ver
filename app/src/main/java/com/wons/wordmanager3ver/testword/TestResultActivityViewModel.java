package com.wons.wordmanager3ver.testword;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.datavalues.EnumLanguage;
import com.wons.wordmanager3ver.datavalues.TestWordResult;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.datavalues.WordInfo;
import com.wons.wordmanager3ver.datavalues.WordList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TestResultActivityViewModel extends ViewModel {

    public MutableLiveData<Integer> listIndexLiveData;
    private ArrayList<TodayWordList> todayWordLists;
    private HashMap<Integer, ArrayList<TestWordResult>> resultWordMap;
    private MyDao dao = MainViewModel.dao;

    public void setListIndexLiveData(int index) {
        if(this.listIndexLiveData == null) {
            this.listIndexLiveData = new MutableLiveData<>();
        }
        this.listIndexLiveData.setValue(index);
    }

    public int getListIndexLiveData() {
        if(this.listIndexLiveData == null || this.listIndexLiveData.getValue() == null) {
            this.listIndexLiveData = new MutableLiveData<>();
            listIndexLiveData.setValue(0);
        }

        return listIndexLiveData.getValue();
    }

    public void initData() {
        if(listIndexLiveData == null) {
            this.listIndexLiveData = new MutableLiveData<>();
        }
        TestWordResult[] testWordResults = dao.getAllTestWordResult();
        TodayWordList[] todayWordLists = dao.getAllTodayListByLanguageCode(MainViewModel.getUserInfo().getLanguageCode());
        this.todayWordLists = new ArrayList<>(Arrays.asList(todayWordLists));
        for(int i=0 ; i<todayWordLists.length ; i++) {
            ArrayList<TestWordResult> testWordResults1 = new ArrayList<>();
            for (TestWordResult testWordResult : testWordResults) {
                if (testWordResult.getListCodeOfWord() == todayWordLists[i].getListCode()) {
                    testWordResults1.add(testWordResult);
                }
            }
            if (resultWordMap == null) {
                this.resultWordMap = new HashMap<>();
            }
            this.resultWordMap.put(i, testWordResults1);
        }
    }

    public String getLanguage() {
        return EnumLanguage.ENGLISH.getLanguage(MainViewModel.getUserInfo().getLanguageCode());
    }

    public String getListCount() {
        return getListIndexLiveData() + 1 + "/" + todayWordLists.size();
    }

    public String getListCountInResult() {
        return String.valueOf(todayWordLists.size());
    }

    public String getWordCount() {
        return String.valueOf(dao.getAllTestWordResult().length);
    }

    public String getCorrectWordCount() {
        int correctCount = 0;
        TestWordResult[] testWordResults = dao.getAllTestWordResult();
        for(TestWordResult testWordResult : testWordResults) {
            if(testWordResult.getTestResult()) {
                correctCount++;
            }
        }
        return correctCount + "/" + testWordResults.length;
    }

    public HashMap<String, WordInfo> getMapByNowList() {
        int nowIndex = getListIndexLiveData();
        ArrayList<TestWordResult> testWordResults = resultWordMap.get(todayWordLists.get(nowIndex));
        HashMap<String, WordInfo> map = new HashMap<>();

        for(TestWordResult testWordResult : testWordResults) {
            WordInfo wordInfo = dao.getWordInfo(testWordResult.getWordTitle().toUpperCase(), MainViewModel.getUserInfo().getLanguageCode());
            map.put(testWordResult.getWordTitle().toUpperCase(), wordInfo);
        }
        return map;
    }

    public ArrayList<TestWordResult> getTestResult() {
        return resultWordMap.get(getListIndexLiveData());
    }
}

