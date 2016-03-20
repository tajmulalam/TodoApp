package com.project.sumon.todo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TodoDetailsActivity extends AppCompatActivity {
    TodoManager todoManager;
    Todo todo;
    TextView categoryTV, dateTV, todoTitleTV, todoTypeTV, todoDescriptionTV;
    ImageView todoTypeImageV, statusImageV;
    TodoCategory todoCategory;
    CategoryManager categoryManager;
    int todoId;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_back);

        sharedPreferences = getSharedPreferences("todoInfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();


        categoryTV = (TextView) findViewById(R.id.categoryTV);
        dateTV = (TextView) findViewById(R.id.dateTV);
        todoTitleTV = (TextView) findViewById(R.id.todoTitleTV);
        todoTypeTV = (TextView) findViewById(R.id.todoTypeTV);
        todoDescriptionTV = (TextView) findViewById(R.id.todoDescriptionTV);
        todoTypeImageV = (ImageView) findViewById(R.id.todoTypeImageV);
        statusImageV = (ImageView) findViewById(R.id.statusImageV);
        todoId = sharedPreferences.getInt("todoId", 0);
        todoManager = new TodoManager(this);
        todo = todoManager.getTodoById(todoId);
        if (todo.getStatus() == 0) {
            statusImageV.setImageResource(R.mipmap.ic_pending);
            statusImageV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isStatusChanged = todoManager.updateStatus(todo.getTodoId(), 1,getDateTime());
                    if (isStatusChanged) {
                        Toast.makeText(getApplicationContext(), "DONE!", Toast.LENGTH_LONG).show();
                        Intent refresh = new Intent(TodoDetailsActivity.this, TodoDetailsActivity.class);
                        startActivity(refresh);//Start the same Activity
                        finish();
                    }
                }
            });
        } else {
            statusImageV.setImageResource(R.mipmap.ic_done);
            statusImageV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isStatusChanged = todoManager.updateStatus(todo.getTodoId(), 0,getDateTime());
                    if (isStatusChanged) {
                        Toast.makeText(getApplicationContext(), "PENDING!", Toast.LENGTH_LONG).show();
                        Intent refresh = new Intent(TodoDetailsActivity.this, TodoDetailsActivity.class);
                        startActivity(refresh);//Start the same Activity
                        finish();
                    }
                }
            });

        }
        dateTV.setText(todo.getCreated_at());
        todoTitleTV.setText(todo.getTitle());
        setTitle(todo.getCreated_at());
        if (todo.getTodo_type() == 1) {
            todoTypeImageV.setImageResource(R.mipmap.ic_normal);
        } else if (todo.getTodo_type() == 2) {
            todoTypeImageV.setImageResource(R.mipmap.ic_urgent);

        } else {
            todoTypeImageV.setImageResource(R.mipmap.ic_vurgent);

        }
        todoDescriptionTV.setText(todo.getDescription());


    }


    public void editTodo(View view)
    {
        Intent editTodoIntent=new Intent(TodoDetailsActivity.this,CreateTodoActivity.class);
        editTodoIntent.putExtra("todoId",todoId);
        startActivity(editTodoIntent);
    }
    public void deleteTodo(View view)
    {
       boolean isDeleted=todoManager.deleteTodo(todo.getTodoId());
        if (isDeleted)
        {
            Toast.makeText(getApplicationContext(),"Todo Item Deleted Successfully",Toast.LENGTH_LONG).show();
            Intent backToList=new Intent(TodoDetailsActivity.this,TodoListActivity.class);
            startActivity(backToList);
        }else {
            Toast.makeText(getApplicationContext(),"Failed!",Toast.LENGTH_LONG).show();

        }
    }


    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
