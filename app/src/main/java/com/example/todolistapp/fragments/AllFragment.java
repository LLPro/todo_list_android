package com.example.todolistapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolistapp.R;
import com.example.todolistapp.Todo;
import com.example.todolistapp.TodoAdapter;
import com.example.todolistapp.helperInterfaces.MainFragment;

import java.util.ArrayList;

public class AllFragment extends Fragment implements MainFragment {

    public AllFragment() {}
    public static AllFragment newInstance() {return new AllFragment();}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all, container, false);
        ListView listView = view.findViewById(R.id.list);

        if (getArguments() != null) {
            ArrayList<Todo> todoList = new ArrayList<>(getArguments().getParcelableArrayList("todos"));
            TodoAdapter adapter = new TodoAdapter(inflater.getContext(),todoList);
            listView.setAdapter(adapter);

        }


        return view;
    }


}