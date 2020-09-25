package com.devthrust.youserve.viewmodel;

import android.app.Application;

import com.devthrust.youserve.pojo.AppDatabase;
import com.devthrust.youserve.pojo.Events;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class HomeViewModel extends AndroidViewModel {
    private LiveData<List<Events>> mEventList;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(getApplication());
        mEventList = db.mEventDao().loadAllEvents();
    }

    public LiveData<List<Events>> getEventList() {
        return mEventList;
    }
}
