package com.project.sumon.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateCategoryActivity extends AppCompatActivity {
    ArrayList<TodoCategory> todoCategoryList;
    CategoryManager categoryManager;
    EditText categoryET;
    TodoCategory category;
    int categoryID;
    Button createCategoryBtn, updateCategoryBtn;
    boolean isUpdated;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_back);
        categoryID = getIntent().getIntExtra("catID", 2);
        categoryET = (EditText) findViewById(R.id.categoryET);
        createCategoryBtn = (Button) findViewById(R.id.createCategoryBtn);
        updateCategoryBtn = (Button) findViewById(R.id.updateCategoryBtn);
        categoryManager = new CategoryManager(this);
        if (categoryID > 0) {
            setTitle("Update Category");
            final TodoCategory haveToUpdateCategory = categoryManager.getTodoCategoryByid(categoryID);
            categoryET.setText(haveToUpdateCategory.getCategoryName().toString());
            createCategoryBtn.setVisibility(View.INVISIBLE);
            updateCategoryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String category = categoryET.getText().toString();
                    if (category.length() > 0 && category != "") {
                        TodoCategory goingToUpdateCategory = new TodoCategory(category);

                        isUpdated = categoryManager.updateCategory(categoryID, goingToUpdateCategory);
                    }
                    if (isUpdated) {
                        categoryET.getText().clear();
                        Toast.makeText(getApplicationContext(), "Category Updated", Toast.LENGTH_LONG).show();
                        Intent backTolistIntent = new Intent(CreateCategoryActivity.this, CategoryListActivity.class);
                        startActivity(backTolistIntent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Update Operation failed", Toast.LENGTH_LONG).show();

                    }

                }
            });
        } else {
            updateCategoryBtn.setVisibility(View.INVISIBLE);
            createCategoryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String categoryName = categoryET.getText().toString();
                    if (categoryName.length() > 2) {
                        category = new TodoCategory();
                        category.setCategoryName(categoryName);
                        boolean isCreated = categoryManager.addCategory(category);
                        if (isCreated) {
                            categoryET.getText().clear();
                            Toast.makeText(getApplicationContext(), "category created", Toast.LENGTH_LONG).show();
                            Intent backTolistIntent = new Intent(CreateCategoryActivity.this, CategoryListActivity.class);
                            startActivity(backTolistIntent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "category failed", Toast.LENGTH_LONG).show();

                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Too Short !", Toast.LENGTH_LONG).show();

                    }
                }
            });

        }


    }


}
