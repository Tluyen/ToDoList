package com.projects.ttl.mytodo;

import android.provider.BaseColumns;

/**
 * Created by Trang_2 on 9/10/2016.
 */
public class TaskContract {

    public static final String DB_NAME = "com.project.ttl.mytodo.db";
    public static final int DB_VERSION = 9;


    public class TaskEntry implements BaseColumns {
        public static final String COL_TASK_ID = "_ID";
        public static final String TABLE = "Tasks";
        public static final String COL_TASK_TITLE = "TaskTitle";

    }

}