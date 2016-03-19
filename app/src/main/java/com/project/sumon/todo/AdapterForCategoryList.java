package com.project.sumon.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sumon on 3/16/2016.
 */
public class AdapterForCategoryList extends ArrayAdapter<TodoCategory> {
    private TodoCategory todoCategory;
    private Context context;
    private ArrayList<TodoCategory> todoCategoryArrayList;

    public AdapterForCategoryList(Context context, ArrayList<TodoCategory> todoCategoryList) {
        super(context, R.layout.custom_row_for_category_list,todoCategoryList);
        this.context = context;
        this.todoCategoryArrayList = todoCategoryList;
    }

    static class ViewHolder {
        TextView categoryNameTV;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_row_for_category_list, null);
            viewHolder = new ViewHolder();
            viewHolder.categoryNameTV = (TextView) convertView.findViewById(R.id.categoryTV);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.categoryNameTV.setText(todoCategoryArrayList.get(position).getCategoryName());
        return convertView;
    }
}
