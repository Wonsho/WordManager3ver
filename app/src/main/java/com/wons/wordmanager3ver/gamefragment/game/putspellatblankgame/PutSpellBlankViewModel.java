package com.wons.wordmanager3ver.gamefragment.game.putspellatblankgame;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.gamefragment.game.GameCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PutSpellBlankViewModel extends ViewModel {
    private MyDao dao = MainViewModel.dao;
    private MutableLiveData<PutSpellAtBlankGame> liveDataGame;

    public void startGame(int gameCode) {

        class PickWord {
            private Word getRandomWordIntoTodayWordList() {
                TodayWordList[] todayWordLists = dao.getAllTodayListByLanguageCode(
                        MainViewModel.getUserInfo().getLanguageCode()
                );

                ArrayList<Word> words = new ArrayList<>();

                for (TodayWordList t : todayWordLists) {
                   words.addAll(new ArrayList<>(Arrays.asList(
                           dao.getAllWordByLanguageByListCode(
                                   t.getListLanguageCode(),
                                   t.getListCode()
                           )
                   )));
                }
                Log.e("random", String.valueOf(words.size()));
                int randomNum = new Random().nextInt(words.size());
                return words.get(randomNum);
            }

            private String getWordKorean(Word word) {
                return dao.getWordInfo(word.getWordTitle().toUpperCase(), word.getLanguageCode()).wordKorean;
            }
        }

        if (liveDataGame == null) {
            liveDataGame = new MutableLiveData<>();
        }

        switch (gameCode) {
            case GameCode.START: {

                if (liveDataGame.getValue() != null) {
                    break;
                }
            }

            case GameCode.RESTART_OTHER_WORD: {
                PickWord pickWord = new PickWord();
                Word word = pickWord.getRandomWordIntoTodayWordList();
                String wordKorean = pickWord.getWordKorean(word);
                this.liveDataGame.setValue(new PutSpellAtBlankGame(
                        word.getWordTitle().trim().toUpperCase(),
                        wordKorean
                        ));
                break;
            }

            case GameCode.RESTART_SAME_WORD: {
                PutSpellAtBlankGame game = this.liveDataGame.getValue();
                game.gameData.approachData.changeData.resetData();
                this.liveDataGame.setValue(game);
                break;
            }
        }
    }

    public ArrayList<Integer> getIndexArr() {
        return this.liveDataGame.getValue().gameData.approachData.getPutIndexOfShowWordArr();
    }

    public String getGameWord() {
        return this.liveDataGame.getValue().getOriginWord();
    }

    public String getGameWordKorean() {
        return this.liveDataGame.getValue().getOriginWordKorean();
    }

    public int inputSpell(String spell) {
        PutSpellAtBlankGame game = liveDataGame.getValue();
        int resultCode = game.gameData.approachData.changeData.inputSpell(spell);
        this.liveDataGame.setValue(game);
        return resultCode;
    }

    public void deleteSpell() {
        PutSpellAtBlankGame game = this.liveDataGame.getValue();
        game.gameData.approachData.changeData.toBack();
        this.liveDataGame.setValue(game);
    }

    public void doResetGame() {
        PutSpellAtBlankGame game = this.liveDataGame.getValue();
        game.gameData.approachData.changeData.resetData();
        this.liveDataGame.setValue(game);
    }

    public PutSpellAtBlankActivity.GameResult getGameResult(PutSpellAtBlankActivity.GameResult result) {
        result.showWordArr = this.liveDataGame.getValue().gameData.approachData.getShowWordArr();
        result.spellMenuArr = this.liveDataGame.getValue().gameData.approachData.getSpellMenuArr();
        return result;
    }
}
