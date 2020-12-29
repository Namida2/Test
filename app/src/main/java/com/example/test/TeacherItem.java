package com.example.test;

import java.util.List;

public class TeacherItem {

    private String name;
    private String exp;
    private String subjects;
    private String quf;
    private String phone;
    private String mail;


    TeacherItem(String name, String exp, String subjects, String quf, String phone, String mail)
    {
        this.name = name;
        this.exp = exp;
        this.subjects = subjects;
        this.quf = quf;
        this.phone = phone;
        this.mail = mail;
    }

    public static String[] convertToArray(TeacherItem item)
    {
        String[] array = {"", "", "", "", "", ""};
        array[0] = item.getName();
        array[1] = item.getPhone();
        array[2] = item.getMail();
        array[3] = item.getExp();
        array[4] = item.getQuf();
        array[5] = item.getSubjects();

        return array;
    }


    public static TeacherItem findByName(List<TeacherItem> teacherItemList, String name)
    {
        for(int i = 0; i < teacherItemList.size(); i++)
        {
            if(teacherItemList.get(i).getName().equals(name))
                return teacherItemList.get(i);
        }

        return null;
    }

    public String getName() {
        return name;
    }

    public String getExp() {
        return exp;
    }

    public String getMail() {
        return mail;
    }

    public String getPhone() {
        return phone;
    }

    public String getQuf() {
        return quf;
    }

    public String getSubjects() {
        return subjects;
    }
}
