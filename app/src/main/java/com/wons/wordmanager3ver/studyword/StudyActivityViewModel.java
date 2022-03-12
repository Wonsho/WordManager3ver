package com.wons.wordmanager3ver.studyword;

import androidx.lifecycle.ViewModel;

import com.wons.wordmanager3ver.MainViewModel;
import com.wons.wordmanager3ver.MyDao;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordInfo;
import com.wons.wordmanager3ver.datavalues.WordList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class StudyActivityViewModel extends ViewModel {
    MyDao myDao = MainViewModel.dao;
    private ArrayList<WordList> wordLists;
    private HashMap<Integer, ArrayList<Word>> wordMap;
    private HashMap<String, WordInfo> wordInfo;
    //todo 리스트 코드 == key 값

    public void setWordList() {
        //todo 메인 라이브 데이터에서 공부할 리스트의 값을 ListWord형으로 가져옴
        ArrayList<WordList> todayList = new ArrayList<>(Arrays.asList(new WordList[]{
                new WordList("Sample1", 1, "2022-03-03"),
                new WordList("Sample2", 2, "2022-03-03"),
                new WordList("Sample3", 3, "2022-03-03")
        }));
        this.wordLists = todayList;
    }

    public void setWordByList() {
        //todo 해당 리스트의 코드로 다오에서 해당 값을 가져와 넣기


        //todo 리스트 코드를 하나씩 가져와 해쉬맵 파싱하기
        //sample Data
        ArrayList<Word> words = new ArrayList<>(Arrays.asList(new Word[] {
                new Word(0, "Apple", 1),
                new Word(0, "Apple2", 1),
                new Word(0, "Apple3", 2),
                new Word(0, "Apple4", 2),
                new Word(0, "Apple5", 3),
                new Word(0, "Apple6", 3),
                new Word(0, "Apple7", 3),
                new Word(0, "Apple8", 4),
                new Word(0, "Apple9", 4),
                new Word(0, "Apple10", 4)
        }));

        HashMap<Integer, ArrayList<Word>> wordMap = new HashMap<>();

        for(WordList list : wordLists) {
            //todo 다오에서 쿼리
            ArrayList<Word> arrayList = new ArrayList<>();
            for(Word word : words) {
                if(word.getWordListCodeInt() == list.listCodeInt) {
                    arrayList.add(word);
                }
            }
            wordMap.put(list.getListCodeInt(), arrayList);
        }
        this.wordMap = wordMap;
    }

    public void setWordInfo() {
    }


    public ArrayList<WordList> getWordLists() {
        return wordLists;
    }

    public HashMap<String, WordInfo> getWordInfo() {
        return wordInfo;
    }

    public HashMap<Integer, ArrayList<Word>> getWordMap() {
        return wordMap;
    }
}
