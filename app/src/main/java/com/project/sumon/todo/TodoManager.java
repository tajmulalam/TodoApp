package com.project.sumon.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Sumon on 3/17/2016.
 */
public class TodoManager {
    private Todo todo;
    private DBHelper dBhelper;
    private SQLiteDatabase database;

    public TodoManager(Context context) {
        dBhelper = new DBHelper(context);
    }

    private void open() {
        database = dBhelper.getWritableDatabase();
    }

    private void close() {
        dBhelper.close();
    }

    public ArrayList<Todo> getTodosByCategoryId(int id) {
        this.open();
        ArrayList<Todo> todosList = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.TABLE_TODO, new String[]{DBHelper.KEY_TODO_ID, DBHelper.KEY_TITLE, DBHelper.KEY_DESCRIPTION, DBHelper.KEY_TYPE, DBHelper.KEY_CATEGORY_ID, DBHelper.KEY_CREATED_AT, DBHelper.KEY_STATUS}, DBHelper.KEY_CATEGORY_ID + " = " + id, null, null, null, null, null);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                int todoId = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_TODO_ID));
                int todoType = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_TYPE));
                String title = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DESCRIPTION));
                int categoryID = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_CATEGORY_ID));
                int status = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_STATUS));
                String created_at = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_CREATED_AT));
                todo = new Todo(todoId, todoType, title, description, created_at, status, categoryID);
                todosList.add(todo);
                cursor.moveToNext();

            }
        }
        this.close();
        return todosList;
    }

    public boolean createTodo(Todo todo, int categoryId) {
        this.open();
        ContentValues contents = new ContentValues();
        contents.put(DBHelper.KEY_TYPE, todo.getTodo_type());
        contents.put(DBHelper.KEY_TITLE, todo.getTitle());
        contents.put(DBHelper.KEY_DESCRIPTION, todo.getDescription());
        contents.put(DBHelper.KEY_CREATED_AT, todo.getCreated_at());
        contents.put(DBHelper.KEY_CATEGORY_ID, categoryId);
        contents.put(DBHelper.KEY_STATUS, todo.getStatus());
        long isInserted = database.insert(DBHelper.TABLE_TODO, null, contents);
        this.close();
        if (isInserted == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Todo getTodoById(int id) {
        this.open();
        Cursor cursor = database.query(DBHelper.TABLE_TODO, new String[]{DBHelper.KEY_TODO_ID, DBHelper.KEY_TYPE, DBHelper.KEY_TITLE, DBHelper.KEY_DESCRIPTION, DBHelper.KEY_CREATED_AT, DBHelper.KEY_CATEGORY_ID, DBHelper.KEY_STATUS}, DBHelper.KEY_TODO_ID + " = " + id, null, null, null, null, null);
        cursor.moveToFirst();
        if (cursor != null && cursor.moveToFirst()) {
            int todoId = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_TODO_ID));
            int todoType = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_TYPE));
            String title = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DESCRIPTION));
            String created_at = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_CREATED_AT));
            int catId = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_CATEGORY_ID));
            int status = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_STATUS));

            todo = new Todo(todoId, todoType, title, description, created_at, status, catId);
        }
        this.close();
        return todo;
    }

    public boolean updateStatus(int id, int status,String dateDone) {
        this.open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_STATUS, status);
        int updated = database.update(DBHelper.TABLE_TODO, contentValues, DBHelper.KEY_TODO_ID + " = " + id, null);
        this.close();
        if (updated > 0) {
            return true;
        } else return false;

    }

    public boolean deleteTodo(int id) {
        this.open();
        int deleted = database.delete(DBHelper.TABLE_TODO, DBHelper.KEY_TODO_ID + " = " + id, null);
        this.close();
        if (deleted > 0) {
            return true;
        } else return false;
    }



    public boolean updateTodo(int todoId,Todo todo)
    {
        this.open();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBHelper.KEY_TYPE,todo.getTodo_type());
        contentValues.put(DBHelper.KEY_TITLE,todo.getTitle());
        contentValues.put(DBHelper.KEY_DESCRIPTION,todo.getDescription());
        contentValues.put(DBHelper.KEY_CREATED_AT,todo.getCreated_at());
        int isUpdated=database.update(DBHelper.TABLE_TODO,contentValues, DBHelper.KEY_TODO_ID+" = "+todoId,null);
        if (isUpdated>0)
        {
            return true;

        }else return false;
    }
}
