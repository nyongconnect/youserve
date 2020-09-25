package com.devthrust.youserve.viewmodel;

import com.devthrust.youserve.pojo.AppDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AddTaskFactory extends ViewModelProvider.NewInstanceFactory {
    private final AppDatabase mDatabase;
    private final int mEventId;

    public AddTaskFactory(AppDatabase db, int id){
        mDatabase = db;
        mEventId = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddTaskViewModel(mDatabase, mEventId);
    }
}
