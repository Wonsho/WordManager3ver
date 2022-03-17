package com.wons.wordmanager3ver.fragmentaddword.addword;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordInfo;
import com.wons.wordmanager3ver.datavalues.WordList;

import java.util.ArrayList;
import java.util.Arrays;

public class AddWordViewModel extends ViewModel {
    MyDao dao = MainViewModel.dao;
    protected MutableLiveData<WordList> wordListMutableLiveData;

    public void setLiveData(int listCode) {
        if(wordListMutableLiveData == null) {
            wordListMutableLiveData = new MutableLiveData<>();
            wordListMutableLiveData.setValue(dao.getSelectedWordlist(listCode));
        }
    }

    public MutableLiveData<WordList> getWordListMutableLiveData() {
        return wordListMutableLiveData;
    }

    public void updateWordList(WordList wordList) {
        dao.updateWordList(wordList);
    }

    public void setWordListMutableLiveData(WordList wordList) {
        this.wordListMutableLiveData.setValue(wordList);
    }

    public ArrayList<Word> getWords () {
        return new ArrayList<>(Arrays.asList(dao.getAllWordByLanguageByListCode(
                wordListMutableLiveData.getValue().getLanguageCode(),
                wordListMutableLiveData.getValue().listCodeInt
        )));
    }

    public ArrayList<WordInfo> getWordInfo() {
        ArrayList<WordInfo> wordInfoArr = new ArrayList<>();
        ArrayList<Word> words = getWords();

        for(Word word : words) {
            wordInfoArr.add(dao.getWordInfo(word.getWordTitle()));
        }

        return wordInfoArr;
    }
}
