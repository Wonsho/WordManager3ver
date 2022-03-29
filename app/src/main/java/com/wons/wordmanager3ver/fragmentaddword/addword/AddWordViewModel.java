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
    private MutableLiveData<Word> mutableLiveData;
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

    public void setLiveData(Word word) {
        if(this.mutableLiveData == null) {
            this.mutableLiveData = new MutableLiveData<>();
        }
        this.mutableLiveData.setValue(word);
    }

    public Word getLiveData() {
        if(this.mutableLiveData == null) {
            this.mutableLiveData = new MutableLiveData<>();
        }
        return this.mutableLiveData.getValue();
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

    public WordInfo getWordInfoByWord(Word word) {
        return dao.getWordInfo(word.getWordTitle().trim().toUpperCase(), word.getLanguageCode());
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
        for(Word word1 : wordArr) {
            if(word1.getWordTitle().toUpperCase().equals(word.getWordTitle().toUpperCase())) {
                count ++;
            }
        }
        if(count <= 1) {
            dao.deleteWordInfo(
                    dao.getWordInfo(word.getWordTitle().toUpperCase(), word.getLanguageCode())
            );
        }
        dao.deleteWord(word);
    }

    public void updateWord(Word word, ArrayList<String> changedWord, int languageCode) {
        //todo 참조하는 단어정보를 참조하는 다른 단어가 있을시 업데이트를 하지 않고
        // 새로 생성후 인서트
        // 참조하는 단어가 없을경우 새로 생성함 */
        Word[] allWordByLanguage = dao.getAllWordByLanguageCode(languageCode);
        int count = 0;
        for(Word word2 : allWordByLanguage) {
            if(word.getWordTitle().toUpperCase().trim().equals(word2.getWordTitle().trim().toUpperCase())) {
                count++;
                if(count >= 2) {
                    // todo 참조하는 단어 인포를 바꾸지 않고 새로 생성후 인서트
                    WordInfo wordInfo = new WordInfo(changedWord.get(WORD_TITLE).toUpperCase().trim(), changedWord.get(WORD_KOREAN), languageCode);
                    word.setWordTitle(changedWord.get(WORD_TITLE).trim());
                    dao.insertWordInfo(wordInfo);
                    dao.updateWord(word);
                    return;
                }
            }
        }

        WordInfo wordInfo = dao.getWordInfo(word.getWordTitle().trim().toUpperCase(), languageCode);
        Word word1 = word;
        if(wordInfo.getWordEnglish().equals(changedWord.get(WORD_TITLE).trim().toUpperCase())) {
            //todo 단어 뜻만 바뀌였을때
            wordInfo.wordKorean = changedWord.get(WORD_KOREAN);
            dao.updateWordInfo(wordInfo);
        } else {
            word1.setWordTitle(changedWord.get(WORD_TITLE).trim());
            wordInfo.setWordMemo("");
            wordInfo.setWordEnglish(changedWord.get(WORD_TITLE).toUpperCase());
            wordInfo.wordKorean = changedWord.get(WORD_KOREAN);
            wordInfo.setTestedTimes(0);
            wordInfo.setCorrectTimes(0);
            dao.updateWordInfo(wordInfo);
            dao.updateWord(word1);
        }
        // todo 아니면 단어 뜻 참조와 단어를 바꾼후 update
    }

    public boolean updateWord(ArrayList<String> words, int listCode, int languageCode) {
        Word[] words1 = dao.getAllWordByLanguageByListCode(languageCode, listCode);
        for(Word word : words1) {
            if(word.getWordTitle().trim().toUpperCase().equals(words.get(WORD_TITLE).trim().toUpperCase())) {
                return false;
            }
        }

    }






}



