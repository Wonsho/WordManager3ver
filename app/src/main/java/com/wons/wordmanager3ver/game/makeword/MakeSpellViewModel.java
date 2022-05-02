package com.wons.wordmanager3ver.game.makeword;

import android.util.Log;

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

public class MakeSpellViewModel extends ViewModel {
    private MyDao dao = MainViewModel.dao;
    private MutableLiveData<MakeWordGame> liveGameData;
    public GameData gameData;

    class GameData {
        MakeWordGame game = liveGameData.getValue();

        public ArrayList<String> getSpellMenuArr() {
            return game.accessGameData.getSpellMenuArr();
        }

        public ArrayList<String> getInputArr() {
            return game.accessGameData.getInputArr();
        }

        public String getWordKorean() {
            WordInfo info = dao.getWordInfo(
                    game.accessGameData.getOriginWord(),
                    MainViewModel.getUserInfo().getLanguageCode()
            );

            return info.wordKorean;
        }

        public String getWordTitle() {
           return liveGameData.getValue().accessGameData.getOriginWord();
        }


        /*This method is check before spell text is correct or not
        * use when before setView and then get wrong spell index.
        * if return value is not -1 --> change index spell color to red
        *
        * @return
        * if spell is right -> -1
        * else --> return wrong spell's index of Arr*/
        public int check() {
            if (!game.accessGameData.check()) {
                return game.accessGameData.getWrongIndex();
            } else {
                return -1;
            }
        }

        public int getLife() {
            return game.accessGameData.getLife();
        }
    }


    public void startGame(int gameCode) {

        class Utils {

            String getRandomWord() {
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

                return wordArr.get(new Random().nextInt(wordArr.size())).getWordTitle();
            }


        }

        if (liveGameData == null) {
            liveGameData = new MutableLiveData<>();
        }

        switch (gameCode) {
            case GameCode.RESTART_SAME_WORD: {
                MakeWordGame game = liveGameData.getValue();
                game.gameAction.restart();
                liveGameData.setValue(game);
                break;
            }

            case GameCode.START: {

                if (liveGameData.getValue() != null) {
                    return;
                }

            }

            case GameCode.RESTART_OTHER_WORD: {
                liveGameData.setValue(new MakeWordGame(new Utils().getRandomWord()));
                break;
            }
        }

        if (gameData == null) {
            gameData = new GameData();
        }

    }

    // resultCode -2 is return code reason is inputArr has wrong spell
    public int onClickSpell(String spell) {
        MakeWordGame game = liveGameData.getValue();
        int resultCode = game.gameAction.onClickSpell(spell);
        liveGameData.setValue(game);
        return resultCode;
    }

    public void onClickBackBtn() {
        MakeWordGame game = liveGameData.getValue();
        game.gameAction.onClickBackBtn();
        liveGameData.setValue(game);
    }

    public void onClickReset() {
        MakeWordGame game = liveGameData.getValue();
        game.gameAction.onClickReset();
        liveGameData.setValue(game);
    }
}
