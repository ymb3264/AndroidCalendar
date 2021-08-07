package com.example.calendarapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarAdapter extends RecyclerView.Adapter {

    private String[] dayList;
    private int year;
    private int month;
    private int day;

    public CalendarAdapter(String[] dayList, int year, int month, int day) {
        this.dayList = dayList;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.calendar_item, parent, false);
        CalendarAdapter.DateViewHolder ca = new CalendarAdapter.DateViewHolder(view);
        return ca;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String day_text = dayList[position];
        DateViewHolder dateViewHolder = (DateViewHolder) holder;
        dateViewHolder.day_item.setText(day_text);
    }

    @Override
    public int getItemCount() {
        return dayList.length;
    }

    public class DateViewHolder extends RecyclerView.ViewHolder {
        TextView day_item;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);

            day_item = itemView.findViewById(R.id.day_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getBindingAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        dayList[pos] = year + "/" + month + "/" + pos;

                        notifyItemChanged(pos);
                    }
                }
            });
        }
    }

    public class DiaryViewHolder extends RecyclerView.ViewHolder {

        public DiaryViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
