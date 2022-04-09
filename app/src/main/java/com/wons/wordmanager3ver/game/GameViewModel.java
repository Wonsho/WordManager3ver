package com.wons.wordmanager3ver.game;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.datavalues.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameViewModel extends ViewModel {
    private MyDao myDao = MainViewModel.dao;
    public MutableLiveData<HangMan> hangman;
    public String wordTitle;

    //startCode is RESTART, START
    public void setHangman(int startCode) {
        if (startCode == HangManActivity.START && hangman == null) {

            this.hangman = new MutableLiveData<>();
            getWordRandomTitle();
            this.hangman.setValue(new HangMan(this.wordTitle));

        } else if (startCode == HangManActivity.RESTART) {

            getWordRandomTitle();
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

        int randomNum = new Random().nextInt(wordArrOfTodayWord.size());
        Log.e("todayWordSize", String.valueOf(wordArrOfTodayWord.size()));
        Log.e("RandomNum", String.valueOf(randomNum));

        this.wordTitle = wordArrOfTodayWord.get(randomNum).getWordTitle().trim().toUpperCase();
        Log.e("word" , this.wordTitle);
    }

}
