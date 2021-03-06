package com.wons.wordmanager3ver;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.wons.wordmanager3ver.datavalues.FlagUserLevelData;
import com.wons.wordmanager3ver.datavalues.Setting;
import com.wons.wordmanager3ver.datavalues.TestWordResult;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.datavalues.UsedCount;
import com.wons.wordmanager3ver.datavalues.UserInfo;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordInfo;
import com.wons.wordmanager3ver.datavalues.WordList;

@Dao
public interface MyDao {
    //유저 셋팅
    @Insert
    void insertUserSetting(Setting setting);

    @Update
    void updateUserSetting(Setting setting);

    @Query("SELECT * FROM setting WHERE settingCode = :settingCode")
    Setting getSetting(int settingCode);

    //유처 랩업 정보
    @Insert
    void insertFlagUserData(FlagUserLevelData data);

    @Query("SELECT * FROM flaguserleveldata WHERE languageCode = :languageCode")
    FlagUserLevelData[] getAllFlagUserDataByLanguage(int languageCode);

    //단어장 리스트
    @Insert
    void insertWordList(WordList list);

    @Update
    void updateWordList(WordList list);

    @Delete
    void deleteWordList(WordList list);

    @Query("SELECT * FROM wordlist WHERE languageCode = :languageCode")
    WordList[] getAllWordlistByLanguageCode(int languageCode);

    @Query("SELECT * FROM (SELECT * FROM wordlist WHERE languageCode = :languageCode) WHERE listName = :listName")
    WordList[] getSelectedWordlist(int languageCode, String listName);

    @Query("SELECT * FROM wordlist WHERE listCodeInt = :listCode")
    WordList getSelectedWordlist(int listCode);


    //유저 정보
    @Insert
    void insertUserInfo(UserInfo userInfo);

    @Update
    void updateUserInfo(UserInfo userInfo);

    @Query("SELECT * FROM UserInfo WHERE languageCode = :languageCode")
    UserInfo getUserInfoByLanguageCode(int languageCode);

    @Query("SELECT * FROM UserInfo")
    UserInfo[] getAllUserInfo();

    //단어
    @Insert
    void insertWord(Word word);

    @Update
    void updateWord(Word word);

    @Delete
    void deleteWord(Word word);

    @Query("SELECT * FROM word WHERE wordId = :wordId" )
    Word getWordById(int wordId);

    @Query("SELECT * FROM Word WHERE languageCode = :languageCode")
    Word[] getAllWordByLanguageCode(int languageCode);

    @Query("SELECT * FROM (SELECT * FROM word WHERE languageCode = :languageCode) WHERE wordListCodeInt = :listCode")
    Word[] getAllWordByLanguageByListCode(int languageCode, int listCode);

    @Query("SELECT * FROM (SELECT * FROM word WHERE wordListCodeInt = :wordListCodeInt) WHERE wordTitle = :wordTitle")
    Word[] getAllSameWordByListCode(int wordListCodeInt, String wordTitle);

    @Query("SELECT * FROM (SELECT * FROM word WHERE languageCode = :languageCode) WHERE wordTitle = :wordTitle")
    Word[] getAllWordByLanguageWordTitle(int languageCode, String wordTitle);


    // 사용 일자
    @Insert
    void insertUsedDay(UsedCount usedCount);

    @Update
    void upDateUsedDay(UsedCount usedCount);

    @Query("SELECT * FROM usedcount")
    UsedCount getUsedCount();

    //오늘의 단어장
    @Insert
    void insertTodayList(TodayWordList todayWordList);

    @Update
    void updateTodayList(TodayWordList todayWordList);

    @Delete
    void deleteTodayList(TodayWordList todayWordList);

    @Query("SELECT * FROM todaywordlist WHERE listLanguageCode = :languageCode")
    TodayWordList[] getAllTodayListByLanguageCode(int languageCode);



    //단어 정보
    @Insert
    void insertWordInfo(WordInfo wordInfo);

    @Delete
    void deleteWordInfo(WordInfo wordInfo);

    @Update
    void updateWordInfo(WordInfo wordInfo);

    @Query("SELECT * FROM(SELECT * FROM wordinfo WHERE languageCode = :languageCode) WHERE wordEnglish = :wordTitle")
    WordInfo getWordInfo(String wordTitle, int languageCode);

    @Query("SELECT * FROM wordinfo WHERE languageCode = :languageCode")
    WordInfo[] getAllWordInfoByLanguageCode(int languageCode);


    // 시험 결과
    @Insert
    void insertTestResult(TestWordResult testWordResult);

    @Delete
    void deleteTestResult(TestWordResult testWordResult);

    @Query("SELECT * FROM testwordresult")
    TestWordResult[] getAllTestWordResult();

}
