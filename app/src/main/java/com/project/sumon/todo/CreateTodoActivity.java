package com.project.sumon.todo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
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
    RadioButton normalRadioBtn, urgentRadioBtn, vUrgentRadioBtn;
    private TodoManager todoManager;
    int todoId;
    Button addTodoBtn, updateTodoBtn;
    LinearLayout categoryLL;

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
        normalRadioBtn = (RadioButton) findViewById(R.id.normalRadioBtn);
        urgentRadioBtn = (RadioButton) findViewById(R.id.urgentRadioBtn);
        vUrgentRadioBtn = (RadioButton) findViewById(R.id.vUrgentRadioBtn);
        todoTypeRadioGR.check(normalRadioBtn.getId());
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        addTodoBtn = (Button) findViewById(R.id.addTodoBtn);
        updateTodoBtn = (Button) findViewById(R.id.updateTodoBtn);
        categoryLL= (LinearLayout) findViewById(R.id.categoryLL);
        todoId = getIntent().getIntExtra("todoId", 0);

        categoryManager = new CategoryManager(this);
        todoCategoryList = categoryManager.getAllCategories();
        adapter = new AdapterForCategoryDropdown(this, todoCategoryList);
        categorySpinner.setAdapter(adapter);
        todoManager = new TodoManager(this);
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
        if (todoId > 0) {
            setTitle("Update Todo");
            addTodoBtn.setVisibility(View.INVISIBLE);
            categoryLL.setVisibility(View.GONE);
            final Todo haveToupdate = todoManager.getTodoById(todoId);
            dateET.setText(haveToupdate.getCreated_at().toString());
            titleET.setText(haveToupdate.getTitle());
            int type = haveToupdate.getTodo_type();
            if (type == 1) {
                todoTypeRadioGR.check(normalRadioBtn.getId());
            } else if (type == 2) {
                todoTypeRadioGR.check(urgentRadioBtn.getId());
            } else {
                todoTypeRadioGR.check(vUrgentRadioBtn.getId());
            }
            descriptionET.setText(haveToupdate.getDescription());
//            int cateid = haveToupdate.getCategoryId();
//            categorySpinner.setSelection(cateid);
            updateTodoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int todoType = 0;
                    String created_at = dateET.getText().toString();
                    String title = titleET.getText().toString();
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
                    String description = descriptionET.getText().toString();
                    if (created_at.length() > 0 && description.length() > 2 && title.length() > 0) {
                        Todo goingToUpdateTodo = new Todo(todoType, title, description, created_at);
                        boolean isUpdated = todoManager.updateTodo(todoId, goingToUpdateTodo);
                        if (isUpdated) {
                            Toast.makeText(getApplicationContext(), "Todo Updated", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(CreateTodoActivity.this, TodoListActivity.class));
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Update Operation failed", Toast.LENGTH_LONG).show();

                        }
                    }
                }
            });
        } else {
            updateTodoBtn.setVisibility(View.INVISIBLE);
            addTodoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pendingStatus = 0;
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
                        Todo aTodo = new Todo(todoType, title, description, created_at, pendingStatus);
                        boolean isTodoCreated = todoManager.createTodo(aTodo, categoryId);
                        if (isTodoCreated) {
                            Toast.makeText(getApplicationContext(), "Todo Created", Toast.LENGTH_LONG).show();
                            dateET.getText().clear();
                            titleET.getText().clear();
                            descriptionET.getText().clear();
                            todoTypeRadioGR.setSelected(false);
                            Intent backTolistIntent = new Intent(CreateTodoActivity.this, TodoListActivity.class);
                            startActivity(backTolistIntent);

                        } else {
                            Toast.makeText(getApplicationContext(), "Todo Not Created", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "LOL! Fill Properly", Toast.LENGTH_LONG).show();

                    }
                }
            });

        }

    }

    DatePickerDialog.OnDateSetListener listner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateET.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
        }
    };

}
