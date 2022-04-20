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
    public ChangeData approachData;


    public void gameStart(int gameCode) {
        if(approachData == null) {
            approachData = new ChangeData();
        }
        if (gameLiveData == null) {
            gameLiveData = new MutableLiveData<>();
        }

        switch (gameCode) {

            case GameCode.START: {
                if (gameLiveData.getValue() == null) {
                    Word word = getRandomWord();
                    gameLiveData.setValue(new PutSpellAtBlankGame(word.getWordTitle(), getWordKorean(word)));
                }
                break;
            }

            case GameCode.RESTART_OTHER_WORD: {
                Word word = getRandomWord();
                gameLiveData.setValue(new PutSpellAtBlankGame(word.getWordTitle(), getWordKorean(word)));
                break;
            }

            case GameCode.RESTART_SAME_WORD: {
                PutSpellAtBlankGame game = gameLiveData.getValue();
                game.data.changeData.inputReset();
                this.gameLiveData.setValue(game);
                break;
            }
        }
    }

    class ChangeData {

        public int inputSpell(String spell) {
            PutSpellAtBlankGame game = gameLiveData.getValue();
            int resultCode = game.data.changeData.inputSpell(spell);
            return resultCode;
        }

        public void inputBack() {
            PutSpellAtBlankGame game = gameLiveData.getValue();
            game.data.changeData.inputBack();
            gameLiveData.setValue(game);
        }

        public ArrayList<String> getShowWord() {
            return gameLiveData.getValue().data.getShowWordArr();
        }

        public ArrayList<String> getSpellMenu() {
            return gameLiveData.getValue().data.getSpellMenuArr();
        }

        public String getWordKorean() {
            return gameLiveData.getValue().originWordKorean;
        }

    }

    private Word getRandomWord() {

        class MakeWordList {
            private ArrayList<Word> getTodayWordArr() {
                TodayWordList[] todayWordLists = dao.getAllTodayListByLanguageCode(
                        MainViewModel.getUserInfo().getLanguageCode()
                );
                ArrayList<Word> words = new ArrayList<>();

                for (TodayWordList t : todayWordLists) {
                    words.addAll(new ArrayList<>(Arrays.asList(dao.getAllWordByLanguageByListCode(
                            t.getListLanguageCode(),
                            t.getListCode()
                    ))));
                }

                return words;
            }
        }

        ArrayList<Word> todayWord = new MakeWordList().getTodayWordArr();

        int randomNum = new Random().nextInt(todayWord.size());

        return todayWord.get(randomNum);
    }

    private String getWordKorean(Word word) {
        return dao.getWordInfo(
                word.getWordTitle().trim().toUpperCase(),
                word.getLanguageCode()
        ).wordKorean;
    }


}
