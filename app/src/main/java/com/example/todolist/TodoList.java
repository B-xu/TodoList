package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import database.TodoBaseHelper;
import database.TodoSchema;
import database.TodoSchema.TodoTable;

public class TodoList {

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private static TodoList mTodoList;

    public static TodoList get(Context context){
        if (mTodoList == null){
            mTodoList = new TodoList(context);
        }

        return mTodoList;

    }

    private TodoList(Context context){
        mContext =context.getApplicationContext();
        mDatabase = new TodoBaseHelper(mContext).getWritableDatabase();
    }

    public void addTodo(Todo t){
        ContentValues contentValues= getContentValues(t);
        mDatabase.insert(TodoTable.NAME, null, contentValues);
    }

    public void updateTodo(Todo t){
        ContentValues contentValues= getContentValues(t);
        String uuidstring = t.getUUID().toString();
        mDatabase.update(TodoTable.NAME, contentValues, TodoTable.cols.UUID + " = ?",
                new String[]{uuidstring});
    }

    private static ContentValues getContentValues(Todo t){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TodoTable.cols.UUID, t.getUUID().toString());
        contentValues.put(TodoTable.cols.TITLE, t.getName());
        contentValues.put(TodoTable.cols.DATE, t.getDate().getTime());
        return contentValues;
    }
    public List<Todo> getTodos(){
        List<Todo> todos = new ArrayList<>();
        TodoCursorWrapper cursor = queryCrimes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                todos.add(cursor.getTodo());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return todos;
    }

    public Todo getTodo(UUID uuid){
        TodoCursorWrapper cursor = queryCrimes(TodoTable.cols.UUID+" =?",
                new String[]{uuid.toString()});
        try {
            if (cursor.getCount()==0) {
                return null;
            } else{
                cursor.moveToFirst();
                return cursor.getTodo();
            }
        } finally {
            cursor.close();
        }
    }

    private TodoCursorWrapper queryCrimes(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(TodoTable.NAME,null, whereClause,
                whereArgs, null, null, null);
        return new TodoCursorWrapper(cursor);
    }
}
