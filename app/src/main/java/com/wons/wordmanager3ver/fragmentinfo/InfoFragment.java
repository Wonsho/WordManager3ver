package com.wons.wordmanager3ver.fragmentinfo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.wons.wordmanager3ver.R;
import com.wons.wordmanager3ver.databinding.FragmentInfoBinding;
import com.wons.wordmanager3ver.datavalues.FlagUserLevelData;
import com.wons.wordmanager3ver.fragmentinfo.adapter.MyWayAdapter;

import java.util.ArrayList;

public class InfoFragment extends Fragment {

    private FragmentInfoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        setWayList();
        getUserFlagData();
        setLanguageSpinner();
        return binding.getRoot();
    }

    private void setLanguageSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.language, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerLanguage.setAdapter(adapter);
    }


    private void getUserFlagData() {
        ArrayList<FlagUserLevelData> arrayList = new ArrayList<>();
        arrayList.add(new FlagUserLevelData(0, "2022-03-10", 1));
        arrayList.add(new FlagUserLevelData(0, "2022-03-11", 2));
        arrayList.add(new FlagUserLevelData(0, "2022-03-12", 3));
        arrayList.add(new FlagUserLevelData(0, "2022-03-13", 4));
        arrayList.add(new FlagUserLevelData(0, "2022-03-14", 5));
        arrayList.add(new FlagUserLevelData(0, "2022-03-15", 6));
        arrayList.add(new FlagUserLevelData(0, "2022-03-16", 7));
        ((MyWayAdapter) binding.lvMyWay.getAdapter()).setUserData(arrayList);
        setWayList();
    }

    private void setWayList() {
        if (binding.lvMyWay.getAdapter() == null) {
            binding.lvMyWay.setAdapter(new MyWayAdapter());
        }
        ((MyWayAdapter) binding.lvMyWay.getAdapter()).notifyDataSetChanged();
    }
}