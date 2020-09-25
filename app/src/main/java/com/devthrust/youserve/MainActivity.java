package com.devthrust.youserve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.devthrust.youserve.data.DataManager;
import com.devthrust.youserve.executor.AppExecutor;
import com.devthrust.youserve.pojo.AppDatabase;
import com.devthrust.youserve.pojo.DateConverter;
import com.devthrust.youserve.pojo.EventDao;
import com.devthrust.youserve.pojo.Events;
import com.devthrust.youserve.viewmodel.AddTaskFactory;
import com.devthrust.youserve.viewmodel.AddTaskViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    public static final String POSITION = "com.devthrust.youserve.position";
    private final int PRIORITY_HIGH = 1;
    private final int PRIORITY_MEDIUM = 2;
    private final int PRIORITY_LOW = 3;

    private int position = -1;
    EditText mDescription;
    Button mApplyChanges;
    RadioButton mRadioButtonHigh, mRadioButtonMedium, mRadioButtonLow;
    AppDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDb = AppDatabase.getInstance(getApplicationContext());
        Intent intent = getIntent();
        initFields();
        position =  intent.getIntExtra(POSITION, -1);
        if (position != -1){
            readItemForUpdate(position);
            mApplyChanges.setText(getResources().getString(R.string.update));
        }

        
        mApplyChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Events event = getInputFromUser();
                AppExecutor.sInstance(getApplicationContext()).getDiskIo().execute(new Runnable() {
                    @Override
                    public void run() {
                        applyChanges(event);
                    }
                });

                finish();
            }
        });

    }

    private void applyChanges(Events events) {
        if (position!= -1){
            events.setId(position);
            mDb.mEventDao().updateEvent(events);

        }
        else {
            mDb.mEventDao().insertEvent(events);

        }
    }

    private Events getInputFromUser() {
        String description = mDescription.getText().toString();
        int priority = getPriority();
        Date date = new Date();
        Events events =new Events(description, date, priority);
        return events;
    }

    private int getPriority() {
        int priority = 0;
        if (mRadioButtonHigh.isChecked()){
            priority = PRIORITY_HIGH;
        }
        else if (mRadioButtonMedium.isChecked()){
            priority=PRIORITY_MEDIUM;
        }
        else if (mRadioButtonLow.isChecked()){
            priority = PRIORITY_LOW;
        }
        return priority;
    }

    private void initFields() {
        mDescription = findViewById(R.id.et_description);
        mRadioButtonHigh = findViewById(R.id.rb_high);
        mRadioButtonLow = findViewById(R.id.rb_low);
        mRadioButtonMedium = findViewById(R.id.rb_medium);
        mApplyChanges = findViewById(R.id.btn_add);
    }

    private void readItemForUpdate(final int position) {

        AddTaskFactory factory = new AddTaskFactory(mDb, position);
        final AddTaskViewModel viewModel = ViewModelProviders.of(this, factory).get(AddTaskViewModel.class);
                viewModel.getLiveData().observe(this, new Observer<Events>() {
                    @Override
                    public void onChanged(Events event) {
                        viewModel.getLiveData().removeObserver(this);
                        loadData(event);
                    }
                });

    }

    private void loadData(Events events) {
       mDescription.setText(events.getTitle());
       int priority = events.getPriority();
       switch (priority){
           case PRIORITY_HIGH:
               mRadioButtonHigh.setChecked(true);
               break;
           case PRIORITY_MEDIUM:
               mRadioButtonMedium.setChecked(true);
               break;
           case PRIORITY_LOW:
               mRadioButtonLow.setChecked(true);
               break;
       }


    }
}