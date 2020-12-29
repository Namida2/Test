package com.example.test.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Data;
import com.example.test.NotesActivity;
import com.example.test.R;

import java.util.List;

public class NotesForFAdapter extends RecyclerView.Adapter<NotesForFAdapter.ViewHolder>{

    private final Context context;
    private List<String> groupNames;
    private List<String> subjectNames;


    public NotesForFAdapter(Context context, List<String> groupNames, List<String> subjectNames)
    {
        this.context = context;
        this.groupNames = groupNames;
        this.subjectNames = subjectNames;

    }
    public void deleteItem(String groupName, String subjectName) {

        for(int i = 0; i < groupNames.size(); i++)
        {
            if (groupNames.get(i).equals(groupName) && subjectNames.get(i).equals(subjectName))
            {
                groupNames.remove(i);
                subjectNames.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, groupNames.size());
                notifyItemRangeChanged(i, subjectNames.size());
                break;
            }
        }

       // holder.itemView.setVisibility(View.GONE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.note_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.groupName.setText(groupNames.get(position));
        holder.subjectName.setText(subjectNames.get(position));
    }

    @Override
    public int getItemCount() {
        return groupNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView groupName;
        TextView subjectName;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            groupName = itemView.findViewById(R.id.groupName);
            subjectName = itemView.findViewById(R.id.nameS);
            relativeLayout = itemView.findViewById(R.id.clickableNoteRelativeLayout);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RelativeLayout relativeLayout = (RelativeLayout) view;
                    Intent intent = new Intent(context, NotesActivity.class);
                    intent.putExtra("groupName", ((TextView)relativeLayout.findViewById(R.id.groupName)).getText());
                    intent.putExtra("subjectName", ((TextView)relativeLayout.findViewById(R.id.nameS)).getText());
                    context.startActivity(intent);

                }
            });
        }
    }
}

