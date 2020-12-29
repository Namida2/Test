package com.example.test.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;

import java.util.List;

public class NotesRecViewAdapter extends RecyclerView.Adapter<NotesRecViewAdapter.ViewHolder> {

    private Context context;
    public List<String> categoryNames;
    public List<String> notes;

    public NotesRecViewAdapter(Context context, List<String> categoryNames,  List<String> notes)
    {
        this.context = context;
        this.categoryNames = categoryNames;
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context); /////////////////////////////////////////
        View instance = inflater.inflate(R.layout.note, parent, false);
        return new ViewHolder(instance);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryName.setText(categoryNames.get(position));
        holder.note.setText(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView categoryName;
        EditText note;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category);
            note = itemView.findViewById(R.id.note);

        }
    }
}
