package com.wons.wordmanager3ver.fragmentinfo;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.datavalues.FlagUserLevelData;
import com.wons.wordmanager3ver.datavalues.UserInfo;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordInfo;
import com.wons.wordmanager3ver.datavalues.WordList;

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
        WordList[] wordLists = dao.getAllWordlistByLanguageCode(languageCode);
        int sum = 0;
        for(WordList wordList : wordLists) {
            sum += wordList.getListGradeInt();
        }
        return (int)((double)sum / (double)wordLists.length);
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
