package com.example.todolist;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.UUID;


public class TodoListFragment extends Fragment {
    public RecyclerView mRecyclerView;
    private TodoAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.todolistfragment, null, false);
        mRecyclerView = v.findViewById(R.id.todo_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return v;
    }

    private void updateUI() {
        TodoList todoList = TodoList.get(getActivity());
        List<Todo> todos= todoList.getTodos();

        if (mAdapter == null) {
            mAdapter = new TodoAdapter(todos);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setCrimes(todos);
            mAdapter.notifyDataSetChanged();
        }


    }

    private class TodoHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{

        private ImageButton mPomoButton;
        private TextView mTitleView;
        private TextView mDateView;
        private Todo mTodo;

        public TodoHolder(LayoutInflater inflater, @NonNull ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_todo,parent, false));
            itemView.setOnClickListener(this);
            mPomoButton = itemView.findViewById(R.id.imageButton);
            mTitleView = itemView.findViewById(R.id.list_title);
            mDateView = itemView.findViewById(R.id.list_date);
        }

        public void bind(Todo todo){
            mTodo = todo;
            mTitleView.setText(todo.getName());
            mDateView.setText(todo.getDate().toString());

            mPomoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mTitleView.setPaintFlags(mTitleView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    startActivity(new Intent(getActivity(),PomodoroActivity.class));
                }
            });
        }

        @Override
        public void onClick(View view) {
           Intent intent = TodoFragment.newIntent(getContext(), mTodo.getUUID().toString());
           startActivity(intent);

        }
    }

    private class TodoAdapter extends RecyclerView.Adapter<TodoHolder>{
        private List<Todo> mTodos;

        public TodoAdapter(List<Todo> todoList){
            mTodos = todoList;
        }
        @NonNull
        @Override
        public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater =LayoutInflater.from(getActivity());
            return new TodoHolder(inflater, parent);
        }


        @Override
        public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
            Todo todo  = mTodos.get(position);
            holder.bind(todo);

        }

        @Override
        public int getItemCount() {
            return mTodos.size();
        }

        public void setCrimes(List<Todo> todos) {
            mTodos = todos;    }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_todo:
                TodoList todoList = TodoList.get(getContext());
                Todo todo = new Todo();
                todoList.addTodo(todo);
                Intent intent = TodoFragment.newIntent(getContext(), todo.getUUID().toString());
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}
