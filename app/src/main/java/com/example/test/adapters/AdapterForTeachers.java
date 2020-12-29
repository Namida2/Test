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

import com.example.test.R;
import com.example.test.TeacherActivity;
import com.example.test.TeacherItem;
import com.example.test.Teachers;

import java.util.List;

public class AdapterForTeachers extends RecyclerView.Adapter<AdapterForTeachers.ViewHolder> {


    private Context context;
    private List<TeacherItem> teachers;

    public AdapterForTeachers(Context context, List<TeacherItem> teachers)
    {
        this.context = context;
        this.teachers = teachers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.teachers_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.teacherName.setText(teachers.get(position).getName());
        holder.phone.setText(teachers.get(position).getPhone());
        holder.mail.setText(teachers.get(position).getMail());
    }

    @Override
    public int getItemCount() {

        return teachers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView teacherName;
        TextView phone;
        TextView mail;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teacherName = itemView.findViewById(R.id.nameTeacher);
            phone = itemView.findViewById(R.id.phone);
            mail = itemView.findViewById(R.id.mail);
            relativeLayout = itemView.findViewById(R.id.clickableTeachersRelativeLayout);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TextView name = view.findViewById(R.id.nameTeacher);
                    TeacherItem item = TeacherItem.findByName(Teachers.teachers, name.getText().toString());
                    Intent intent = new Intent(context, TeacherActivity.class);
                    intent.putExtra("item", TeacherItem.convertToArray(item));
                    context.startActivity(intent);
                }
            });

        }
    }
}
