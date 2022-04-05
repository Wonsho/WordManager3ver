package com.wons.wordmanager3ver.fragmentaddword.addword;

import android.icu.text.IDNA;
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
            WordInfo info = dao.getWordInfo(word.getWordTitle().toUpperCase(), word.getLanguageCode());
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
        if (dao.getWordInfo(word.get(WORD_TITLE).toUpperCase(), getWordListMutableLiveData().getValue().getLanguageCode()) != null) {
            return SAME_WORD_IN_DB;
        }

        return NON;
    }

    public void setLiveDataCount(ArrayList<Word> words) {
        WordList list = getWordListMutableLiveData().getValue();
        list.setWordCountInt(words.size());
        this.wordListMutableLiveData.setValue(list);
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
    }

    public void deleteWord(Word word) {
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

    public boolean checkInList(Word word, ArrayList<String> words) {
        Word[] words1 = dao.getAllWordByLanguageByListCode(word.getLanguageCode(), word.getWordListCodeInt());
        for (Word word1 : words1) {
            if (word1.getWordTitle().trim().toUpperCase().equals(words.get(WORD_TITLE).trim().toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkOriginWordInDB(Word word) {
        Word[] words1 = dao.getAllWordByLanguageCode(word.getLanguageCode());
        int count = 0;

        for (Word word1 : words1) {
            if (word1.getWordTitle().trim().toUpperCase().equals(word.getWordTitle().trim().toUpperCase())) {
                count++;
                if (count == 2) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkChangedWordInDB(ArrayList<String> words, Word word) {
        Word[] words1 = dao.getAllWordByLanguageCode(word.getLanguageCode());
        for(Word word1 : words1) {
            if(word1.getWordTitle().trim().toUpperCase().equals(words.get(WORD_TITLE).trim().toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public void updateWordAndWordInfo(ArrayList<String> words, Word word) {
        WordInfo wordInfo = dao.getWordInfo(word.getWordTitle().trim().toUpperCase(), word.getLanguageCode());
        wordInfo.setWordMemo("");
        wordInfo.setCorrectTimes(0);
        wordInfo.setTestedTimes(0);
        wordInfo.wordKorean = words.get(WORD_KOREAN).trim();
        wordInfo.setWordEnglish(words.get(WORD_TITLE).trim().toUpperCase());
        Word word1 = word;
        word1.setWordTitle(words.get(WORD_TITLE));

        dao.updateWordInfo(wordInfo);
        dao.updateWord(word);

    }

    public void updateWordAndNewWordInfo(ArrayList<String> words, Word word) {
        Word word1 = word;
        word1.setWordTitle(words.get(WORD_TITLE).trim());
        WordInfo wordInfo = new WordInfo(word1.getWordTitle(), words.get(WORD_KOREAN).trim(), word.getLanguageCode());
        dao.updateWord(word1);
        dao.insertWordInfo(wordInfo);
    }

    public boolean checkWordInfo(ArrayList<String> words, Word word) {
        WordInfo wordInfo = dao.getWordInfo(word.getWordTitle().trim().toUpperCase(),word.getLanguageCode());
        if(wordInfo.wordKorean.trim().equals(words.get(WORD_KOREAN).trim())) {
            return true;
        } else {
            return false;
        }
    }

    public void updateWordListGrade() {
        Word[] words1 = dao.getAllWordByLanguageByListCode(
                getWordListMutableLiveData().getValue().getLanguageCode(),
                getWordListMutableLiveData().getValue().getListCodeInt()
                );
        int sum = 0;

        for(Word word : words1) {
            WordInfo wordInfo = dao.getWordInfo(word.getWordTitle().trim().toUpperCase(), word.getLanguageCode());
            sum += wordInfo.getCorrectPercentage();
        }

        int average = sum/words1.length;
        WordList wordList = getWordListMutableLiveData().getValue();
        wordList.setListGradeInt(average);
        dao.updateWordList(wordList);
    }

}





