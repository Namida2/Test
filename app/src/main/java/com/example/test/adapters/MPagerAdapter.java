package com.example.test.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.test.Week;
import com.example.test.R;

import java.util.List;

public class MPagerAdapter extends androidx.viewpager.widget.PagerAdapter {


    private Context context;
    private List<String> listStudyDays;
    private List<ListViewAdapter> listItemAdapters;

    public MPagerAdapter(Context context, List<String> listStudyDays, List<ListViewAdapter> listItemAdapters)
    {
        this.context = context;
        this.listStudyDays = listStudyDays;
        this.listItemAdapters = listItemAdapters;
    }

    @Override
    public int getCount() {
        return listStudyDays.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem (ViewGroup viewGroup, int position )
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card, viewGroup, false);

        TextView dayOfWeek = view.findViewById(R.id.dayOfWeek);
        dayOfWeek.setText(listStudyDays.get(position));

        ListView containerForCardItems = view.findViewById(R.id.containerForCardItems);
        containerForCardItems.setAdapter(listItemAdapters.get(position));

        viewGroup.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
