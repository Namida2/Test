package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.adapters.NoteListViewAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.example.test.Notes.notesForFAdapter;


public class NotesActivity extends AppCompatActivity {

    public int SIZE;
    public static NoteListViewAdapter notesRecViewAdapter;
    public static ListView container;
    private static List<String> categoryNames = new ArrayList<String>();
    private static List<String> notes = new ArrayList<String>();
    private static List<String> subjects = new ArrayList<String>();

    private String groupName;
    private String subjectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ImageButton btn = (ImageButton) findViewById(R.id.back);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        groupName = intent.getStringExtra("groupName");
        subjectName = intent.getStringExtra("subjectName");

        subjects =  new ArrayList<String>();
        categoryNames = new ArrayList<String>();
        notes = new ArrayList<String>();
        SIZE = 0;

        String substring;
        String str = subjectName;
        while(str.indexOf("\n") != -1)
        {
            substring = str.substring(0,str.indexOf("\n"));
            subjects.add(str.substring(0,str.indexOf("\n")));
            str = str.replaceFirst(substring,"");
            str = str.replaceFirst(Character.toString(str.charAt(str.indexOf('\n'))),"");
            SIZE++;
        }
        subjects.add(str);
        SIZE++;

        for(int i = 0; i < subjects.size(); i++)
        {
            if (subjects.get(i).lastIndexOf(".") != -1 )
            {
                str = subjects.get(i);
                substring = subjects.get(i).substring(0, subjects.get(i).lastIndexOf("."));
                subjects.remove(subjects.get(i));
                subjects.add(i, str.replaceFirst(substring + ". ", ""));
               // subjects.add(i, subjects.get(i).replace(subjects.get(i), str.replace(substring + ". ", "")));
            }
        }

        for(int i = 0; i < subjects.size(); i ++)
        {
            String FILE_NAME = groupName + "\n" + subjects.get(i);
            File file = new File(getApplicationContext().getFilesDir(), FILE_NAME);
            if(file.exists())
            {
                FileInputStream fis;
                try {
                    fis = openFileInput(FILE_NAME);
                    byte[] bytes = new byte[fis.available()];
                    fis.read(bytes);
                    str = new String (bytes);
                }
                catch(IOException ex) {
                }
                substring = str.substring(0,str.indexOf("\n"));
                categoryNames.add(str.substring(0,str.indexOf("\n")));
                str = str.replaceFirst(substring,"");
                notes.add(str.replaceFirst(Character.toString(str.charAt(str.indexOf('\n'))),""));
            }
            else
            {
                categoryNames.add(subjects.get(i));
                notes.add("");
            }
        }

        for(int i = 0; i < categoryNames.size(); i++)
        {
            String name = categoryNames.get(i);
            for(int j = 0; j < categoryNames.size(); j++)
            {
                if(j != i && categoryNames.get(j).equals(name))
                {
                    categoryNames.remove(j);
                    j--;
                }
            }
        }


       // StaggeredGridLayoutManager _sGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //container.setLayoutManager(_sGridLayoutManager);
        container = (ListView) findViewById(R.id.listViewForNotes);
        //container.setLayoutManager(_sGridLayoutManager);
        notesRecViewAdapter = new NoteListViewAdapter(this, categoryNames, notes);
        container.setAdapter(notesRecViewAdapter);

        container.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                int position = (int)(((ImageButton)view.findViewById(R.id.share)).getTag());
                notesRecViewAdapter.notes.set(position, ((EditText)view.findViewById(R.id.note)).getText().toString());
                saveData();
            }
        });
    }

    @Override
    protected void onPause() {
        saveData();
        super.onPause();
        FileOutputStream fos;
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

    public void saveData()
    {
        String category;
        String note;
        FileOutputStream fos;
        String substring;
        for(int i = 0; i < container.getChildCount(); i++)
        {
            View view = container.getChildAt(i);
            category = ((TextView) view.findViewById(R.id.category)).getText().toString();
            note = ((EditText) view.findViewById(R.id.note)).getText().toString();

            String FILE_NAME = groupName + "\n" + subjects.get(i);

            File file = new File(getApplicationContext().getFilesDir(), FILE_NAME);
            if(!note.equals("") && !note.isEmpty()) {
                if (!Data.pathOfExistFiles.contains(groupName + "\n" + subjects.get(i) + "$")) {
                    Data.pathOfExistFiles.add(groupName + "\n" + subjects.get(i) + "$");
                }
                note = category + "\n" + note;
                try {
                    fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                    fos.write(note.getBytes());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                if (Data.pathOfExistFiles.contains(groupName + "\n" + subjects.get(i) + "$")) {
                    Data.pathOfExistFiles.remove(groupName + "\n" + subjects.get(i) + "$");
                    try {
                        fos = openFileOutput(Data.FILE_NOTES_NAME, MODE_PRIVATE);
                        if (Data.pathOfExistFiles.size()!=0)
                            for(int j = 0; j < Data.pathOfExistFiles.size(); j++)
                            {
                                fos.write(Data.pathOfExistFiles.get(i).getBytes());
                            }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    File f = new File(getApplicationContext().getFilesDir(), (groupName + "\n" + subjects.get(i)));
                    f.delete();
                    notesForFAdapter.deleteItem(groupName, subjects.get(i));
                }

            }
        }
    }
}