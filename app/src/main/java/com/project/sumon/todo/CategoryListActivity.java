package com.project.sumon.todo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoryListActivity extends AppCompatActivity {
    private ListView categoryListView;
    CategoryManager categoryManager;
    AdapterForCategoryList adapter;
    ArrayList<TodoCategory> todoCategoryList;
    int categoryId;
    int cateId;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView infoTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        categoryListView = (ListView) findViewById(R.id.categoryListView);
        infoTV= (TextView) findViewById(R.id.infoTV);
        sharedPreferences = getSharedPreferences("todoInfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        categoryManager = new CategoryManager(this);
        todoCategoryList = categoryManager.getAllCategories();
        if (todoCategoryList.size()>0){
            infoTV.setVisibility(View.GONE);
            adapter = new AdapterForCategoryList(this, todoCategoryList);
            categoryListView.setAdapter(adapter);

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
                    cateId = todoCategoryList.get(position).getCategoryId();
                    AlertDialog.Builder builder = new AlertDialog.Builder(CategoryListActivity.this);
                    builder.setTitle("OPERATIONS");

                    builder.setMessage("Which Operation You want to perform ?");

                    builder.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent editIntent = new Intent(CategoryListActivity.this, CreateCategoryActivity.class);// (Intent.ACTION_CALL);
                            editIntent.putExtra("catID", cateId);

                            startActivity(editIntent);


                            dialog.dismiss();
                        }
                    });
                    builder.setNeutralButton("DELETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            AlertDialog.Builder builder = new AlertDialog.Builder(CategoryListActivity.this);
                            builder.setTitle("WARNING");
                            builder.setMessage("Previous Todos of this Category will be delete. Are you sure?  You want to delete ?");

                            builder.setNeutralButton("DELETE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    boolean isDeleted = categoryManager.deleteCategory(cateId);
                                    if (isDeleted) {
                                        Toast.makeText(getApplicationContext(), "category delete successfull", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(CategoryListActivity.this, CategoryListActivity.class));
                                        finish();

                                    } else {
                                        Toast.makeText(getApplicationContext(), "category delete not successfull", Toast.LENGTH_LONG).show();

                                    }

                                    dialog.dismiss();


                                }
                            });

                            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            builder.show();






























//                            boolean isDeleted = categoryManager.deleteCategory(cateId);
//                            if (isDeleted) {
//                                Toast.makeText(getApplicationContext(), "category delete successfull", Toast.LENGTH_LONG).show();
//                                startActivity(new Intent(CategoryListActivity.this, CategoryListActivity.class));
//                                finish();
//
//                            } else {
//                                Toast.makeText(getApplicationContext(), "category delete not successfull", Toast.LENGTH_LONG).show();
//
//                            }
//
//                            dialog.dismiss();


                        }
                    });

                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.show();
                    return false;

                }
            });
        }else {
            infoTV.setText("NO NEW CATEGORY");
        }


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
            createCatIntent.putExtra("catID", -1);
            startActivity(createCatIntent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onStop();
    }
}

