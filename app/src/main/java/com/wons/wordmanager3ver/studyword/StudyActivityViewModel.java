package com.wons.wordmanager3ver.studyword;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordInfo;
import com.wons.wordmanager3ver.datavalues.WordList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class StudyActivityViewModel extends ViewModel {
    private MyDao dao = MainViewModel.dao;
    public MutableLiveData<WordList> wordListMutableLiveData;
    public MutableLiveData<Word> wordMutableLiveData;
    private ArrayList<WordList> wordLists;
    private HashMap<String, ArrayList<Word>> wordMap;
    private HashMap<String, WordInfo> wordInfo;

    public void setWordListMutableLiveData() {
        if (wordListMutableLiveData == null) {
            this.wordListMutableLiveData = new MutableLiveData<>();
        }
    }

    public void setWordMutableLiveData() {
        if (wordMutableLiveData == null) {
            this.wordMutableLiveData = new MutableLiveData<>();
        }
    }

    public MutableLiveData<Word> getWordMutableLiveData() {
        if (wordMutableLiveData == null) {
            this.wordMutableLiveData = new MutableLiveData<>();
        }
        return wordMutableLiveData;
    }

    public HashMap<String, WordInfo> getWordInfo() {
        return wordInfo;
    }

    public HashMap<String, ArrayList<Word>> getWordMap() {
        return wordMap;
    }

    public ArrayList<WordList> getWordLists() {
        return wordLists;
    }

    public WordList getWordListMutableLiveData() {
        if (wordListMutableLiveData == null) {
            this.wordListMutableLiveData = new MutableLiveData<>();
        }
        return wordListMutableLiveData.getValue();
    }

    public void setStudyData() {
        TodayWordList[] todayWordLists = dao.getAllTodayListByLanguageCode(MainViewModel.getUserInfo().getLanguageCode());
        ArrayList<WordList> wordLists = new ArrayList<>();

        for (TodayWordList todayWordList : todayWordLists) {
            wordLists.add(dao.getSelectedWordlist(todayWordList.getListCode()));
        }

        this.wordLists = wordLists;
        HashMap<String, ArrayList<Word>> wordMap = new HashMap<>();
        HashMap<String, WordInfo> wordInfoHashMap = new HashMap<>();

        for (WordList wordList : wordLists) {
            Word[] words = dao.getAllWordByLanguageByListCode(MainViewModel.getUserInfo().getLanguageCode(),
                    wordList.getListCodeInt());
            for (Word word : words) {
                wordInfoHashMap.put(word.getWordTitle().toUpperCase(), dao.getWordInfo(word.getWordTitle().toUpperCase(), wordList.getLanguageCode()));
            }
            wordMap.put(wordList.listName, new ArrayList<>(Arrays.asList(words)));
        }

        this.wordMap = wordMap;
        this.wordInfo = wordInfoHashMap;
    }

    public ArrayList<Word> getSelectedWords() {
        return wordMap.get(wordListMutableLiveData.getValue().listName);
    }

    public WordInfo getWordInfoByWord(Word word) {
        return wordInfo.get(word.getWordTitle().toUpperCase());
    }
}
