package com.example.test.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;

import java.util.List;

import static com.example.test.NotesActivity.container;

public class NoteListViewAdapter extends BaseAdapter {

    private final Context context;
    private final List<String> categoryNames;
    public static List<String> notes;
    public static int test;
    public NoteListViewAdapter(Context context, List<String> categoryNames, List<String> notes)
    {
        this.context = context;
        this.categoryNames = categoryNames;
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return categoryNames.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryNames.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        TextView categoryName;
        EditText note;
        ImageButton share;
        test = position;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.note, viewGroup, false);
        share = view.findViewById(R.id.share);
        share.setTag(position);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewParent ib = view.getParent();
                String name;
                String data;
                View item = container.getChildAt((int)view.getTag());
                name = ((TextView) item.findViewById(R.id.category)).getText().toString();
                data = ((EditText) item.findViewById(R.id.note)).getText().toString();
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Timetable");
                    String shareMessage= "Let me recommend you this application";
                    shareMessage = name + ":\n"+ data;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    context.startActivity(Intent.createChooser(shareIntent, "Share"));
                } catch(Exception e) {

                }
            }
        });
        categoryName = view.findViewById(R.id.category);
        note = view.findViewById(R.id.note);
        categoryName.setText(categoryNames.get(position));
        note.setText(notes.get(position));

        return view;
    }
}
