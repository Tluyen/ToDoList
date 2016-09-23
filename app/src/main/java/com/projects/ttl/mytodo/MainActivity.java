package com.projects.ttl.mytodo;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    private static Integer FORCED_INIT = 1;

    private static Object o;
    private static long taskID;
    TextView taskTitle;
    private TaskDBHelper mHelper;
    private ListView mTaskListView;
    private TextView mTaskTitle;
    private ArrayAdapter<String> mAdapter;
    private String oldTaskName;
    private int selectedItem;
    private ArrayList<String> taskRowID = new ArrayList<>();

    //final EditText taskEditText = new EditText (this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTaskListView = (ListView) findViewById(R.id.list_tasks);
        mHelper = new TaskDBHelper(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String updatedTaskName = (String) bundle.get(ConstantsDef.NEW_TASK_DESC);
            if (updatedTaskName != null) {
                if (updatedTaskName.equals(ConstantsDef.DELETE_TASK)) {
                    mHelper.deleteTask(o.toString(), updatedTaskName, taskID);
                } else {
                    mHelper.updateTask(o.toString(), updatedTaskName, taskID);
                }
            }
        }
        updateList();


        mTaskListView.setClickable(true);
        mTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int pos, long id) {
                o = mTaskListView.getItemAtPosition(pos);
                taskID = id;
                Intent i = new Intent(MainActivity.this, EditTaskActivity.class);
                i.putExtra(ConstantsDef.CURRENT_TASK_DESC, o.toString());
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

        final EditText taskEditText = new EditText(this);
        final TextView txtDueDateLabel = new TextView(this);


        if (item.getItemId() == R.id.action_add_task) {
            setTheme(0);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(ConstantsDef.ADD_NEW_TASK_TITLE)
                    .setView((taskEditText))
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String task = taskEditText.getText().toString();
                            if (task != null || task != "") {
                                mHelper.addTask(task);
                                updateList();
                            }
                            //Log.d(TAG, "Add new Task");
                        }
                    })

                    .setNegativeButton("Cancel", null)
                    .create();

            taskEditText.requestFocus();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }


    private void updateList() {
        int index;

        ArrayList<String> taskList = new ArrayList<>();
        Cursor dbCursor = mHelper.getAllTasks();

        if (dbCursor != null && dbCursor.getCount() > 0) {
            dbCursor.moveToFirst();
            index = dbCursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            taskList.add(dbCursor.getString(index));
        } else {
            //nothing in the database
            return;
        }


        while (dbCursor.moveToNext()) {
            if (dbCursor != null) {
                index = dbCursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
                taskList.add(dbCursor.getString(index));
            }
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
    }
}

