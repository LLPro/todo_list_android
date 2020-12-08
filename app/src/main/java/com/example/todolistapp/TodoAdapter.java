package com.example.todolistapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import com.example.todolistapp.helperInterfaces.RefreshableDB;

import java.util.ArrayList;

public class TodoAdapter extends ArrayAdapter<Todo> {

    RefreshableDB refreshableDB;

    public TodoAdapter(@NonNull Context context, ArrayList<Todo> todoList) {
        super(context, R.layout.item, todoList);
        refreshableDB = (RefreshableDB) context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Todo todo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item, null);
        }
        ((TextView) convertView.findViewById(R.id.todo_text)).setText(todo.getName());
        SwitchCompat switchCompat = convertView.findViewById(R.id.todo_checked);
        switchCompat.setChecked(todo.isChecked());
        switchCompat.setOnClickListener(v -> {
            int value = switchCompat.isChecked()?1:0;
            refreshableDB.refresh(position, value);
        });

        return convertView;
    }
}
