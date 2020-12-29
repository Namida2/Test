package com.example.test.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>
{
    private Context context;
    private List<String> listSubjects = new ArrayList<String>();

    public ItemAdapter(Context context, List<String> listSubjects)
    {
        this.context = context;
        this.listSubjects = listSubjects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.card_item, parent,false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameOfSubject.setText(listSubjects.get(position));
    }

    @Override
    public int getItemCount() {
        return listSubjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        Button nameOfSubject;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameOfSubject = itemView.findViewById(R.id.nameSubject);
        }
    }
}
