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
import android.widget.Toast;

import java.util.ArrayList;

public class CategoryListActivity extends AppCompatActivity {
    private ListView categoryListView;
    CategoryManager categoryManager;
    AdapterForCategoryList adapter;
    ArrayList<TodoCategory> todoCategoryList;
    int categoryId;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        categoryListView = (ListView) findViewById(R.id.categoryListView);
        sharedPreferences = getSharedPreferences("todoInfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        categoryManager = new CategoryManager(this);
        todoCategoryList = categoryManager.getAllCategories();
        adapter = new AdapterForCategoryList(this, todoCategoryList);
        categoryListView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoryId = todoCategoryList.get(position).getCategoryId();
                Intent todoListIntent = new Intent(CategoryListActivity.this, TodoListActivity.class);
                editor.putInt("categoryId", categoryId);
                editor.commit();
                startActivity(todoListIntent);
            }
        });
        categoryListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                int cateId = todoCategoryList.get(position).getCategoryId();
                boolean isDeleted = categoryManager.deleteCategory(cateId);
                if (isDeleted) {
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "category delete successfull", Toast.LENGTH_LONG).show();

                    return true;
                } else {
                    return false;

                }
            }
        });
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.category_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_category) {
            Intent createCatIntent = new Intent(CategoryListActivity.this, CreateCategoryActivity.class);
            startActivity(createCatIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
