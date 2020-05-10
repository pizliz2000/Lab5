package com.example.lab5.activities;



import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.lab5.Item;

import com.example.lab5.R;
import com.example.lab5.Singleton;
import com.example.lab5.adapters.MainAdapter;
import com.example.lab5.adapters.SecondAdapter;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

import static com.example.lab5.activities.MainActivity.EXTRA_URL;


public class DetailActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private SecondAdapter mExampleAdapter;
    private List<Item> mExampleList= Singleton.getInstance().getItems();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mRecyclerView = findViewById(R.id.liked_recycleView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mExampleAdapter = new SecondAdapter(DetailActivity.this, mExampleList);
        mRecyclerView.setAdapter(mExampleAdapter);
    }
}
