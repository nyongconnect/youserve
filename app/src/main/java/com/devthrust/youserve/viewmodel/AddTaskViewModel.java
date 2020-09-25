package com.devthrust.youserve.viewmodel;

import com.devthrust.youserve.pojo.AppDatabase;
import com.devthrust.youserve.pojo.Events;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AddTaskViewModel extends ViewModel {
    private LiveData<Events> mLiveData;

    public AddTaskViewModel(AppDatabase appDatabase, int eventId) {
        mLiveData = appDatabase.mEventDao().loadEvent(eventId);

    }

    public LiveData<Events> getLiveData() {
        return mLiveData;
    }
}
