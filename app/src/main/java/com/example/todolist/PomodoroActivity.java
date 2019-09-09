package com.example.todolist;

import androidx.fragment.app.Fragment;

public class PomodoroActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new PomodoroFragment();
    }
}
