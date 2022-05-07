package com.wons.wordmanager3ver;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.wons.wordmanager3ver.datavalues.EnumDialogSettingValue;
import com.wons.wordmanager3ver.datavalues.EnumLanguage;
import com.wons.wordmanager3ver.datavalues.EnumSetting;
import com.wons.wordmanager3ver.datavalues.Setting;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.datavalues.UsedCount;
import com.wons.wordmanager3ver.datavalues.UserInfo;
import com.wons.wordmanager3ver.datavalues.UserRecommendWordListSettingValue;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordInfo;
import com.wons.wordmanager3ver.datavalues.WordList;
import com.wons.wordmanager3ver.tool.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainViewModel extends ViewModel {
    public static MainDatabase database;
    public static MyDao dao;
    public static TextToSpeech tts;

    public void buildDataBase(Context context) {
        database = Room.databaseBuilder(context, MainDatabase.class, "MainDataBase").allowMainThreadQueries().build();
        dao = database.getDao();
        EnumSetting[] enumSettings = EnumSetting.values();

        for (EnumSetting enumSetting : enumSettings) {
            if (dao.getSetting(enumSetting.settingCodeId) == null) {
                if (enumSetting.settingCodeId == EnumSetting.DIALOG_SHOW.settingCodeId) {
                    dao.insertUserSetting(new Setting(EnumSetting.DIALOG_SHOW.settingCodeId, EnumDialogSettingValue.SHOW.dialogCodeInt));
                }

                if (enumSetting.settingCodeId == EnumSetting.TTS_SPEED.settingCodeId) {
                    dao.insertUserSetting(new Setting(EnumSetting.TTS_SPEED.settingCodeId, 0));
                }

                if (enumSetting.settingCodeId == EnumSetting.USER_RECOMMEND_STYLE.settingCodeId) {
                    dao.insertUserSetting(new Setting(EnumSetting.USER_RECOMMEND_STYLE.settingCodeId, UserRecommendWordListSettingValue.USER_RECOMMEND_STYLE_RECOMMEND.recommendStyleCodeInt));
                }

                if (enumSetting.settingCodeId == EnumSetting.USER_RECOMMEND_TODAY_LIST_COUNT.settingCodeId) {
                    dao.insertUserSetting(new Setting(EnumSetting.USER_RECOMMEND_TODAY_LIST_COUNT.settingCodeId, 1));
                }

                if (enumSetting.settingCodeId == EnumSetting.LANGUAGE.settingCodeId) {
                    dao.insertUserSetting(new Setting(EnumSetting.LANGUAGE.settingCodeId, EnumLanguage.ENGLISH.languageCodeInt));
                }
            }
        }

        EnumLanguage[] languages = EnumLanguage.values();

        if (languages.length != dao.getAllUserInfo().length) {
            for (EnumLanguage language : languages) {
                try {
                    UserInfo userInfo = new UserInfo(language.languageCodeInt);
                    dao.insertUserInfo(userInfo);
                } catch (Exception e) {

                }
            }
        }
        if (dao.getUsedCount() == null) {
            UsedCount usedCount = new UsedCount();
            String getTime = new Tools().getNoWDate();
            usedCount.usedDay = getTime;
            dao.insertUsedDay(usedCount);
        } else {
            String getTime = new Tools().getNoWDate();
            if (!dao.getUsedCount().usedDay.equals(getTime)) {
                UsedCount usedCount = dao.getUsedCount();
                usedCount.addCount();
                usedCount.usedDay = getTime;
                dao.upDateUsedDay(usedCount);
            }
        }

        EnumLanguage[] enumLanguages = EnumLanguage.values();
        int languageCode = MainViewModel.getUserInfo().getLanguageCode();
        EnumLanguage enumLanguage = EnumLanguage.ENGLISH;
        for (EnumLanguage language : enumLanguages) {
            if (languageCode == language.languageCodeInt) {
                enumLanguage = language;
            }
        }
        tts = new Tools().speakTTS(context, enumLanguage);
    }


    public static UserInfo getUserInfo() {
        return dao.getUserInfoByLanguageCode(dao.getSetting(EnumSetting.LANGUAGE.settingCodeId).settingValue);
    }

    public static void upDateUserInfo(UserInfo userInfo) {
        dao.updateUserInfo(userInfo);
    }

    public static void changeLanguageSetting(int languageCode) {
        dao.updateUserSetting(new Setting(EnumSetting.LANGUAGE.settingCodeId, languageCode));
    }

    public static ArrayList<WordList> getAllWordListByLanguageCode(int languageCode) {
        return new ArrayList<>(Arrays.asList(dao.getAllWordlistByLanguageCode(languageCode)));
    }

    public static int checkSameWordList(int languageCode, String listName) {
        if (dao.getSelectedWordlist(languageCode, listName) == null || dao.getSelectedWordlist(languageCode, listName).length == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public static void insertWordList(WordList wordList) {
        dao.insertWordList(wordList);
    }

    public static void deleteWordList(WordList wordList) {
        Word[] words = dao.getAllWordByLanguageByListCode(wordList.getLanguageCode(), wordList.getListCodeInt());
        for (Word word : words) {
            deleteWord(word);
        }
        dao.deleteWordList(wordList);
    }

    private static void deleteWord(Word word) {
        int count = 0;
        Word[] wordArr = dao.getAllWordByLanguageCode(MainViewModel.getUserInfo().getLanguageCode());
        for (Word word1 : wordArr) {
            if (word1.getWordTitle().toUpperCase().equals(word.getWordTitle().toUpperCase())) {
                count++;
            }
        }
        if (count <= 1) {
            dao.deleteWordInfo(
                    dao.getWordInfo(word.getWordTitle().toUpperCase(), word.getLanguageCode())
            );
        }
        dao.deleteWord(word);
    }

    public static int getAverageWordGradeInWordList(WordList wordList) {

        Word[] words1 = dao.getAllWordByLanguageByListCode(wordList.getLanguageCode(), wordList.listCodeInt);
        int sum = 0;
        boolean check = false;
        for (Word word : words1) {
            WordInfo wordInfo = dao.getWordInfo(
                    word.getWordTitle().trim().toUpperCase(),
                    word.getLanguageCode()
            );
            if(wordInfo.getTestedTimes() != 0) {
                check = true;
            }
            sum += wordInfo.getCorrectPercentage();
        }
        if(!check) {
            return -1;
        }
        return sum / words1.length;
    }


    public static void updateWordList(WordList list) {
        dao.updateWordList(list);
    }



    //todo userinfo



    //todo 뷰의 메소드

    public static Setting getSetting(int settingCode) {
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


    public void setRecommendSettingReset() {
        Setting setting = dao.getSetting(EnumSetting.USER_RECOMMEND_TODAY_LIST_COUNT.settingCodeId);
        setting.settingValue = 1;
        dao.updateUserSetting(setting);
    }

    public boolean checkUsedCount() {
        UsedCount count = dao.getUsedCount();

        // if
        // return Value is
        // false that mean is don't show Dialog,
        // true is show Dialog
        return count.checkCount();

    }







}