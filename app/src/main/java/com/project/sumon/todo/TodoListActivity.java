package com.project.sumon.todo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TodoListActivity extends AppCompatActivity {
    private AdapterForTodoList adapter;
    private TodoManager todoManager;
    private ArrayList<Todo> todoList;
    private ListView todoListView;
    private int CategoryId;
    int todoId;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView infoTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_back);
        todoListView = (ListView) findViewById(R.id.todoListView);
        infoTV= (TextView) findViewById(R.id.infoTV);
        sharedPreferences = getSharedPreferences("todoInfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        CategoryId = sharedPreferences.getInt("categoryId", 0);
        todoManager = new TodoManager(this);
        todoList = todoManager.getTodosByCategoryId(CategoryId);
        if (todoList.size()>0) {
            infoTV.setVisibility(View.GONE);
            adapter = new AdapterForTodoList(this, todoList);
            todoListView.setAdapter(adapter);
            todoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    todoId = todoList.get(position).getTodoId();
                    Intent todoDetailsIntent = new Intent(TodoListActivity.this, TodoDetailsActivity.class);
                    editor.putInt("todoId", todoId);
                    editor.commit();
                    startActivity(todoDetailsIntent);

                }
            });
        }else {
            todoListView.setVisibility(View.GONE);
            infoTV.setText("YOU DON'T CREATE ANY TODO YET");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.todo_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_todo) {
            Intent addTodoIntent = new Intent(TodoListActivity.this, CreateTodoActivity.class);
            startActivity(addTodoIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
