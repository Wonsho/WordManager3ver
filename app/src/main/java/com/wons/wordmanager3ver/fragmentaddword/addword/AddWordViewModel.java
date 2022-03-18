package com.wons.wordmanager3ver.fragmentaddword.addword;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordInfo;
import com.wons.wordmanager3ver.datavalues.WordList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

public class AddWordViewModel extends ViewModel {

    static final int WORD_TITLE = 0;
    static final int WORD_KOREAN = 1;

    static final int NON = 0;
    static final int SAME_WORD_IN_DB = 1;
    static final int SAME_WORD_IN_LIST = 2;


    private MyDao dao = MainViewModel.dao;
    protected MutableLiveData<WordList> wordListMutableLiveData;

    public void setLiveData(int listCode) {
        if (wordListMutableLiveData == null) {
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

    public ArrayList<Word> getAllWordInList() {

        return new ArrayList<>(Arrays.asList(dao.getAllWordByLanguageByListCode(
                getWordListMutableLiveData().getValue().getLanguageCode(),
                getWordListMutableLiveData().getValue().getListCodeInt()
        )));
    }

    public HashMap<String, WordInfo> getWordInfo() {
        HashMap<String, WordInfo> map = new HashMap<>();
        ArrayList<Word> wordArr = new ArrayList<>(Arrays.asList(dao.getAllWordByLanguageByListCode(
                getWordListMutableLiveData().getValue().getLanguageCode(),
                getWordListMutableLiveData().getValue().getListCodeInt())));
        for (Word word : wordArr) {
            WordInfo info = dao.getWordInfo(word.getWordTitle().toUpperCase());
            map.put(word.getWordTitle(), info);
        }

        return map;
    }

    public int getWordCount() {
        return getWordListMutableLiveData().getValue().getWordCountInt();
    }

    public int getResultCodeWhenAddWord(ArrayList<String> word) {
        ArrayList<Word> wordArr = new ArrayList<>(Arrays.asList(dao.getAllWordByLanguageByListCode(
                getWordListMutableLiveData().getValue().getLanguageCode(),
                getWordListMutableLiveData().getValue().getListCodeInt()
        )));
        for (Word word1 : wordArr) {
            if (word1.getWordTitle().toUpperCase().equals(word.get(WORD_TITLE).toUpperCase().trim())) {
                return SAME_WORD_IN_LIST;
            }
        }
        if (dao.getWordInfo(word.get(WORD_TITLE).toUpperCase()) != null) {
            return SAME_WORD_IN_DB;
        }

        return NON;
    }

    public void insertWord(ArrayList<String> word) {
        Word word1 = new Word(
                getWordListMutableLiveData().getValue().getLanguageCode(),
                word.get(WORD_TITLE),
                getWordListMutableLiveData().getValue().getListCodeInt()
        );
       WordInfo info = new WordInfo(
               word.get(WORD_TITLE).toUpperCase(),
               word.get(WORD_KOREAN),
               getWordListMutableLiveData().getValue().getLanguageCode());

       dao.insertWord(word1);
       dao.insertWordInfo(info);

       WordList wordList = getWordListMutableLiveData().getValue();
       wordList.addWordCount();
       this.getWordListMutableLiveData().setValue(wordList);
    }

    public static void updateWord(ArrayList<String> word) {

    }




}

//todo 3가지 경우
// 중복 단어가 리스트에 있는 경우 --> return
// 다른 리스트에 중복 단어가 있는 경우 --> 덮어 씌울껀지 아니면 원래의 걸로 추가 할지 묻고 추가
// 중복단어가 없고 다른 리스트에도 없는 경우 --> 그냥 추가 */


