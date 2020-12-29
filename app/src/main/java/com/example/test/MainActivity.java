package com.example.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends FragmentActivity {

    public static Fragment lastSelectFragment = null;
    public static Fragment lastSelectFragmentBack = null;
    public static ChipNavigationBar navigation;
    public static FrameLayout front;
    public static FrameLayout back;
    public static String existTimetable ="0";               //АККУРАТНЕЕ СО STATIC
    public static String FILE_NAME = "settings.txt";
    public static String firstVisit = "0КС-181";
    public static String settingsDataForWriting = "0КС-181";
    public static androidx.appcompat.widget.Toolbar toolbar;

    private static TextView toollBarTitle;
    public static int REQUEST_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        front = (FrameLayout)findViewById(R.id.fragment);
        back = (FrameLayout)findViewById(R.id.fragmentForTimetable);
        //back.setVisibility(View.GONE);
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.myTool);
        navigation = (ChipNavigationBar) findViewById(R.id.navigation);
        toollBarTitle = (TextView) findViewById(R.id.toolbar_title);


        File file = new File(getApplicationContext().getFilesDir(),FILE_NAME);
        if(file.exists())
        {
            FileInputStream fis;
            try {
                fis = openFileInput(FILE_NAME);
                byte[] bytes = new byte[fis.available()];
                fis.read(bytes);
                settingsDataForWriting = new String (bytes);
            }
            catch(IOException ex) {
            }

            existTimetable = Character.toString(settingsDataForWriting.charAt(0));
            Data.groupName = "";
            for (int i = 1; i < settingsDataForWriting.length(); i++) {
                Data.groupName += Character.toString(settingsDataForWriting.charAt(i));
            }
            toollBarTitle.setText(Data.groupName);
        }
        else{
            FileOutputStream fos;
            try {
                fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                fos.write(firstVisit.getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        file = new File(getApplicationContext().getFilesDir(),Data.FILE_NOTES_NAME);
        if(file.exists())
        {
            String str = "";
            String delStr = "";
            FileInputStream fis;
            try {
                fis = openFileInput(Data.FILE_NOTES_NAME);
                byte[] bytes = new byte[fis.available()];
                fis.read(bytes);
                 str = new String (bytes);
            }
            catch(IOException ex) {
            }
            while(str.indexOf("$") != -1){

                Data.pathOfExistFiles.add(0,str.substring(0,str.indexOf("$")+1));
                str = str.replace((str.substring(0,str.indexOf("$")+1)), "");
            }
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        if(existTimetable.equals("0"))
        {
            navigation.setVisibility(View.GONE);
            lastSelectFragment = new EditTimetable();
            fragmentManager.beginTransaction().replace(R.id.fragment, lastSelectFragment).commit();
        }
        else
        {
            lastSelectFragmentBack = new Timetable();
            fragmentManager.beginTransaction().add(R.id.fragmentForTimetable, lastSelectFragmentBack).commit();
        }

        navigation.setItemSelected(R.id.timetable, true);
        navigation.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int itemId) {
                Fragment fragment1;
                FragmentManager fragmentManager = getSupportFragmentManager();
                switch (itemId) {
                    case R.id.timetable:
                        if (lastSelectFragment != null) {
                            toollBarTitle.setText(Data.groupName);
                            back.setVisibility(View.VISIBLE);
                            fragmentManager.beginTransaction().remove(lastSelectFragment).commit();
                        }
                        break;
                    case R.id.teachers:
                        front.setVisibility(View.VISIBLE);
                        back.setVisibility(View.GONE);
                        toollBarTitle.setText("Преподаватели");
                        toolbar.hideOverflowMenu();
                        fragment1 = new Teachers();
                        fragmentManager.beginTransaction().
                                setCustomAnimations(R.anim.fragment_open_enter, R.anim.fragment_close_exit)
                                .replace(R.id.fragment, fragment1).commit();
                        lastSelectFragment = fragment1;
                        break;
                    case R.id.notes:
                        front.setVisibility(View.VISIBLE);
                        back.setVisibility(View.GONE);
                        toollBarTitle.setText("Заметки");
                        toolbar.hideOverflowMenu();
                        fragment1 = new Notes();
                        fragmentManager.beginTransaction().
                                setCustomAnimations(R.anim.fragment_open_enter, R.anim.fragment_close_exit)
                                .replace(R.id.fragment, fragment1).commit();
                        lastSelectFragment = fragment1;
                        break;
                }
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                back.setVisibility(View.GONE);
                navigation.setVisibility(View.GONE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                lastSelectFragment = new EditTimetable();
                toollBarTitle.setText("Главная");
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.left_anim, R.anim.fragment_close_exit)
                        .replace(R.id.fragment, lastSelectFragment).commit();
                return true;
            }
        });
    }
   
    @Override
    protected void onPause() {
        super.onPause();
        FileOutputStream fos;

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(settingsDataForWriting.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fos = openFileOutput(Data.FILE_NOTES_NAME, MODE_PRIVATE);
            if (Data.pathOfExistFiles.size()!=0)
                for(int i = 0; i < Data.pathOfExistFiles.size(); i++)
                {
                    fos.write(Data.pathOfExistFiles.get(i).getBytes());
                }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        back.setVisibility(View.VISIBLE);
        navigation.setVisibility(View.VISIBLE);
        navigation.setItemSelected(R.id.timetable, true);
        toollBarTitle.setText(Data.groupName);
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .remove(lastSelectFragment)
                .replace(R.id.fragmentForTimetable, new Timetable())
                .commit();
    }

    public void onButtonClick(View view)
    {
        Button btn = (Button) view;
        Data.nameSubject = btn.getText().toString();
        Intent intent = new Intent();

        intent.putExtra("groupName",Data.groupName);
        intent.putExtra("subjectName",Data.nameSubject);

        intent.setClass(this, NotesActivity.class);
        startActivity(intent);
    }
}
