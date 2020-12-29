package com.example.test;

public class Lesson {

    private String nameLesson;
    private String time;
    private String room;

    Lesson(String nameLesson, String time, String room)
    {
        this.nameLesson = nameLesson;
        this.time = time;
        this.room = room;
    }

    public String getNameLesson() {
        return nameLesson;
    }
    public String getTime() {
        return time;
    }
    public String getRoom() {
        return room;
    }
}
