package com.wons.wordmanager3ver.fragmentaddword.sameword;

import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordInfo;
import com.wons.wordmanager3ver.datavalues.WordList;
import com.wons.wordmanager3ver.fragmentaddword.addword.AddWordActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class
CheckListViewModel extends ViewModel {
    private MyDao dao = MainViewModel.dao;

    public ArrayList<WordList> getSameWordList(String wordTitle, int languageCode) {
        ArrayList<WordList> listArr = new ArrayList<>();
        ArrayList<Word> arr = new ArrayList<>(Arrays.asList(dao.getAllWordByLanguageCode(languageCode)));
        for (Word word : arr) {
            if ((word.getWordTitle().toUpperCase()).equals(wordTitle.toUpperCase())) {
                WordList wordList = dao.getSelectedWordlist(word.getWordListCodeInt());
                listArr.add(wordList);
            }
        }
        return listArr;
    }

    public WordInfo getWordInfo(String wordTitle, int languageCode) {
        return dao.getWordInfo(wordTitle.toUpperCase(), languageCode);
    }

    public void insertNewWord(int languageCode, int listCode, String wordTitle, String wordKorean) {
        WordInfo wordInfo = dao.getWordInfo(wordTitle.toUpperCase(), languageCode);
        wordInfo.wordKorean = wordKorean;
        dao.updateWordInfo(wordInfo);
        insertOriginWord(listCode, languageCode, wordTitle);

    }

    public void insertOriginWord(int listCode, int languageCode, String wordTitle) {
        dao.insertWord(new Word(languageCode, wordTitle.trim(), listCode));
    }

    public void updateWordAndDeleteWordInfoToOrigin(int wordId, String wordTitle) {
        Word word = dao.getWordById(wordId);
        WordInfo wordInfo = dao.getWordInfo(word.getWordTitle().trim().toUpperCase(), word.getLanguageCode());
        dao.deleteWordInfo(wordInfo);
        word.setWordTitle(wordTitle.trim());
        dao.updateWord(word);
    }

    public void updateWordAndDeleteWordInfoToNewInfo(int wordId, String wordTitle, String wordKorean) {
        Word word = dao.getWordById(wordId);
        WordInfo wordInfo = dao.getWordInfo(word.getWordTitle().trim().toUpperCase(), word.getLanguageCode());
        dao.deleteWordInfo(wordInfo);
        word.setWordTitle(wordTitle.trim());
        dao.updateWord(word);
        WordInfo wordInfo1 = dao.getWordInfo(wordTitle.trim().toUpperCase(), word.getLanguageCode());
        wordInfo1.wordKorean = wordKorean.trim();
        dao.updateWordInfo(wordInfo1);
    }

    public void updateWordToOrigin(int wordId, String wordTitle) {
        Word word = dao.getWordById(wordId);
        word.setWordTitle(wordTitle.trim().toUpperCase());
        dao.updateWord(word);
    }

    public void updateWordToNew(int wordId, String wordTitle, String wordKorean) {
        Word word = dao.getWordById(wordId);
        WordInfo wordInfo = dao.getWordInfo(wordTitle.trim().toUpperCase(), word.getLanguageCode());
        word.setWordTitle(wordTitle.trim());
        wordInfo.wordKorean = wordKorean.trim();
        dao.updateWord(word);
        dao.updateWordInfo(wordInfo);
    }


}
