package com.project.sumon.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sumon on 3/17/2016.
 */
public class AdapterForCategoryDropdown extends ArrayAdapter<TodoCategory> {
    private ArrayList<TodoCategory> todoCategories;
    private Context context;

    public AdapterForCategoryDropdown(Context context, ArrayList<TodoCategory> todoCategories) {
        super(context, R.layout.spinner, todoCategories);
        this.todoCategories = todoCategories;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    static class ViewHolder {
    TextView categoryNameTV;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null)
        {
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.spinner,null);
            viewHolder=new ViewHolder();
            viewHolder.categoryNameTV= (TextView) convertView.findViewById(R.id.spinnerTV);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.categoryNameTV.setText(todoCategories.get(position).getCategoryName());
        return convertView;
    }
}
