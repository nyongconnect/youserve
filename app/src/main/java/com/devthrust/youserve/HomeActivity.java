package com.devthrust.youserve;

import android.content.Intent;
import android.os.Bundle;

import com.devthrust.youserve.adapter.TodoAdapter;
import com.devthrust.youserve.data.DataManager;
import com.devthrust.youserve.executor.AppExecutor;
import com.devthrust.youserve.pojo.AppDatabase;
import com.devthrust.youserve.pojo.Events;
import com.devthrust.youserve.viewmodel.HomeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<Events> mEventsList;
    DataManager mDataManager;
    private TodoAdapter mTodoAdapter;
    AppDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = findViewById(R.id.rv_todo);
        mDataManager = DataManager.getInstance();
        mEventsList = mDataManager.insertDummyTodo();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mTodoAdapter = new TodoAdapter(this, mEventsList);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mTodoAdapter);
        mDatabase = AppDatabase.getInstance(getApplicationContext());
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutor.sInstance(getApplicationContext()).getDiskIo().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        mEventsList = mTodoAdapter.getEventsList();
                        Events events = mEventsList.get(position);
                        AppDatabase.getInstance(getApplicationContext()).mEventDao().deleteEvent(events);
                    }
                });


            }
        }).attachToRecyclerView(mRecyclerView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               launchMainActivity();
            }
        });
        retrieveData();
    }

    private void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void retrieveData() {
        HomeViewModel viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        viewModel.getEventList().observe(this, new Observer<List<Events>>() {
            @Override
            public void onChanged(List<Events> events) {
                mTodoAdapter.setTask(events);
            }
        });


    }
}