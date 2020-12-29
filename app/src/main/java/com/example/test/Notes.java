package com.example.test;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.adapters.NotesForFAdapter;

import java.util.ArrayList;
import java.util.List;

public class Notes extends Fragment {

    private List<String> groupNames;
    private List<String> subjectNames;
    private RecyclerView recyclerView;
    public static  NotesForFAdapter notesForFAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<String> groupNames = new ArrayList<String>();
        List<String> subjectNames = new ArrayList<String>();
        String str = "";
        for(int i = 0; i < Data.pathOfExistFiles.size(); i++)
        {
            String name = Data.pathOfExistFiles.get(i);
            for(int j = 0; j < Data.pathOfExistFiles.size(); j++)
            {
                if(j != i && Data.pathOfExistFiles.get(j).equals(name))
                {
                    Data.pathOfExistFiles.remove(j);
                    j--;
                }
            }
        }
        if(Data.pathOfExistFiles.size() > 0) {
            for (int i = 0; i < Data.pathOfExistFiles.size(); i++) {
                str = Data.pathOfExistFiles.get(i);
                groupNames.add(str.substring(0, str.indexOf("\n")));
                subjectNames.add(str.substring(str.indexOf("\n") + 1, str.indexOf("$")));
            }

            recyclerView = (RecyclerView) getActivity().findViewById(R.id.container);
            notesForFAdapter = new NotesForFAdapter(getActivity(), groupNames, subjectNames);
            StaggeredGridLayoutManager staggeredGridLayoutManager
                    = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            recyclerView.setAdapter(notesForFAdapter);
        }
        else{
            TextView textView = (TextView) getActivity().findViewById(R.id.ifEmpty);
            textView.setText("Тут ничего нет.");
        }

    }

    @Override
    public void onPause() {
        super.onPause();

    }
}