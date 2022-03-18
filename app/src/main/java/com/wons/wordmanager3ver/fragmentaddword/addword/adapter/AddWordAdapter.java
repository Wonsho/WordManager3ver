package com.wons.wordmanager3ver.fragmentaddword.addword.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.datavalues.EnumLanguage;
import com.wons.wordmanager3ver.datavalues.Word;
import com.wons.wordmanager3ver.datavalues.WordInfo;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class AddWordAdapter extends BaseAdapter {
    private ArrayList<Word> words;
    private HashMap<String, WordInfo> infoMap;
    private String language;

    public AddWordAdapter() {
        this.words = new ArrayList<>();
        this.infoMap = new HashMap<>();
    }

    @Override
    public int getCount() {
        return words.size();
    }

    @Override
    public Word getItem(int i) {
        return words.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_word_add_list, viewGroup, false);
        }

        TextView tv_language = view.findViewById(R.id.tv_language);
        TextView tv_wordTitle = view.findViewById(R.id.tv_wordTitle);
        TextView tv_wordKorean = view.findViewById(R.id.tv_wordKorean);
        TextView tv_percentage = view.findViewById(R.id.tv_percentage);
        ImageView btn_rename = view.findViewById(R.id.btn_rename);
        ImageView btn_sound = view.findViewById(R.id.btn_sound);
        ImageView btn_memo = view.findViewById(R.id.btn_memo);
        ImageView btn_delete = view.findViewById(R.id.btn_delete);

        if (language == null || language.isEmpty()) {
            for (EnumLanguage enumLanguage : EnumLanguage.values()) {
                if (enumLanguage.languageCodeInt == words.get(i).getLanguageCode()) {
                    this.language = enumLanguage.getLanguage();
                }
            }
        }

        int percentage = infoMap.get(words.get(i).getWordTitle()).getCorrectPercentage();
        tv_language.setText(language);
        tv_wordTitle.setText(words.get(i).getWordTitle());
        tv_wordKorean.setText(infoMap.get(words.get(i).getWordTitle()).wordKorean);
        if (percentage == 0) {
            tv_percentage.setText("데이터 없음");
        } else {
            tv_percentage.setText(String.valueOf(percentage));
        }

        btn_delete.setOnClickListener(v -> {

        });

        btn_memo.setOnClickListener(v -> {

        });

        btn_rename.setOnClickListener(v -> {

        });

        btn_sound.setOnClickListener(v -> {

        });

        return view;
    }

    public void setWords(ArrayList<Word> words, HashMap<String, WordInfo> hashMap) {
        this.words = words;
        this.infoMap = hashMap;
    }
}
