package com.wons.wordmanager3ver.testfragment.testword;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.datavalues.TestWordResult;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordInfo;

import java.util.ArrayList;
import java.util.Arrays;

public class TestActivityViewModel extends ViewModel {
    private ArrayList<TodayWordList> todayWordLists;
    private ArrayList<Word> wordsArr;
    public MutableLiveData<Integer> liveDataOfIndex;
    private MyDao myDao = MainViewModel.dao;
    private ArrayList<TestWordResult> testResult;

    public void init() {
        this.todayWordLists = new ArrayList<>();
        this.wordsArr = new ArrayList<>();
        this.liveDataOfIndex = new MutableLiveData<>();
        this.testResult = new ArrayList<>();
    }

    public void setData() {
        this.todayWordLists = new ArrayList<>(Arrays.asList(myDao.getAllTodayListByLanguageCode(MainViewModel.getUserInfo().getLanguageCode())));

        for (TodayWordList todayWordList : todayWordLists) {
            Word[] words = myDao.getAllWordByLanguageByListCode(todayWordList.getListLanguageCode(), todayWordList.getListCode());
            ArrayList<Word> words1 = new ArrayList<>();
            for (Word w : words) {
                WordInfo wordInfo = myDao.getWordInfo(w.getWordTitle().trim().toUpperCase(), w.getLanguageCode());
                if(!wordInfo.getTodayTestResult()) {
                    words1.add(w);
                }
            }
            wordsArr.addAll(words1);
        }
        liveDataOfIndex.setValue(0);
    }

    public Word getWordByIndex() {
        return wordsArr.get(liveDataOfIndex.getValue());
    }

    public void setLiveDataOfIndex(int index) {
        this.liveDataOfIndex.setValue(index);
    }

    public int getLiveDataOfIndex() {
        return liveDataOfIndex.getValue();
    }

    public int getWordSize() {
        return wordsArr.size();
    }

    public String getWordKorean() {
        WordInfo wordInfo = myDao.getWordInfo(
                wordsArr.get(getLiveDataOfIndex()).getWordTitle().trim().toUpperCase(),
                MainViewModel.getUserInfo().getLanguageCode()
        );
        return wordInfo.wordKorean;
    }

    public int getNowIndex() {
        if (liveDataOfIndex == null || liveDataOfIndex.getValue() == null) {
            return -1;
        }
        return liveDataOfIndex.getValue();
    }

    public void addWordResult(String wordTitle) {
        Word word = getWordByIndex();
        TestWordResult wordResult = new TestWordResult(word.getWordId(), word.getWordTitle(), word.getWordListCodeInt(), wordTitle);

        String[] originArr = word.getWordTitle().trim().toUpperCase().split(" ");
        String[] inputArr = wordTitle.toUpperCase().split(" ");

        StringBuilder originBuilder = new StringBuilder();

        for (String s : originArr) {
            originBuilder.append(s.trim());
        }

        StringBuilder inputBuilder = new StringBuilder();

        for (String s : inputArr) {
            inputBuilder.append(s.trim());
        }

        if (originBuilder.toString().equals(inputBuilder.toString())) {
            wordResult.setTestResult(true);
        } else {
            wordResult.setTestResult(false);
        }
        testResult.add(wordResult);

    }

    public void insertWordResultInDB() {
        TestWordResult[] testWordResults = myDao.getAllTestWordResult();

        for (TestWordResult testWordResult : testWordResults) {
            myDao.deleteTestResult(testWordResult);
        }

        ArrayList<TestWordResult> testWordResults1 = this.testResult;
        for (TestWordResult testWordResult : testWordResults1) {
            myDao.insertTestResult(testWordResult);
        }
    }
}
