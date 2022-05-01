package com.wons.wordmanager3ver.game.fourcard;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.game.GameCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class FourCardViewModel extends ViewModel {
    private MyDao dao = MainViewModel.dao;
    private MutableLiveData<ForCard> gameLiveData;
    private MutableLiveData<Integer> nowIndex;
    private MutableLiveData<Integer> life;
    private MutableLiveData<ArrayList<Boolean>> booleanMutableLiveData;
    public AccessGameData gameData = new AccessGameData();

    class AccessGameData {

        public ArrayList<String> getGameCardData() {
           return gameLiveData.getValue().forCardMap.get(nowIndex.getValue());
        }

        public String getNowIndex() {
            return String.valueOf(nowIndex.getValue() + 1);
        }

        public String getLife() {
            return String.valueOf(life.getValue());
        }

        public ArrayList<Boolean> getCardBoolean() {
            return booleanMutableLiveData.getValue();
        }

        public String getWordKorean() {
            String originWordTitle = gameLiveData.getValue().wordArr.get(nowIndex.getValue());
            return dao.getWordInfo(
                    originWordTitle.toUpperCase(),
                    MainViewModel.getUserInfo().getLanguageCode()
            ).wordKorean;
        }

        public String getWordArrSize() {
            return String.valueOf(gameLiveData.getValue().wordArr.size());
        }
    }

    public void startGame(int gameCode) {

        class Utils {
            MyDao myDao;

            Utils(MyDao dao) {
                this.myDao = dao;
            }

            ArrayList<String> getRandomWordToString() {
                return mixWordArrToString(getTodayWord());
            }

            ArrayList<String> mixWordArrToString(ArrayList<Word> words) {
                ArrayList<Word> words1 = words;
                ArrayList<String> mixedArr = new ArrayList<>();

                while (true) {

                    if (words1.size() == 0) {
                        break;
                    }

                    int randomN = new Random().nextInt(words1.size());
                    mixedArr.add(words1.get(randomN).getWordTitle());
                    words1.remove(randomN);
                }

                return mixedArr;
            }

            ArrayList<Word> getTodayWord() {
                TodayWordList[] todayWordLists = dao.getAllTodayListByLanguageCode(
                        MainViewModel.getUserInfo().getLanguageCode()
                );
                ArrayList<Word> words = new ArrayList<>();

                for (TodayWordList t : todayWordLists) {
                    words.addAll(new ArrayList<>(
                            Arrays.asList(dao.getAllWordByLanguageByListCode(
                                    t.getListLanguageCode(),
                                    t.getListCode()
                            ))
                    ));
                }

                return words;
            }

            int getLife() {
                int wordQuantity = gameLiveData.getValue().wordArr.size();
                double life = (double) wordQuantity * 0.6;
                Log.e("FourCard", "Life : " + String.valueOf(life));
                return Integer.parseInt(String.format("%.0f", life));
            }
        }

        Utils utils = new Utils(dao);

        if (gameLiveData == null) {
            gameLiveData = new MutableLiveData<>();
        }

        if (nowIndex == null) {
            nowIndex = new MutableLiveData<>();
        }

        if (life == null) {
            life = new MutableLiveData<>();
        }

        if (booleanMutableLiveData == null) {
            booleanMutableLiveData = new MutableLiveData<>();
        }

        switch (gameCode) {

            case GameCode.START: {

                if (gameLiveData.getValue() != null) {
                    break;
                }

                gameLiveData.setValue(new ForCard(utils.getRandomWordToString()));
                nowIndex.setValue(0);
                life.setValue(utils.getLife());
                booleanMutableLiveData.setValue(getBooleanArr());
                break;
            }

            case GameCode.GAME_RESTART: {
                gameLiveData.setValue(new ForCard(utils.getRandomWordToString()));
                nowIndex.setValue(0);
                life.setValue(utils.getLife());
                booleanMutableLiveData.setValue(getBooleanArr());
                break;
            }
        }
    }

    private ArrayList<Boolean> getBooleanArr() {
        ArrayList<Boolean> bArr = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            bArr.add(true);
        }

        return bArr;
    }

    public int checkWordCard(String selectedWordTitle, int indexOfCard) {

        String originWord = gameLiveData.getValue().wordArr.get(nowIndex.getValue());

        if (originWord.equals(selectedWordTitle)) {
            if(nowIndex.getValue() + 1 == gameLiveData.getValue().wordArr.size()) {
                return GameCode.GAME_FINISH;
            } else {
                nowIndex.setValue(nowIndex.getValue() + 1);
                booleanMutableLiveData.setValue(getBooleanArr());
                return GameCode.PASS;
            }
        } else {
            life.setValue(life.getValue() - 1);

            if(life.getValue() == 0) {
                return GameCode.GAME_OVER;
            } else {
                ArrayList<Boolean> bArr = booleanMutableLiveData.getValue();
                bArr.set(indexOfCard, false);
                booleanMutableLiveData.setValue(bArr);
                return -1;
            }
        }
    }

}
