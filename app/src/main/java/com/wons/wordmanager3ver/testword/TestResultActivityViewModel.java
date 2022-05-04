package com.wons.wordmanager3ver.testword;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.datavalues.EnumLanguage;
import com.wons.wordmanager3ver.datavalues.FlagUserLevelData;
import com.wons.wordmanager3ver.datavalues.TestWordResult;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.datavalues.UserInfo;
import com.wons.wordmanager3ver.datavalues.WordInfo;
import com.wons.wordmanager3ver.datavalues.WordList;
import com.wons.wordmanager3ver.tool.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestResultActivityViewModel extends ViewModel {
    private MyDao dao = MainViewModel.dao;
    public MutableLiveData<Integer> indexOfLiveData;
    public ArrayList<TodayWordList> todayWordLists;
    public HashMap<Integer, ArrayList<TestWordResult>> testWordMap;
    public HashMap<String, WordInfo> wordInfoHashMap;

    public void setIndexOfLiveData() {
        if (indexOfLiveData == null) {
            this.indexOfLiveData = new MutableLiveData<>();
        }
    }

    public void initIndexOfLiveData() {
        if (indexOfLiveData.getValue() == null || indexOfLiveData.getValue() == -1) {
            indexOfLiveData.setValue(0);
        }
    }

    public void setTodayWordLists() {
        TodayWordList[] todayWordLists = dao.getAllTodayListByLanguageCode(
                MainViewModel.getUserInfo().getLanguageCode()
        );
        this.todayWordLists = new ArrayList<>(Arrays.asList(todayWordLists));
    }

    public void setTestWordMap() {
        ArrayList<TodayWordList> todayWordLists = this.todayWordLists;
        TestWordResult[] testWordResults = dao.getAllTestWordResult();
        HashMap<Integer, ArrayList<TestWordResult>> hashMap = new HashMap<>();

        for (int i = 0; i < todayWordLists.size(); i++) {
            ArrayList<TestWordResult> testWordResults1 = new ArrayList<>();
            for (TestWordResult testWordResult : testWordResults) {
                if (todayWordLists.get(i).getListCode() == testWordResult.getListCodeOfWord()) {
                    testWordResults1.add(testWordResult);
                }
            }
            hashMap.put(i, testWordResults1);
        }
        this.testWordMap = hashMap;
    }

    public void setWordInfoData() {
        TestWordResult[] testWordResults = dao.getAllTestWordResult();
        HashMap<String, WordInfo> wordInfoHashMap = new HashMap<>();
        for (TestWordResult testWordResult : testWordResults) {
            WordInfo wordInfo = dao.getWordInfo(
                    testWordResult.getWordTitle().trim().toUpperCase(),
                    MainViewModel.getUserInfo().getLanguageCode()
            );
            wordInfoHashMap.put(
                    testWordResult.getWordTitle().trim().toUpperCase(),
                    wordInfo
            );
        }
        this.wordInfoHashMap = wordInfoHashMap;
    }

    public String getLanguage() {
        return EnumLanguage.ENGLISH.getLanguage(MainViewModel.getUserInfo().getLanguageCode());
    }

    public int getWordListSize() {
        return this.todayWordLists.size();
    }

    public int getWordCount() {
        return dao.getAllTestWordResult().length;
    }

    public int getCorrectWordCount() {
        int count = 0;
        TestWordResult[] testWordResults = dao.getAllTestWordResult();
        for (TestWordResult testWordResult : testWordResults) {
            if (testWordResult.getTestResult()) {
                count++;
            }
        }
        return count;
    }

    public String getTestResult() {
        for (int i = 0; i < this.todayWordLists.size(); i++) {
            ArrayList<TestWordResult> testWordResults = this.testWordMap.get(i);
            int wordCount = testWordResults.size();
            double correctWordCount = 0.0;
            for (TestWordResult testWordResult : testWordResults) {
                if (testWordResult.getTestResult()) {
                    correctWordCount++;
                }
            }
            if ((int) (correctWordCount / wordCount * 100.0) < 70) {
                return "불합격";
            }
        }
        for (TodayWordList todayWordList : todayWordLists) {
            todayWordList.passOrNo = true;
            dao.updateTodayList(todayWordList);
        }

        int exp = getTestExp();
        Log.e("exp", String.valueOf(exp));
        UserInfo userInfo = dao.getUserInfoByLanguageCode(MainViewModel.getUserInfo().getLanguageCode());
        int userLevel = userInfo.getLv();
        userInfo.addExp(exp);
        dao.updateUserInfo(userInfo);
        UserInfo updatedUserInfo = dao.getUserInfoByLanguageCode(MainViewModel.getUserInfo().getLanguageCode());

        Log.e("level", String.valueOf(userLevel) + " and " + updatedUserInfo.getLv());

        if (userLevel != updatedUserInfo.getLv()) {
            int gap = updatedUserInfo.getLv() - userLevel;

            for(int i = 0; i < gap; i++) {
                int level = userLevel + i + 1;
                FlagUserLevelData flagUserLevelData = new FlagUserLevelData(
                        MainViewModel.getUserInfo().getLanguageCode(),
                        new Tools().getNoWDate(),
                        level
                );
                MainViewModel.dao.insertFlagUserData(flagUserLevelData);
            }
        }

            return "합격";
    }

    private int getTestExp() {
        int wordCount = 0;
        double exp = 0.0;
        TestWordResult[] testWordResults = dao.getAllTestWordResult();

        for (TestWordResult testWordResult : testWordResults) {
            if (wordCount <= 25) {
                 exp += 1.8;
//                exp += 10.8;

            } else {
                exp += 2.0;
            }
            wordCount++;
        }
        return (int) exp;
    }

    public ArrayList<TestWordResult> getTestResultArr() {
        return testWordMap.get(indexOfLiveData.getValue());
    }

    public String getListTitle() {
        WordList wordList = dao.getSelectedWordlist(todayWordLists.get(indexOfLiveData.getValue()).getListCode());
        return wordList.listName.trim();
    }

    public void updateWordData() {
        //todo 시험본 단어 업데이트
        TestWordResult[] testWordResults = dao.getAllTestWordResult();
        for (TestWordResult testWordResult : testWordResults) {
            Log.e("updateWordData", "pass");
            WordInfo wordInfo = wordInfoHashMap.get(
                    testWordResult.getWordTitle().trim().toUpperCase()
            );
            wordInfo.addTestedCount();
            if (testWordResult.getTestResult()) {
                wordInfo.addCorrectCount();
            }
            dao.updateWordInfo(wordInfo);
        }

        updateWordListGrade();

    }

    private void updateWordListGrade() {

        ArrayList<WordList> wordLists = new ArrayList<>();

        for (TodayWordList todayWordList : todayWordLists) {
            WordList wordList = dao.getSelectedWordlist(todayWordList.getListCode());
            wordLists.add(wordList);
        }

        for (WordList wordList : wordLists) {
            int average = MainViewModel.getAverageWordGradeInWordList(wordList);
            Log.e("average", String.valueOf(average));
            wordList.setListGradeInt(average);
            dao.updateWordList(wordList);
        }
    }
}

