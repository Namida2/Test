package com.example.test;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.test.adapters.ListViewAdapter;
import com.example.test.adapters.ListViewForSpinnerAdapter;

import java.util.ArrayList;
import java.util.List;


public class ExTimetables extends Fragment {

    static List<String> list = new ArrayList<String>();
    static String[][] groupList= {{"09.02.03 Программирование в компьютерных системах", "КС-171", "КС-172", "КС-181", "КС-182", "КС-191", "КС-192", "КС-201", "КС-202", "КС-203" },
            { "09.02.05 Прикладная информатика (по отраслям)","ПИ-171", "ПИ-172", "ПИ-181", "ПИ-191", "ПИ-201"},
            {"09.02.04 Информационные системы (по отраслям)","ИС-181", "ИС-182", "ИС-191", "ИС-201", "ИС-202"},
            {"40.02.01 Право и организация социального обеспечения","ПС-181", "ПС-182", "ПС-191", "ПС-192", "ПС-193", "ПС-194", "ПС-195", "ПС-196", "ПС-201", "ПС-202", "ПС-203", "ПС-204", "ПС-205", "ПС-206"},
            { "38.02.04 Коммерция (по отраслям)","К-181", "К-191", "К-201"},
            {"Товароведение???","Т-181"},
            {"38.02.01 Экономика и бухгалтерский учет (по отраслям)","БУ-181", "БУ-191", "БУ-192", "БУ-201", "БУ-202"}};

    List<ArrayAdapter<String>> listAdapters = new ArrayList<ArrayAdapter<String>>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ex_timetables, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fillAdapters(getActivity());
        fillGroupNamesList();
        ListView listView;
        listView = (ListView) getActivity().findViewById(R.id.listViewForSpinners);
        listView.setAdapter(new ListViewForSpinnerAdapter(getActivity(), list, listAdapters, fragmentManager));
    }
    public void fillAdapters(Context context)
    {
        for (int i = 0; i < groupList.length; i++)
        {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.text_for_spinner, groupList[i]);
            arrayAdapter.setDropDownViewResource(R.layout.text_for_spinner);
            listAdapters.add(arrayAdapter);
        }
    }

    public void fillGroupNamesList()
    {
        list.add("09.02.03 Программирование в компьютерных системах");
        list.add("09.02.04 Информационные системы (по отраслям)");
        list.add("09.02.05 Прикладная информатика (по отраслям)");
        list.add("38.02.01 Экономика и бухгалтерский учет (по отраслям)");
        list.add("38.02.04 Коммерция (по отраслям)");
        list.add( "40.02.01 Право и организация социального обеспечения");
        list.add( "Товароведение???");
    }
}