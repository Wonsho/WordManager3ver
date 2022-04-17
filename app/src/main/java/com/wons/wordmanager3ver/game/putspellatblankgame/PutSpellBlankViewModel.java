package com.wons.wordmanager3ver.game.putspellatblankgame;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordInfo;
import com.wons.wordmanager3ver.game.GameCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PutSpellBlankViewModel extends ViewModel {
    private MyDao dao = MainViewModel.dao;
    public MutableLiveData<PutSpellAtBlankGame> gameLiveData;


    public void startGame(int gameCode) {

        if (this.gameLiveData == null) {
            this.gameLiveData = new MutableLiveData<>();
        }

        switch (gameCode) {

            case GameCode.START: {

                if (gameLiveData.getValue() != null) {
                    return;
                }

                Word word = getRandomWord();
                this.gameLiveData.setValue(new PutSpellAtBlankGame(word, getWordInfo(word)));
                break;
            }

            case GameCode.RESTART_OTHER_WORD: {
                Word word = getRandomWord();
                this.gameLiveData.setValue(new PutSpellAtBlankGame(word, getWordInfo(word)));
                break;
            }

            case GameCode.RESTART_SAME_WORD: {
                PutSpellAtBlankGame game = this.gameLiveData.getValue();
                game.delete.resetAllSpell();
                break;
            }

        }
    }

    public void onclickBack() {
        PutSpellAtBlankGame game = this.gameLiveData.getValue();
        game.delete.deleteOneSpell();
        this.gameLiveData.setValue(game);
    }

    public void onclickReset() {
        startGame(GameCode.RESTART_SAME_WORD);
    }

    public PutSpellGameData getGameData() {
        PutSpellAtBlankGame game = this.gameLiveData.getValue();
        PutSpellGameData data = game.getDATA.getGameData();
        return data;
    }



    private Word getRandomWord() {
        TodayWordList[] todayWordLists = dao.getAllTodayListByLanguageCode(
                MainViewModel.getUserInfo().getLanguageCode()
        );

        ArrayList<Word> wordArr = new ArrayList<>();

        for (TodayWordList t : todayWordLists) {
            wordArr.addAll(new ArrayList<>(Arrays.asList(dao.getAllWordByLanguageByListCode(
                    t.getListLanguageCode(),
                    t.getListCode()
            ))));
        }

        int ranDomNum = new Random().nextInt(wordArr.size());

        return wordArr.get(ranDomNum);
    }

    private WordInfo getWordInfo(Word word) {
        return dao.getWordInfo(word.getWordTitle().trim().toUpperCase(), word.getLanguageCode());
    }
}
