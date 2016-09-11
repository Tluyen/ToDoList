package com.projects.ttl.mytodo;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    private TaskDBHelper mHelper;
    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;
    //final EditText taskEditText = new EditText (this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTaskListView = (ListView) findViewById(R.id.list_tasks);
        mHelper = new TaskDBHelper(this);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String taskString[] = new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE};
        Cursor dbCursor = db.query(TaskContract.TaskEntry.TABLE,
                taskString, null, null, null, null, null);
        while (dbCursor.moveToNext()) {
            int index = dbCursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            //Log.d(TAG, "Task"+ dbCursor.getString(index));
        }
        dbCursor.close();
        db.close();
        updateList();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final EditText taskEditText = new EditText(this);
        final DatePicker taskDueDate = new DatePicker(this);
        final TimePicker taskDueTime = new TimePicker(this);
        // taskEditText.setTextColor(Color.BLUE);
        //taskDueDate.setBackgroundColor(Color.BLUE);
        taskDueDate.setScaleX((float) 0.8);
        taskDueDate.setScaleY((float) 0.8);
        taskDueTime.setScaleX((float) 0.3);
        taskDueTime.setScaleY((float) 0.3);

        // taskDueTime.setBackgroundColor(Color.BLUE);
        switch (item.getItemId()) {
            case R.id.action_add_task:
                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.addView(taskEditText);
                ll.addView(taskDueDate);

                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add New Task")
                        .setView(ll)
                        //.setView(taskDueDate)
                        //    .setView(taskDueTime)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                int mMonth = taskDueDate.getMonth();
                                int mDay = taskDueDate.getDayOfMonth();
                                int mYear = taskDueDate.getYear();
                                String strDueDate = mMonth + "-" + mDay + "-" + mYear;
                                SQLiteDatabase db = mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
                                values.put(TaskContract.TaskEntry.COL_TASK_DATE, strDueDate);
                                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateList();
                                //Log.d(TAG, "Add new Task");
                            }
                        })

                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();


        }
        return super.onOptionsItemSelected(item);
    }


    private void updateList() {
        int index;
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();

        //String strTaskString[]= new String[]{TaskContract.TaskEntry._ID,
        //                    TaskContract.TaskEntry.COL_TASK_DATE,
        //                 TaskContract.TaskEntry.COL_TASK_TITLE};

        String strTaskString[] = new String[]{TaskContract.TaskEntry.COL_TASK_TITLE};

        Cursor dbCursor = db.query(TaskContract.TaskEntry.TABLE,
                strTaskString, null, null, null, null, null);

        while (dbCursor.moveToNext()) {
            index = dbCursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            taskList.add(dbCursor.getString(index));

        }

        if (mAdapter == null) {
            //  mAdapter = new ArrayAdapter<>(this, R.layout.item_todo,R.id.task_title, taskList);
            // mTaskListView.setAdapter(mAdapter);
            mAdapter = new ArrayAdapter<>(this, R.layout.item_todo, R.id.task_title, taskList);
            mTaskListView.setAdapter(mAdapter);

        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
        dbCursor.close();
        db.close();
    }


    public void deleteTask(View vw) {

        View parentView = (View) vw.getParent();
        TextView taskTextView = (TextView) parentView.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry.COL_TASK_TITLE + " =?",
                new String[]{task});
        db.close();
        updateList();
    }

}


