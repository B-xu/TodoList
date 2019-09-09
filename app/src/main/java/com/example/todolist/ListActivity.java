package com.example.todolist;

import androidx.fragment.app.Fragment;

public class ListActivity extends  SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new TodoListFragment();
    }
}
