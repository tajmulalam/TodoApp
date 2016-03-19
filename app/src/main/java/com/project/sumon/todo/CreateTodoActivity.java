package com.project.sumon.todo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateTodoActivity extends AppCompatActivity {
    CategoryManager categoryManager;
    ArrayList<TodoCategory> todoCategoryList;
    private Spinner categorySpinner;
    private AdapterForCategoryDropdown adapter;
    private ImageButton datePickerBtn;
    Calendar calendar = Calendar.getInstance();
    private EditText dateET;
    private EditText titleET, descriptionET;
    private RadioGroup todoTypeRadioGR;
    int categoryId;
    private TodoManager todoManager;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_back);

        datePickerBtn = (ImageButton) findViewById(R.id.datePickerBtn);
        dateET = (EditText) findViewById(R.id.dateET);
        titleET = (EditText) findViewById(R.id.titleET);
        descriptionET = (EditText) findViewById(R.id.descriptionET);
        todoTypeRadioGR = (RadioGroup) findViewById(R.id.todoTypeRadioGR);
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);

        categoryManager = new CategoryManager(this);
        todoCategoryList = categoryManager.getAllCategories();
        adapter = new AdapterForCategoryDropdown(this, todoCategoryList);
        categorySpinner.setAdapter(adapter);
        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateTodoActivity.this, listner, calendar.get(calendar.YEAR), calendar.get(calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH)).show();
            }
        });
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryId = todoCategoryList.get(position).getCategoryId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        todoManager = new TodoManager(this);
    }

    DatePickerDialog.OnDateSetListener listner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateET.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
        }
    };

    public void addTodo(View view) {
        int pendingStatus=0;
        int todoType = 0;
        String created_at = dateET.getText().toString();
        int id = todoTypeRadioGR.getCheckedRadioButtonId();
        if (id == -1) {
            Toast.makeText(getApplicationContext(), "LOL! Select Properly", Toast.LENGTH_LONG).show();

        } else if (id == R.id.normalRadioBtn) {
            todoType = 1;
        } else if (id == R.id.urgentRadioBtn) {
            todoType = 2;
        } else if (id == R.id.vUrgentRadioBtn) {
            todoType = 3;
        }
        String title = titleET.getText().toString();
        String description = descriptionET.getText().toString();
        if (created_at.length() > 0 && description.length() > 2 && title.length() > 0) {
            Todo aTodo = new Todo(todoType, title, description, created_at,pendingStatus);
            boolean isTodoCreated = todoManager.createTodo(aTodo, categoryId);
            if (isTodoCreated) {
                Toast.makeText(getApplicationContext(), "Todo Created", Toast.LENGTH_LONG).show();
                dateET.getText().clear();
                titleET.getText().clear();
                descriptionET.getText().clear();
                todoTypeRadioGR.setSelected(false);
            } else {
                Toast.makeText(getApplicationContext(), "Todo Not Created", Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(getApplicationContext(), "LOL! Fill Properly", Toast.LENGTH_LONG).show();

        }
    }
}
