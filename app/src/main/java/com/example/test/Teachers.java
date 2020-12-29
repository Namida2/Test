package com.example.test;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.adapters.AdapterForTeachers;
import com.example.test.adapters.ListViewAdapter;
import com.example.test.adapters.NotesForFAdapter;

import java.util.ArrayList;
import java.util.List;

public class Teachers extends Fragment {


    public static List<TeacherItem> teachers;
    private DBHelperForTeachers dbHelper;
    private SQLiteDatabase dbTeachers;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_teachers, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        teachers = new ArrayList<TeacherItem>();
        dbHelper = new DBHelperForTeachers(getActivity());
        dbHelper.create_db();

        dbTeachers = dbHelper.open();
        Cursor cursor;
        cursor = dbTeachers.query("Teachers", null, null, null, null, null, null);
        int position = dbHelper.getGroupPosition(Data.groupName);
        if (cursor.moveToFirst()) {

            int name = cursor.getColumnIndex("Name");
            int exp = cursor.getColumnIndex("Exp");
            int subjects = cursor.getColumnIndex("Subjects");
            int quf = cursor.getColumnIndex("QuF");
            int phone = cursor.getColumnIndex("Phone");
            int mail = cursor.getColumnIndex("Mail");

            TeacherItem teacherItem;
            do {
                teacherItem = new TeacherItem(
                        cursor.getString(name),
                        cursor.getString(exp),
                        cursor.getString(subjects),
                        cursor.getString(quf),
                        cursor.getString(phone),
                        cursor.getString(mail)
                );
                teachers.add(teacherItem);
            } while (cursor.moveToNext());

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.containerT);
        AdapterForTeachers adapter = new AdapterForTeachers(getActivity(), teachers);
        recyclerView.setAdapter(adapter);

    }
}