package com.example.test.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.FragmentManager;
import com.example.test.Data;
import com.example.test.R;

import java.util.List;


public class ListViewForSpinnerAdapter extends BaseAdapter
{
    Context context;
    List<String> listGroupNames;
    LayoutInflater inflater;
    List<ArrayAdapter<String>> listAdapters;
    public static  FragmentManager fragmentManager;
    public ListViewForSpinnerAdapter(Context context,  List<String>  listGroupNames, List<ArrayAdapter<String>> listAdapters,  FragmentManager fragmentManager)
    {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.listGroupNames = listGroupNames;
        this.listAdapters = listAdapters;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public int getCount() {
        return listGroupNames.size();
    }

    @Override
    public Object getItem(int i) {
        return listGroupNames.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        final AppCompatSpinner spinner;
        if (view == null)
        {
            view = inflater.inflate(R.layout.spinner, viewGroup, false);

        }

        if (view != null)
        {
            spinner = view.findViewById(R.id.mySpinner);
            spinner.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
            spinner.setAdapter(listAdapters.get(i));
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                   @Override
                   public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                       boolean a = listGroupNames.contains(spinner.getSelectedItem().toString());
                       if(!listGroupNames.contains(spinner.getSelectedItem().toString())) {
                           Data.groupName = adapterView.getItemAtPosition(i).toString();
                           Intent returnIntent = new Intent();
                           ((Activity)context).setResult(Activity.RESULT_CANCELED, returnIntent);
                           ((Activity)context).finish();
                       }
                   }

                   @Override
                   public void onNothingSelected(AdapterView<?> adapterView) {

                   }
               });
        }

        return view;
    }
}
