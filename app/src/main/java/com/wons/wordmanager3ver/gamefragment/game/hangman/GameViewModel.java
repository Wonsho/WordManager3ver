package com.wons.wordmanager3ver.gamefragment.game.hangman;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordInfo;
import com.wons.wordmanager3ver.gamefragment.game.GameCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameViewModel extends ViewModel {
    private MyDao myDao = MainViewModel.dao;
    public MutableLiveData<HangMan> hangman;
    public String wordTitle;

    //startCode is RESTART_SAME_WORD, RESTART_OTHER_WORD, START
    public void setHangman(int startCode) {
        if (startCode == GameCode.START && hangman == null) {

            this.hangman = new MutableLiveData<>();
            getWordRandomTitle();
            this.hangman.setValue(new HangMan(this.wordTitle));

        } else if (startCode == GameCode.RESTART_OTHER_WORD) {

            getWordRandomTitle();
            HangMan hangMan = new HangMan(wordTitle);
            this.hangman.setValue(hangMan);

        } else if (startCode == GameCode.RESTART_SAME_WORD) {
            HangMan hangMan = new HangMan(wordTitle);
            this.hangman.setValue(hangMan);
        }
    }


    private void getWordRandomTitle() {
        TodayWordList[] todayWordLists = myDao.getAllTodayListByLanguageCode(
                MainViewModel.getUserInfo().getLanguageCode()
        );
        ArrayList<Word> wordArrOfTodayWord = new ArrayList<>();

        for (TodayWordList todayWordList : todayWordLists) {
            Word[] words1 = myDao.getAllWordByLanguageByListCode(
                    todayWordList.getListLanguageCode(),
                    todayWordList.getListCode()
            );
            wordArrOfTodayWord.addAll(Arrays.asList(words1));
        }

        ArrayList<String> unCorrectWordArr = new ArrayList<>();

        for(Word w : wordArrOfTodayWord) {
            if(myDao.getWordInfo(w.getWordTitle().toUpperCase(), w.getLanguageCode()).getTodayTestResult())
                continue;
            unCorrectWordArr.add(w.getWordTitle().trim().toUpperCase());
        }


        int randomNum = new Random().nextInt(unCorrectWordArr.size());
        Log.e("todayWordSize", String.valueOf(unCorrectWordArr.size()));
        Log.e("RandomNum", String.valueOf(randomNum));

        this.wordTitle = unCorrectWordArr.get(randomNum);
        Log.e("word", this.wordTitle);
    }

    public String getWordKorean() {
        WordInfo wordInfo = myDao.getWordInfo(this.wordTitle.trim().toUpperCase(), MainViewModel.getUserInfo().getLanguageCode());
        return wordInfo.wordKorean.trim();
    }

}
