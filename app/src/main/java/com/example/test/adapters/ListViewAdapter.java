package com.example.test.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.test.Lesson;
import com.example.test.R;

import java.util.List;

public class ListViewAdapter extends BaseAdapter
{
    Context context;
    List<Lesson> listItems;
    LayoutInflater inflater;
    public ListViewAdapter(Context context, List<Lesson> listItems)
    {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        Button nameSubject;
        TextView time;
        TextView room;
        if (view == null)
        {
            view = inflater.inflate(R.layout.card_item, viewGroup, false);
        }

        if (view != null)
        {
            nameSubject = view.findViewById(R.id.nameSubject);
            time = view.findViewById(R.id.timeBeginEnd);
            room = view.findViewById(R.id.numberRoom);
            nameSubject.setText(listItems.get(i).getNameLesson());
            time.setText(listItems.get(i).getTime());
            room.setText(listItems.get(i).getRoom());
        }

        return view;
    }
}
