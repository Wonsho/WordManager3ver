package com.wons.wordmanager3ver.addwordactivity.memo;

import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.datavalues.EnumDialogSettingValue;
import com.wons.wordmanager3ver.datavalues.EnumSetting;
import com.wons.wordmanager3ver.datavalues.Setting;
import com.wons.wordmanager3ver.datavalues.WordInfo;

public class MemoViewModel extends ViewModel {
   private MyDao dao = MainViewModel.dao;

    public WordInfo getWordInfo(String wordTitle, int languageCode) {
       return dao.getWordInfo(wordTitle.toUpperCase(), languageCode);
    }

    public void insertMemo(WordInfo wordInfo) {
        dao.updateWordInfo(wordInfo);
    }

    public boolean checkSameData(WordInfo wordInfo, String memoStr) {
        if(!wordInfo.getWordMemo().trim().equals(memoStr.trim())) {
            return true;
        } else {
            return false;
        }
    }

    public Setting getDialogSetting() {
        return dao.getSetting(EnumSetting.DIALOG_SHOW.settingCodeId);
    }

    public void updateSetting() {
        dao.updateUserSetting(new Setting(EnumSetting.DIALOG_SHOW.settingCodeId, EnumDialogSettingValue.NON.dialogCodeInt));
    }

}
