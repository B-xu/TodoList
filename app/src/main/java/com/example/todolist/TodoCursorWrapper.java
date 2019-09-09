package com.example.todolist;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

import database.TodoSchema;
import database.TodoSchema.TodoTable.cols;

public class TodoCursorWrapper extends CursorWrapper {
    public TodoCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Todo getTodo(){
        String uuidString  = getString(getColumnIndex(cols.UUID));
        String name =getString(getColumnIndex(cols.TITLE));
        long date = getLong(getColumnIndex(cols.DATE));
        int pomos = getInt(getColumnIndex(cols.POMOS));
        int urgency = getInt(getColumnIndex(cols.URGENCY));

        Todo t = new Todo(UUID.fromString(uuidString));
        t.setDate(new Date(date));
        t.setName(name);

        return t;

    }
}
