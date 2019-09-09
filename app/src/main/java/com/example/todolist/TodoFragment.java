package com.example.todolist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Date;
import java.util.UUID;

public class TodoFragment extends Fragment {
    public static final int TARGET = 0;
    private static final String UUID_EXTRA = "uuid";
    private Todo mTodo;
    private EditText mTitleButton;
    private Button mDateButton;
    private Button mUrgencyButton;
    private Button mPomoSetButton;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String uuid = getActivity().getIntent().getStringExtra(UUID_EXTRA);
        mTodo = TodoList.get(getActivity()).getTodo(UUID.fromString(uuid));
    }

    public static Intent newIntent(Context context, String uuid){
        Intent intent  = new Intent(context, MainActivity.class);
        intent.putExtra(UUID_EXTRA, uuid);
        return intent;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.todo_fragment, container, false);

        mTitleButton = v.findViewById(R.id.activity_title);
        mTitleButton.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTodo.setName(charSequence.toString());
                TodoList.get(getContext()).updateTodo(mTodo);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDateButton = v.findViewById(R.id.activity_date);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                Dialog dialog = Dialog.newInstance(mTodo.getDate());
                dialog.setTargetFragment(TodoFragment.this,TARGET );
                dialog.show(fm, "date");
            }
        });


        mTitleButton.setText(mTodo.getName());
        mDateButton.setText(mTodo.getDate().toString());

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode!=0) {
            return;
        }
        if (resultCode == TARGET){
            Date date = (Date) data.getSerializableExtra(Dialog.EXTRA_DATE);
            mDateButton.setText(date.toString());
            mTodo.setDate(date);
            TodoList.get(getActivity()).updateTodo(mTodo);
        }
    }
}
