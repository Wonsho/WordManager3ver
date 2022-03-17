package com.wons.wordmanager3ver.fragmentaddword.addword;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.datavalues.WordList;

public class AddWordViewModel extends ViewModel {
    MyDao dao = MainViewModel.dao;
    protected MutableLiveData<WordList> wordListMutableLiveData;

    public MutableLiveData<WordList> getWordListMutableLiveData(int listCode) {
        if(wordListMutableLiveData == null) {
            wordListMutableLiveData = new MutableLiveData<>();
            wordListMutableLiveData.setValue(dao.getSelectedWordlist(listCode));
        }
        return wordListMutableLiveData;
    }

    public void updateWordList() {
        dao.updateWordList(wordListMutableLiveData.getValue());
    }

    public void setWordListMutableLiveData(WordList wordList) {
        this.wordListMutableLiveData.setValue(wordList);
    }
}
