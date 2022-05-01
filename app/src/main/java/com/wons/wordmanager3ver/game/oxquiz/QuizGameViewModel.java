package com.wons.wordmanager3ver.game.oxquiz;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.datavalues.TodayWordList;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class QuizGameViewModel extends ViewModel {
    private MyDao dao = MainViewModel.dao;
    public MutableLiveData<ArrayList<QuizObject>> quizLiveData;
    public MutableLiveData<Integer> liveIndex;

    public QuizObject getNowQuiz() {
        return this.quizLiveData.getValue().get(this.liveIndex.getValue());
    }

    public int getWordQuantity() {
        return quizLiveData.getValue().size();
    }

    public int getNowCount() {
        return liveIndex.getValue();
    }

    public void initData() {

        if (this.quizLiveData == null) {
            this.quizLiveData = new MutableLiveData<>();
            this.quizLiveData.setValue(makeQuizObject());
        }

        if(liveIndex == null) {
            liveIndex = new MutableLiveData<>();
            liveIndex.setValue(0);
        }

    }

    private ArrayList<QuizObject> makeQuizObject() {
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

        ArrayList<QuizObject> quizObjects = new ArrayList<>();

        class MakeObject {

            private QuizObject makeWrongObject(String str) {
                int random = new Random().nextInt(words.size());
                Word word = words.get(random);
                WordInfo wordInfo = dao.getWordInfo(
                        word.getWordTitle().trim().toUpperCase(),
                        word.getLanguageCode()
                        );
                return new QuizObject(str, wordInfo.wordKorean);
            }

            private QuizObject makeCorrectObject(String str) {
                WordInfo wordInfo = dao.getWordInfo(str.trim().toUpperCase(), MainViewModel.getUserInfo().getLanguageCode());
                return new QuizObject(str, wordInfo.wordKorean);
            }

        }

        for (Word w : words) {
            int randomNum = new Random().nextInt(2);

            if (randomNum == 0) {
                quizObjects.add(new MakeObject().makeWrongObject(w.getWordTitle()));
            } else {
                quizObjects.add(new MakeObject().makeCorrectObject(w.getWordTitle()));
            }

        }
        return quizObjects;
    }

    public boolean check(boolean check) {
        //todo 먼저 나와있는걸 체크 한다음 check 와 check 가 맞는지 테스트후 리턴
        boolean checkResult = check();
        if(checkResult == check) {
            setObject(true);
            return true;
        } else {
            setObject(false);
            return false;
        }
    }

    private boolean check() {
        QuizObject word = this.quizLiveData.getValue().get(this.liveIndex.getValue());
        String wordTitle = word.word_title;
        String wordKorean = word.word_korean;

        WordInfo wordInfo = dao.getWordInfo(
                wordTitle.trim().toUpperCase(),
                MainViewModel.getUserInfo().getLanguageCode()
        );

        if(wordKorean.trim().equals(wordInfo.wordKorean.trim())) {
            return true;
        } else {
            return false;
        }
    }

    public void setNextIndex() {
        int index = this.liveIndex.getValue() + 1;
        this.liveIndex.setValue(index);
    }

    public void setObject(boolean b) {
       ArrayList<QuizObject> objects = this.quizLiveData.getValue();
       QuizObject object = objects.get(this.liveIndex.getValue());
       object.check = b;
       objects.set(this.liveIndex.getValue(), object);
       this.quizLiveData.setValue(objects);
    }

    public String getWordKorean() {
        WordInfo wordInfo = dao.getWordInfo(
                this.quizLiveData.getValue()
                .get(this.liveIndex.getValue())
                .word_title.trim().toUpperCase(),
                MainViewModel.getUserInfo().getLanguageCode()
        );
        return wordInfo.wordKorean;
    }

    public String getWordTitle() {
        String wordTitle = this.quizLiveData.getValue()
                            .get(this.liveIndex.getValue())
                            .word_title;

        return wordTitle;
    }


    public int getCorrectCount() {
        int count = 0;
        ArrayList<QuizObject> objects = this.quizLiveData.getValue();

        for(QuizObject o : objects) {
            if(o.check) {
                count++;
            }
        }

        return count;
    }
}
