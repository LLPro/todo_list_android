package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.todolistapp.fragments.AllFragment;
import com.example.todolistapp.fragments.CreateFragment;
import com.example.todolistapp.fragments.SettingsFragment;
import com.example.todolistapp.helperClasses.DB;
import com.example.todolistapp.helperInterfaces.CreatableTodo;
import com.example.todolistapp.helperInterfaces.RefreshableDB;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Optional;

public class MainActivity extends AppCompatActivity implements CreatableTodo, RefreshableDB {

    FragmentManager fragmentManager;
    DB database;
    SQLiteDatabase sqLiteDatabase;
    String newTodo = null;
    private static ArrayList<Todo> todos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        init();
        fragmentManager = getSupportFragmentManager();
        callAllFragment(fragmentManager);

        database = new DB(this);
        sqLiteDatabase = database.getWritableDatabase();

        Log.d("Dbb",database.toString());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
            if (fragment != null) {
                switch (item.getItemId()) {
                    case R.id.all:
                        callAllFragment(fragmentManager);
                        return true;
                    case R.id.create:
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, new CreateFragment())
                                .addToBackStack(null)
                                .commit();
                        return true;
                    case R.id.settings:
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, new SettingsFragment())
                                .addToBackStack(null)
                                .commit();
                        return true;
                }
            }
            return false;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
        }
        if (database != null) {
            database.close();
        }
    }

    private static void init() {
        todos.add(new Todo("task1", false));
        todos.add(new Todo("task2", false));
        todos.add(new Todo("task3", false));
        todos.add(new Todo("task4", false));
        todos.add(new Todo("task5", false));
        todos.add(new Todo("task6", false));
        todos.add(new Todo("task7", false));
        todos.add(new Todo("task8", false));
        todos.add(new Todo("task9", false));
        todos.add(new Todo("task10", false));
        todos.add(new Todo("task11", false));
        todos.add(new Todo("task12", false));
        todos.add(new Todo("task13", false));
        todos.add(new Todo("task14", false));
        todos.add(new Todo("task15", false));
    }

    private static void callAllFragment(FragmentManager fragmentManager) {
        AllFragment allFragment = new AllFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("todos", todos);
        allFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, allFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void save(String todoName) {
        if (todoName != null) {
            Optional<Todo> todo = todos.stream().filter(e -> e.getName().equalsIgnoreCase(todoName)).findFirst();
            if (!todo.isPresent()) {
                newTodo = todoName;
                AddToDBTask progressTask = new AddToDBTask();
                progressTask.execute();
                todos.add(new Todo(todoName, false));
            }
        }
    }

    class AddToDBTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... unused) {
            if (!newTodo.equals("")) {
                sqLiteDatabase.execSQL(
                        "INSERT INTO TODO_TABLE (title, checked) VALUES ('" + newTodo + "','1')"
                );
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Added todo: " + newTodo, Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void refresh(int position, int value) {
        SyncDBTask syncDBTask = new SyncDBTask();
        syncDBTask.execute(position, value);
    }

    class SyncDBTask extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... integers) {
            int id = integers[0];
            int checked = integers[1];
            sqLiteDatabase.execSQL("UPDATE TODO_TABLE SET 'checked' = " + checked + " WHERE '_id' = " + id);
            return String.valueOf(id);
        }

        @Override
        protected void onPostExecute(String todo) {
            Toast.makeText(getApplicationContext(), "Refresh todo: " + todo, Toast.LENGTH_SHORT)
                    .show();
        }
    }
}