package com.example.test;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

class DBHelper extends SQLiteOpenHelper {
    private static String DB_PATH; // полный путь к базе данных
    private static String DB_NAME = "Timetable.db";
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "Timetable"; // название таблицы в бд
    private Context myContext;

    static List<PairGroupPosition> listGroupPosition = new ArrayList<PairGroupPosition>();

    DBHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA);
        this.myContext=context;
        DB_PATH =context.getFilesDir().getPath() + DB_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
    }

    void create_db(){
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            File file = new File(DB_PATH);
            if (!file.exists()) {
                this.getReadableDatabase();
                //получаем локальную бд как поток
                myInput = myContext.getAssets().open(DB_NAME);
                // Путь к новой бд
                String outFileName = DB_PATH;
                // Открываем пустую бд
                myOutput = new FileOutputStream(outFileName);
                // побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
        }
        catch(IOException ex){
            Log.d("DatabaseHelper", ex.getMessage());
        }
    }
    public SQLiteDatabase open()throws SQLException {

        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }


    public void fillListGroupPosition(Cursor cursor)
    {
        int groupName = cursor.getColumnIndex("GroupName");
        int position = 0;
        if(cursor.moveToFirst())
        {
            do {
                if(cursor.getString(groupName) != null)
                {
                    listGroupPosition.add(new PairGroupPosition(cursor.getString(groupName), position));
                }
                position++;
            }while(cursor.moveToNext());
        }
    }
    public int getGroupPosition(String group)
    {
        int position = -1;

        for(int i = 0; i < listGroupPosition.size(); i++)
        {
            if(group.equals(listGroupPosition.get(i).nameGroup))
            {
                position = listGroupPosition.get(i).position;
                break;
            }
        }

        return position;
    }

}