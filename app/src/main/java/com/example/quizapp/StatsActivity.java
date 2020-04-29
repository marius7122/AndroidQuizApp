package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // get list of scores from db
        dbHelper = new DatabaseHelper(this);
        Cursor data = dbHelper.getData();
        ArrayList<Score> listData = new ArrayList<>();
        while(data.moveToNext()){
            Score s = new Score();
            s.category = data.getString(1);
            s.difficulty = data.getString(2);
            s.score = data.getString(3);
            listData.add(s);
        }

        MyAdapter myAdapter = new MyAdapter(listData);
        recyclerView.setAdapter(myAdapter);
    }
}
