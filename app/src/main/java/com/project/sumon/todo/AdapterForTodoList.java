package com.project.sumon.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sumon on 3/16/2016.
 */
public class AdapterForTodoList extends ArrayAdapter<Todo> {

    private ArrayList<Todo>todoList;
    private Context context;

    public AdapterForTodoList(Context context, ArrayList<Todo> todoList) {
        super(context, R.layout.custom_row_for_todo_list, todoList);
        this.todoList = todoList;
        this.context = context;
    }

    static class ViewHolder {
        TextView todoTitleTV;
        ImageView statusImageV, todoTypeImageV;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.custom_row_for_todo_list,null);
            viewHolder=new ViewHolder();
            viewHolder.todoTitleTV= (TextView) convertView.findViewById(R.id.todoTitleTV);
            viewHolder.statusImageV= (ImageView) convertView.findViewById(R.id.statusImageV);
            viewHolder.todoTypeImageV= (ImageView) convertView.findViewById(R.id.todoTypeImageV);

            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.todoTitleTV.setText(todoList.get(position).getTitle());
        int todoType=todoList.get(position).getTodo_type();
        if (todoType==1)
        {
            viewHolder.todoTypeImageV.setImageResource(R.mipmap.ic_normal);

        }else if (todoType==2)
        {
            viewHolder.todoTypeImageV.setImageResource(R.mipmap.ic_urgent);

        }else
        {
            viewHolder.todoTypeImageV.setImageResource(R.mipmap.ic_vurgent);

        }
        int status=todoList.get(position).getStatus();
        if (status==0)
        {
            viewHolder.statusImageV.setImageResource(R.mipmap.ic_pending);

        }else
        {
            viewHolder.statusImageV.setImageResource(R.mipmap.ic_done);
        }
        return convertView;
    }
}
