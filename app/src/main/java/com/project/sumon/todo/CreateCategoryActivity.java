package com.project.sumon.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateCategoryActivity extends AppCompatActivity {
    ArrayList<TodoCategory> todoCategoryList;
    CategoryManager categoryManager;
    private EditText categoryET;
    TodoCategory category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_back);
        categoryET = (EditText) findViewById(R.id.categoryET);
        categoryManager = new CategoryManager(this);


    }

    public void createCategory(View view) {
        String categoryName = categoryET.getText().toString();
        if (categoryName.length() > 2) {
            category = new TodoCategory();
            category.setCategoryName(categoryName);
            boolean isCreated = categoryManager.addCategory(category);
            if (isCreated){
                categoryET.getText().clear();
                Toast.makeText(getApplicationContext(),"category created",Toast.LENGTH_LONG).show();
                Intent backTolistIntent=new Intent(CreateCategoryActivity.this,CategoryListActivity.class);
                startActivity(backTolistIntent);
            }else {
                Toast.makeText(getApplicationContext(),"category failed",Toast.LENGTH_LONG).show();

            }
        }
    }
}
