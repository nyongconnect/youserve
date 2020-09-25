package com.devthrust.youserve.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devthrust.youserve.MainActivity;
import com.devthrust.youserve.R;
import com.devthrust.youserve.pojo.Events;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoAdapterViewHolder> {
    Context mContext;
    List<Events> mEventsList;

    public List<Events> getEventsList() {
        return mEventsList;
    }

    LayoutInflater mLayoutInflater;
    View view;
    public  TodoAdapter(Context context, List<Events> eventList){

        mContext = context;
        mEventsList = eventList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TodoAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = mLayoutInflater.inflate(R.layout.todo_xml, parent, false);
        return new TodoAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapterViewHolder holder, int position) {
        holder.mTitle.setText(mEventsList.get(position).getTitle());
        holder.mDate.setText(mEventsList.get(position).getDate() + "");
        holder.mPriority.setText(""+mEventsList.get(position).getPriority());
        holder.position = mEventsList.get(position).getId();

    }

    @Override
    public int getItemCount() {
        if (mEventsList != null)
            return mEventsList.size();
        return 0;
    }

    public void setTask(List<Events> events){
        mEventsList = events;
        notifyDataSetChanged();
    }
    class TodoAdapterViewHolder extends RecyclerView.ViewHolder {
        public int position;
        TextView mTitle, mDate, mPriority;

        public TodoAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tv_title);
            mDate = itemView.findViewById(R.id.tv_date);
            mPriority = itemView.findViewById(R.id.tv_priority);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    intent.putExtra(MainActivity.POSITION, position);
                    mContext.startActivity(intent);
                }
            });

        }
    }
}
