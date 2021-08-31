package com.example.calendarapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Member;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private int year;
    private int month;
    private int day;
    private RecyclerView recyclerView;
    private String uniqueID;
    ArrayList<Memo> memoList = new ArrayList<Memo>();
    private String androidID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference(androidID);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                memoList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Memo m = dataSnapshot.getValue(Memo.class);

                    memoList.add(m);
                }
                showCalendar();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("error", "error");
            }
        });

        uniqueID = UUID.randomUUID().toString();
        Date time = new Date();
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
        SimpleDateFormat formatDay = new SimpleDateFormat("dd");

        recyclerView = (RecyclerView)findViewById(R.id.calendar);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 7));

        year = Integer.parseInt(formatYear.format(time));
//        int month = Calendar.AUGUST;
        month = Integer.parseInt(formatMonth.format(time));
        day = Integer.parseInt(formatDay.format(time));

        Spinner yearSpin =  (Spinner)findViewById(R.id.year_spin);
        Spinner monthSpin =  (Spinner)findViewById(R.id.month_spin);

        yearSpin.setSelection(getIndex(yearSpin));
        monthSpin.setSelection(getIndex(monthSpin));

        yearSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = Integer.parseInt((String)parent.getItemAtPosition(position));
                day = 1;

                showCalendar();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        monthSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = Integer.parseInt((String)parent.getItemAtPosition(position));
                day = 1;

                showCalendar();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        showCalendar();
    }

    public void showCalendar() {
        Calendar cal = new GregorianCalendar(year, month-1, day);
        int dayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1;
        String[] dayList = new String[dayOfMonth+dayOfWeek];

//        System.out.println(dayOfMonth);

        for(int i = 1; i <= dayList.length; i++) {
            if(i <= dayOfWeek) {
                dayList[i-1] = "";
            } else {
                dayList[i-1] = String.valueOf(i-dayOfWeek);
            }
        }

        CalendarAdapter calendarAdapter = new CalendarAdapter(this, dayList, year, month, day, dayOfWeek, androidID, memoList);
        recyclerView.setAdapter(calendarAdapter);
    }

    public int getIndex(Spinner spinner) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (year == Integer.parseInt(spinner.getItemAtPosition(i).toString())) {
                return i;
            } else if(month == Integer.parseInt(spinner.getItemAtPosition(i).toString())) {
                return i;
            }
        }
        return 0;
    }





























}