package com.projects.ttl.mytodo;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    private static Object o;
    private static long taskID;
    private TaskDBHelper mHelper;
    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;
    private String oldTaskName;
    private int selectedItem;


    //final EditText taskEditText = new EditText (this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTaskListView = (ListView) findViewById(R.id.list_tasks);
        mHelper = new TaskDBHelper(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String updatedTaskName = (String) bundle.get("Update Task Name");
            updateTask(o, updatedTaskName, taskID);
        } else {

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


        mTaskListView.setClickable(true);
        mTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int pos, long id) {
                o = mTaskListView.getItemAtPosition(pos);
                taskID = id + 1;
                //selectedItem = mTaskListView.getItem(pos);
                Intent i = new Intent(MainActivity.this, EditTaskActivity.class);
                startActivity(i);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // View v = (View) setView(R.layout.add_task);
        final EditText taskEditText = new EditText(this);
        //  setContentView(R.layout.add_task);
        //   final EditText taskEditText = (EditText) findViewById(R.id.mTask);
        final TextView txtDueDateLabel = new TextView(this);
        //final DatePicker taskDueDate = new DatePicker(this);
        final DatePicker taskDueDate = (DatePicker) (findViewById(R.id.mDate));
        //  final TimePicker taskDueTime =  (TimePicker) (findViewById(R.id.mTime));
        //final TimePicker taskDueTime = new TimePicker(this);
        // final Calendar c= Calendar.getInstance();
        // taskEditText.setTextColor(Color.BLUE);
        //taskDueDate.setBackgroundColor(Color.BLUE);
        //taskDueDate.setScaleX((float) 0.8);
        // taskDueDate.setScaleY((float) 0.8);
        // taskDueTime.setScaleX((float) 0.3);
        // taskDueTime.setScaleY((float) 0.3);
        //  txtDueDateLabel.setText("Due Date");
        //taskDueDate.setMinDate(c.get(Calendar.DATE));


        // taskDueTime.setBackgroundColor(Color.BLUE);
        switch (item.getItemId()) {
            case R.id.action_add_task:
                //   LinearLayout ll = new LinearLayout(this);
                //   ll.setOrientation(LinearLayout.VERTICAL);
                //  ll.addView(taskEditText);
                //   ll.addView(txtDueDateLabel);
                //   ll.addView(taskDueDate);
                setTheme(0);

                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add New Task")

                        .setView((taskEditText))
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // String task = String.valueOf(findViewById(R.layout.add_task.mTask.getText()));
                                String task = taskEditText.getText().toString();
                                // int mMonth = taskDueDate.getMonth();
                                // int mDay = taskDueDate.getDayOfMonth();
                                // int mYear = taskDueDate.getYear();
                                //  String strDueDate = mMonth + "-" + mDay + "-" + mYear;
                                SQLiteDatabase db = mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                if (task != null || task != "") {
                                    values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
                                    //values.put(TaskContract.TaskEntry.COL_TASK_DATE, strDueDate);
                                    db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                            null,
                                            values,
                                            SQLiteDatabase.CONFLICT_REPLACE);
                                    db.close();
                                    updateList();
                                }
                                //Log.d(TAG, "Add new Task");
                            }
                        })

                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();


        }
        return super.onOptionsItemSelected(item);
    }

/*
    private void updateList() {
        int index;


        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();

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
            mAdapter = new ArrayAdapter<String>(this, R.layout.activity_main, R.id.task_title, taskList);
            mTaskListView.setAdapter(mAdapter);

        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
        dbCursor.close();
        db.close();

    }
*/

    private void updateList() {
        int index;


        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String strTaskString[] = new String[]{TaskContract.TaskEntry.COL_TASK_TITLE};

        Cursor dbCursor = db.query(TaskContract.TaskEntry.TABLE,
                strTaskString, null, null, null, null, null);

        while (dbCursor.moveToNext()) {
            index = dbCursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            taskList.add(dbCursor.getString(index));

        }

        if (mAdapter == null) {

            mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
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


    public void updateTask(Object o, String task, long id) {

        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
        SQLiteDatabase db = mHelper.getWritableDatabase();

        db.updateWithOnConflict(TaskContract.TaskEntry.TABLE, values,
                TaskContract.TaskEntry.COL_TASK_ID + "=" + id, null, SQLiteDatabase.CONFLICT_IGNORE);

        db.close();
        updateList();
    }

}

