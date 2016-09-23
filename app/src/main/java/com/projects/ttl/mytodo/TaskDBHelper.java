package com.projects.ttl.mytodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Trang_2 on 9/10/2016.
 */

public class TaskDBHelper extends SQLiteOpenHelper {

    // public SQLiteDatabase db;
    public TaskDBHelper(Context context) {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        String createTable = "CREATE TABLE " + TaskContract.TaskEntry.TABLE + " ( " +
                TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL);";

        db.execSQL(createTable);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE);
        onCreate(db);
    }

    public void emptyTable(SQLiteDatabase db) {
        db.execSQL("DELETE * FROM TASKS");
    }


    public Cursor getAllTasks() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void addTask(String str) {
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, str);
        SQLiteDatabase db = this.getWritableDatabase();

        db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);

        db.close();
    }


    public void updateTask(String oldTask, String newTask, long id) {

        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, newTask);
        SQLiteDatabase db = this.getWritableDatabase();

        db.updateWithOnConflict(TaskContract.TaskEntry.TABLE, values,
                TaskContract.TaskEntry.COL_TASK_TITLE + "= ?", new String[]{oldTask}, SQLiteDatabase.CONFLICT_REPLACE);

        db.close();

    }

    public void deleteTask(String oldTask, String task, long id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry.COL_TASK_TITLE + "= ?", new String[]{oldTask});

        db.close();

    }

}



