package com.project.sumon.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Sumon on 3/17/2016.
 */
public class CategoryManager {
    private TodoCategory todoCategory;
    private DBHelper dBhelper;
    private SQLiteDatabase database;
    private TodoManager todoManager;

    public CategoryManager(Context context) {
        dBhelper = new DBHelper(context);

    }

    private void open() {
        database = dBhelper.getWritableDatabase();
    }

    private void close() {
        dBhelper.close();
    }


    public boolean addCategory(TodoCategory todoCategory) {
        this.open();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_CATEGORY_NAME, todoCategory.getCategoryName());
        contentValues.put(DBHelper.KEY_CREATED_AT, formattedDate);
        long isInserted = database.insert(DBHelper.TABLE_CATEGORY, null, contentValues);
        this.close();
        if (isInserted == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<TodoCategory> getAllCategories() {
        this.open();
        ArrayList<TodoCategory> todoCategoryList = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.TABLE_CATEGORY, null, null, null, null, null, DBHelper.KEY_CATEGORY_ID + " DESC", null);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                int categoryId = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_CATEGORY_ID));
                String categoryName = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_CATEGORY_NAME));
                todoCategory = new TodoCategory(categoryId, categoryName);
                todoCategoryList.add(todoCategory);
                cursor.moveToNext();

            }
        }
        this.close();
        return todoCategoryList;
    }

    public TodoCategory getTodoCategoryByid(int id) {
        this.open();
        Cursor cursor = database.query(DBHelper.TABLE_CATEGORY, new String[]{DBHelper.KEY_CATEGORY_ID, DBHelper.KEY_CATEGORY_NAME}, DBHelper.KEY_CATEGORY_ID + " = " + id, null, null, null, null);

        cursor.moveToFirst();
        int categoryID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_CATEGORY_ID));
        String categoryName = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_CATEGORY_NAME));
        todoCategory = new TodoCategory(categoryID, categoryName);
        this.close();
        return todoCategory;
    }

    public boolean deleteCategory(int categoryId) {
        this.open();
//        ArrayList<Todo> allCategoryTodos = todoManager.getTodosByCategoryId(categoryId);
//
//        for (Todo todo : allCategoryTodos) {
//            todoManager.deleteTodo(todo.getTodoId());
//        }

        int deleted = database.delete(DBHelper.TABLE_CATEGORY, DBHelper.KEY_CATEGORY_ID + " = " + categoryId, null);
        this.close();
        if (deleted > 0) {
            return true;

        } else return false;

    }


}
