package com.example.calendarapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MemoWriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_write);

        TextView today = (TextView)findViewById(R.id.today);
        Button inputMemoBtn = (Button)findViewById(R.id.inputMemoBtn);
        EditText inputMemo = (EditText)findViewById(R.id.inputMemo);
        Button deleteMemoBtn = (Button)findViewById(R.id.deletetMemoBtn);

        Intent intent = getIntent();
        int year = intent.getIntExtra("year", 0);
        int month = intent.getIntExtra("month", 0);
        int day = intent.getIntExtra("day", 0);
        String androidID = intent.getStringExtra("androidID").toString();

        String memoContent = null;
        if(intent.getStringExtra("memoContent") != null) {
            memoContent = intent.getStringExtra("memoContent").toString();
            inputMemo.setText(memoContent);
            inputMemoBtn.setText("수정하기");
            deleteMemoBtn.setVisibility(View.VISIBLE);
        }

        String date = year + "-" + month + "-" + day;

        today.setText(year + "." + month + "." + day);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference();

        inputMemoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Memo memo = new Memo();
                String inputText = inputMemo.getText().toString();
                String date = year + "-" + month + "-" + day;
                String m = "memo" + date;
                memo.setDate(date);
                memo.setContent(inputText);

                dbRef.child(androidID).child(m).setValue(memo);

                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent2);
            }
        });

        deleteMemoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = year + "-" + month + "-" + day;
                String m = "memo" + date;

                dbRef.child(androidID).child(m).child("content").setValue("");
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent2);
            }
        });
    }
}