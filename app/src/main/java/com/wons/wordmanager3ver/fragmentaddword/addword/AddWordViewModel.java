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

public class AddWordViewModel extends ViewModel {

    final int WORD_TITLE = 0;
    final int WORD_KOREAN = 1;

    MyDao dao = MainViewModel.dao;
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

    public void setWordListMutableLiveData(WordList wordList) {
        this.wordListMutableLiveData.setValue(wordList);
    }

    public ArrayList<Word> getWords() {
        return new ArrayList<>(Arrays.asList(dao.getAllWordByLanguageByListCode(
                wordListMutableLiveData.getValue().getLanguageCode(),
                wordListMutableLiveData.getValue().listCodeInt
        )));
    }

    public ArrayList<WordInfo> getWordInfo() {
        ArrayList<WordInfo> wordInfoArr = new ArrayList<>();
        ArrayList<Word> words = getWords();

        for (Word word : words) {
            wordInfoArr.add(dao.getWordInfo(word.getWordTitle()));
        }

        return wordInfoArr;
    }

    public int checkWord(ArrayList<String> word) {
        if (dao.getAllSameWordByListCode(getWordListMutableLiveData().getValue().getListCodeInt(), word.get(WORD_TITLE)).length != 0) {
            return 2;
        }
        if (dao.getAllWordByLanguageWordTitle(
                getWordListMutableLiveData().getValue().getLanguageCode(), word.get(WORD_TITLE)).length != 0
        ) {
            // DB에 중복 단어
            return 1;
        }
        return 0;
    }

    public void insertWord(ArrayList<String> word) {
        if (dao.getWordInfo(word.get(WORD_TITLE)) == null) {

            Word word1 = new Word(
                    getWordListMutableLiveData().getValue().getLanguageCode(),
                    word.get(WORD_TITLE),
                    getWordListMutableLiveData().getValue().getListCodeInt()
            );

            WordInfo info = new WordInfo(word.get(WORD_TITLE), word.get(WORD_KOREAN),
                    getWordListMutableLiveData().getValue().getLanguageCode());

            dao.insertWord(word1);
            dao.insertWordInfo(info);

            WordList wordList = getWordListMutableLiveData().getValue();
            wordList.addWordCount();
            this.wordListMutableLiveData.setValue(wordList);


        } else {

            Word word1 = new Word(
                    getWordListMutableLiveData().getValue().getLanguageCode(),
                    word.get(WORD_TITLE),
                    getWordListMutableLiveData().getValue().getListCodeInt()
            );

            dao.insertWord(word1);
            WordInfo info = dao.getWordInfo(word.get(WORD_TITLE));
            info.wordKorean = word.get(WORD_KOREAN);
            dao.updateWordInfo(info);
        }
    }

    //todo 3가지 경우
    // 중복 단어가 리스트에 있는 경우 --> return
    // 다른 리스트에 중복 단어가 있는 경우 --> 덮어 씌울껀지 아니면 원래의 걸로 추가 할지 묻고 추가
    // 중복단어가 없고 다른 리스트에도 없는 경우 --> 그냥 추가 */

}
