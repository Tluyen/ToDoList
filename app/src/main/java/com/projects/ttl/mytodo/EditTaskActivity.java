package com.projects.ttl.mytodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        final EditText editTask = (EditText) (findViewById(R.id.edit_task_desc));
        String updateTaskName = editTask.getText().toString();
        Button updateButton = (Button) findViewById(R.id.update_button);
        Button cancelButton = (Button) findViewById(R.id.cancel_button);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTaskActivity.this, MainActivity.class);
                intent.putExtra("Update Task Name", editTask.getText().toString());
                startActivity(intent);
                finish();
            }
        });

        cancelButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }));


    }

    public void EditTask() {

    }


}
