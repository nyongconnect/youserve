package com.devthrust.youserve.executor;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.MainThread;

public class AppExecutor {
    public static final Object LOCK = new Object();
    public static AppExecutor sInstance;
    private final Executor diskIo;
    private final Executor networkIo;
    private final Executor mainThread;

    private AppExecutor(Executor diskIo, Executor networkIo, Executor mainThread){
        this.diskIo = diskIo;
        this.mainThread = mainThread;
        this.networkIo = networkIo;
    }

    public static AppExecutor sInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new AppExecutor(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3), new MainThreadExecutor());
            }
        }
        return sInstance;

    }

    public Executor getDiskIo() {
        return diskIo;
    }

    public Executor getNetworkIo() {
        return networkIo;
    }

    public Executor getMainThread() {
        return mainThread;
    }

    private static class MainThreadExecutor implements Executor{
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable runnable) {
            mainThreadHandler.post(runnable);
        }
    }
}
