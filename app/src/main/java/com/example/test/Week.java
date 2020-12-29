package com.example.test;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class Week {

    private List<String> dayOfWeek = new ArrayList<String>();
    private List<Lesson> listLessons = new ArrayList<Lesson>();
    public Week(List<String> dayOfWeek, List<Lesson> listLessons)
    {
        this.dayOfWeek = dayOfWeek;
        this.listLessons = listLessons;

    }
    public List<String> getDayOfWeek() {
        return dayOfWeek;
    }
    public List<Lesson> getListLessons() {
        return listLessons;
    }
}
