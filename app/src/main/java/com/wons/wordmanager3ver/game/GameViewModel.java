package com.wons.wordmanager3ver.game;

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

    public void choiceWord(int startCode) {

        if (hangman != null && startCode == HangManActivity.START) {
            return;
        } else if (startCode == HangManActivity.RESTART) {
            hangman = new MutableLiveData<>();
            choiceWord(HangManActivity.START);
        } else {
            hangman = new MutableLiveData<>();
        }

        TodayWordList[] todayWordLists = myDao.getAllTodayListByLanguageCode(
                MainViewModel.getUserInfo().getLanguageCode()
        );
        ArrayList<Word> arrayList = new ArrayList<>();

        for (TodayWordList todayWordList : todayWordLists) {
            arrayList.addAll(new ArrayList<>(Arrays.asList(myDao.getAllWordByLanguageByListCode(
                    MainViewModel.getUserInfo().getLanguageCode(),
                    todayWordList.getListCode()
            ))));
        }

        Random random = new Random();

        int randomNum = random.nextInt(arrayList.size());

        this.hangman.setValue(new HangMan(arrayList.get(randomNum).getWordTitle()));
    }
}
