package com.projects.ttl.mytodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditTaskActivity extends AppCompatActivity {
    public String CURRENT_TASK_DESC = "CurrentTaskDesc";
    public String NEW_TASK_DESC = "NewTaskDesc";
    private String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        final EditText editTask = (EditText) (findViewById(R.id.edit_task_desc));
        Bundle bundle = getIntent().getExtras();
        str = (String) bundle.get(CURRENT_TASK_DESC);
        editTask.setText(str);
        editTask.requestFocus();
        Button updateButton = (Button) findViewById(R.id.update_button);
        Button cancelButton = (Button) findViewById(R.id.cancel_button);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTaskActivity.this, MainActivity.class);
                intent.putExtra(NEW_TASK_DESC, editTask.getText().toString());
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
