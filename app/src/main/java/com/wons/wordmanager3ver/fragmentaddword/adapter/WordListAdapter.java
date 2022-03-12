package com.wons.wordmanager3ver.fragmentaddword.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.datavalues.EnumGrade;
import com.wons.wordmanager3ver.datavalues.WordList;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class WordListAdapter extends BaseAdapter {
    private ArrayList<WordList> lists;

    public WordListAdapter() {
        this.lists = new ArrayList<>();
    }
    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.list_word_list_add_wordlist, viewGroup, false);
        }

        TextView tv_wordCount = view.findViewById(R.id.tv_wordCount);
        TextView tv_listTitle = view.findViewById(R.id.tv_listTitle);
        TextView tv_listGrade = view.findViewById(R.id.tv_listGrade);

        tv_wordCount.setText(String.valueOf(lists.get(i).getWordCountInt()));
        tv_listTitle.setText(lists.get(i).listName);
        String listGrade = EnumGrade.A.getGradeToString(lists.get(i).getListGradeInt());
        if(lists.get(i).getListGradeInt() == 0) {
            tv_listGrade.setText("데이터 없음");
        } else {
            tv_listGrade.setText(listGrade);
        }
        return view;
    }

    public void setLists(ArrayList<WordList> lists) {
        this.lists = lists;
    }
}
