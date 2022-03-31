package com.wons.wordmanager3ver.fragmentinfo.adapter;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.datavalues.FlagUserLevelData;
import com.wons.wordmanager3ver.datavalues.UserInfo;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordInfo;

import java.util.ArrayList;
import java.util.Arrays;

public class InfoViewModel {
    private MyDao dao;

    public InfoViewModel() {
        this.dao = MainViewModel.dao;
    }

    public UserInfo getNowUserInfo() {
        int languageCode = MainViewModel.getUserInfo().getLanguageCode();
        UserInfo userInfo = dao.getUserInfoByLanguageCode(languageCode);
        return userInfo;
    }

    public int getWordQuantity(int languageCode) {
        return dao.getAllWordByLanguageCode(languageCode).length;
    }

    public int getUserGrade(int languageCode) {
        double wordInfoCount = 0.0;
        double passedWordCount = 0.0;
        boolean check = false;
        WordInfo[] wordInfos = dao.getAllWordInfoByLanguageCode(languageCode);

        for(WordInfo wordInfo : wordInfos) {
            if(wordInfo.getTestedTimes() != 0) {
                check = true;
                wordInfoCount += 1;
                if(wordInfo.getCorrectPercentage() >= 70) {
                    passedWordCount += 1;
                }
            }
        }

        if(!check) {
            return -1;
        }

        return (int)(passedWordCount / wordInfoCount * 100.0);
    }

    public int getWordListQuantity(int languageCode) {
        return dao.getAllWordlistByLanguageCode(languageCode).length;
    }

    public ArrayList<FlagUserLevelData> getFlagUserData(int languageCode) {
        FlagUserLevelData[] flagUserLevelData = dao.getAllFlagUserDataByLanguage(languageCode);
        if(flagUserLevelData.length == 0) {
            dao.insertFlagUserData(new FlagUserLevelData(languageCode, getNowUserInfo().getStartDay(), 1));
        }
        return new ArrayList<FlagUserLevelData>(Arrays.asList(dao.getAllFlagUserDataByLanguage(languageCode)));
    }
}
