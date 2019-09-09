package com.example.todolist;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

public class Todo {
    private String mName;
    private Date mDate;
    private UUID mUUID;
    private Boolean mDone;

    public Todo(){
        this(UUID.randomUUID());
    }

    public Todo(UUID id){
        mUUID = id;
        mDate = new Date();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public void setDone(Boolean done) {
        mDone = done;
    }
    public boolean getDone(){
        return mDone;
    }

    public UUID getUUID() {
        return mUUID;
    }

}
