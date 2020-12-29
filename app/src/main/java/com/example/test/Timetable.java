package com.example.test;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.adapters.ListViewAdapter;
import com.example.test.adapters.MPagerAdapter;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import java.util.ArrayList;
import java.util.List;

import static com.example.test.MainActivity.settingsDataForWriting;

public class Timetable extends Fragment {

    //static List<String> listOfAllSubjects = new ArrayList<String>();
    //static List<List<String>> listOfSubjects = new ArrayList<List<String>>();
   // static List<Week> finalList = new ArrayList<Week>();
    static List<ListViewAdapter> listItemAdapters = new ArrayList<ListViewAdapter>();
    static HorizontalInfiniteCycleViewPager viewPager;

    static DBHelper dbHelper;
    static SQLiteDatabase dbTimetable;
    static Cursor cursor;
    static List<String> daysOfWeek;
    static List<List<Lesson>> listLessons;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timetable, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

            viewPager = (HorizontalInfiniteCycleViewPager) getView().findViewById(R.id.main);
            MPagerAdapter adapter = new MPagerAdapter(getActivity(), daysOfWeek, listItemAdapters);
            viewPager.setPadding(0, 0, 0, 100);
            viewPager.setAdapter(adapter);
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                 @Override
                 public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
                 @Override
                 public void onPageSelected(int position) { }
                 @Override
                 public void onPageScrollStateChanged(int state) { }
             });
            MainActivity.navigation.setVisibility(View.VISIBLE);
            settingsDataForWriting = "1"+ Data.groupName;
        }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        daysOfWeek = new ArrayList<String>();
        listLessons = new ArrayList<List<Lesson>>();
        dbHelper = new DBHelper(context);
        listItemAdapters = new ArrayList<ListViewAdapter>();
        dbHelper.create_db();
        dbTimetable = dbHelper.open();
        cursor = dbTimetable.query("Timetable", null, null, null, null, null, null);
        // достать номер группы
        dbHelper.fillListGroupPosition(cursor);
        int position = dbHelper.getGroupPosition(Data.groupName);
        if (position != -1) {
            cursor.moveToPosition(position);
            int groupName = cursor.getColumnIndex("GroupName");
            int dayOfWeek = cursor.getColumnIndex("DayOfWeek");
            int nameLesson = cursor.getColumnIndex("NameLesson");
            int time = cursor.getColumnIndex("Time");
            int room = cursor.getColumnIndex("Room");

            List<Lesson> list = new ArrayList<Lesson>();
            do {

                if(cursor.getString(dayOfWeek) != null)
                    daysOfWeek.add(cursor.getString(dayOfWeek));
                if (daysOfWeek.size() > 1 && cursor.getString(dayOfWeek) != null
                        && !cursor.getString(dayOfWeek).equals(daysOfWeek.get(daysOfWeek.size()-2)))
                {
                    listLessons.add(list);
                    list = new ArrayList<Lesson>();
                }

                list.add(new Lesson(
                        cursor.getString(nameLesson),
                        cursor.getString(time),
                        cursor.getString(room)
                ));

            } while (cursor.moveToNext() &&( cursor.getString(groupName) == null || cursor.getString(groupName).equals(Data.groupName)));
            listLessons.add(list);
            for(int i = 0; i < daysOfWeek.size(); i++)
            {
                listItemAdapters.add(new ListViewAdapter(getActivity(), listLessons.get(i)));
            }

        }
    }

}
