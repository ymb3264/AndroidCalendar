package com.example.calendarapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private String[] dayList;
    private int year;
    private int month;
    private int day;
    private int emptyDay;
    private String androidID;
    private ArrayList<Memo> memoList;

    public CalendarAdapter(Context context, String[] dayList, int year, int month, int day, int emptyDay, String androidID, ArrayList<Memo> memoList) {
        this.dayList = dayList;
        this.year = year;
        this.month = month;
        this.day = day;
        this.mContext = context;
        this.emptyDay = emptyDay-1;
        this.androidID = androidID;
        this.memoList = memoList;
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
        String today = year + "-" + month + "-" + day_text;

        for(Memo m : memoList) {
            if (m.getDate().equals(today)) {
                    dateViewHolder.diary_item.setText(m.getContent());
            }
        }
    }

    @Override
    public int getItemCount() {
        return dayList.length;
    }

    public class DateViewHolder extends RecyclerView.ViewHolder {
        TextView day_item;
        TextView diary_item;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);

            day_item = itemView.findViewById(R.id.day_item);
            diary_item = itemView.findViewById(R.id.diary_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getBindingAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
//                        dayList[pos] = year + "/" + month + "/" + (pos-emptyDay);
//                        notifyItemChanged(pos);
//                        today = year + "-" + month + "-" + (pos-emptyDay);

                        Intent intent = new Intent(mContext, MemoWriteActivity.class);
                        intent.putExtra("year", year);
                        intent.putExtra("month", month);
                        intent.putExtra("day", pos-emptyDay);
                        intent.putExtra("androidID", androidID);

                        if(diary_item.getText().toString().equals("")) {
                            mContext.startActivity(intent);
                        } else {
                            intent.putExtra("memoContent", diary_item.getText().toString());
                            mContext.startActivity(intent);
                        }

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
