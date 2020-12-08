package com.example.todolistapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.todolistapp.R;
import com.example.todolistapp.helperInterfaces.CreatableTodo;

public class CreateFragment extends Fragment {

    CreatableTodo creatableTodo;



    public CreateFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        creatableTodo = (CreatableTodo) context;
    }

    public static CreateFragment newInstance(String param1, String param2) {
        return new CreateFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);
        EditText editText = (EditText) view.findViewById(R.id.create_todo_text);

        view.findViewById(R.id.save_todo).setOnClickListener(v -> {
            creatableTodo.save(editText.getText().toString());
            editText.setText("");
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            View activity_view = getActivity().getCurrentFocus();
            if (activity_view == null) {
                activity_view = new View(getActivity());
            }
            imm.hideSoftInputFromWindow(activity_view.getWindowToken(), 0);
        });
        return view;
    }
}