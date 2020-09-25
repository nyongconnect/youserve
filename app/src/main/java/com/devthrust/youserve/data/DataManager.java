package com.devthrust.youserve.data;

import com.devthrust.youserve.pojo.Events;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    static DataManager mDataManager;
    List<Events> mEvents = new ArrayList<>();
    private DataManager(){}

    public static DataManager getInstance(){

        if (mDataManager ==null){
            mDataManager = new DataManager();
        }
        return mDataManager;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public List<Events> insertDummyTodo(){
        if (mEvents.size() < 1){
        mEvents.add(new Events("Eat", null, 1));
        mEvents.add(new Events("Sleep", null, 2));
        mEvents.add(new Events("drink", null,3));
        }
        return mEvents;
    }

    public  Events getEvent(int position){
        Events events = mEvents.get(position);
        return events;

    }

    public int updateEvent(int position, Events events){
        mEvents.set(position,events);
        return position;
    }

    public int addEvent(Events events) {
        mEvents.add(events);
        return mEvents.size() - 1;
    }

    public int delete(int position){
        mEvents.remove(position);
        return position;
    }
}
