package com.wons.wordmanager3ver;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.wons.wordmanager3ver.datavalues.EnumDialogSettingValue;
import com.wons.wordmanager3ver.datavalues.EnumLanguage;
import com.wons.wordmanager3ver.datavalues.EnumSetting;
import com.wons.wordmanager3ver.datavalues.Setting;
import com.wons.wordmanager3ver.datavalues.UsedCount;
import com.wons.wordmanager3ver.datavalues.UserInfo;
import com.wons.wordmanager3ver.datavalues.UserRecommendWordListSettingValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainViewModel extends ViewModel {
    public static MainDatabase database;
    public static MyDao dao;

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
        if(dao.getUsedCount() == null) {
            UsedCount usedCount = new UsedCount();
            long now = System.currentTimeMillis();
            Date mDate = new Date(now);
            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
            String getTime = simpleDate.format(mDate);
            usedCount.usedDay = getTime;
            dao.insertUsedDay(usedCount);
        } else {
            long now = System.currentTimeMillis();
            Date mDate = new Date(now);
            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
            String getTime = simpleDate.format(mDate);
            if(!dao.getUsedCount().usedDay.equals(getTime)) {
                UsedCount usedCount = dao.getUsedCount();
                usedCount.addCount();
                usedCount.usedDay = getTime;
                dao.upDateUsedDay(usedCount);
            }
        }

    }
}

