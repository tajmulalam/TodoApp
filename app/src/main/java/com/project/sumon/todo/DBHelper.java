package com.project.sumon.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * Created by Sumon on 3/16/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "todoDB";
    static final String TABLE_TODO = "todos";
    static final String TABLE_CATEGORY = "categories";

    static final String KEY_CREATED_AT = "created_at";

    //TODO TABLES COLUMNS
    static final String KEY_TODO_ID = "todo_id";
    static final String KEY_TITLE = "title";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_TYPE = "todo_type";
    static final String KEY_STATUS = "todo_status";

    //CATEGORIES TABLE COLUMNS

    static final String KEY_CATEGORY_ID = "category_id";
    static final String KEY_CATEGORY_NAME = "category_name";

    ////todo table columns


    // Category table create statement
    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY
            + "(" + KEY_CATEGORY_ID + " INTEGER PRIMARY KEY," + KEY_CATEGORY_NAME + " TEXT,"
            + KEY_CREATED_AT + " DATETIME" + ")";


    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_TODO = "CREATE TABLE "
            + TABLE_TODO + "(" + KEY_TODO_ID + " INTEGER PRIMARY KEY," + KEY_TITLE
            + " TEXT," + KEY_DESCRIPTION + " TEXT," + KEY_TYPE + " INTEGER," + KEY_CREATED_AT
            + " DATETIME," + KEY_STATUS + " INTEGER, " + KEY_CATEGORY_ID + " INTEGER," + " FOREIGN KEY( " + KEY_CATEGORY_ID + " ) REFERENCES " + TABLE_CATEGORY + " ( " + KEY_CATEGORY_ID + " ) ON DELETE CASCADE" + ")";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_TODO);

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
//            db.execSQL("PRAGMA foreign_keys=ON;");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            }


        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);


        // create new tables
        onCreate(db);
    }
}
