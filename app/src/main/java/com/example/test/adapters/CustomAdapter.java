package com.example.test.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Week;
import com.example.test.R;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder>
{

    List<Week> listItems;
    List<ListViewAdapter> listItemAdapters;
    Context context;

    CustomAdapter(Context context , List<Week> listItems, List<ListViewAdapter> listItemAdapters)
    {
        this.listItems = listItems;
        this.context = context;
        this.listItemAdapters = listItemAdapters;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater1 = LayoutInflater.from(context);
        View instance = inflater1.inflate(R.layout.card, parent, false);
        return new Holder(instance);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
       // holder.dayOfWeek.setText(listItems.get(position).first);
       // holder.containerForCardItems.setAdapter(listItemAdapters.get(position));
    }

    @Override
    public int getItemCount() {
        return  listItems.size();
    }

    public class Holder extends RecyclerView.ViewHolder
    {
        TextView dayOfWeek;
        ListView containerForCardItems;
        public Holder(@NonNull View itemView) {
            super(itemView);
            dayOfWeek = itemView.findViewById(R.id.dayOfWeek);
            containerForCardItems = itemView.findViewById(R.id.containerForCardItems);
        }
    }
}
