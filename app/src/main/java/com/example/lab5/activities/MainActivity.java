package com.example.lab5.activities;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.example.lab5.BreedItem;
import com.example.lab5.Item;

import com.example.lab5.adapters.MainAdapter;
import com.example.lab5.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_URL = "imageUrl";
    private String JSON_URL;
    private String JSON_URL2 = "https://api.thecatapi.com/v1/breeds?attach_breed=0";
    LinearLayoutManager manager;
    private RecyclerView mRecyclerView;
    private MainAdapter mExampleAdapter;
    private ArrayList<Item> mExampleList;
    private RequestQueue mRequestQueue, mRequestQueue1;
    Spinner spinner;
    boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    private Button nextPage, start;
    String[] cities = {"abys", "aege", "acur", "asho", "amau", "bali"};

    String[] ids;
    public List<BreedItem> items = new ArrayList<>();
    public List<BreedItem> breedItems = new ArrayList<>();

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRequestQueue = Volley.newRequestQueue(this);
        mRequestQueue1 = Volley.newRequestQueue(this);
        start = (Button) findViewById(R.id.start);
        spinner = (Spinner) findViewById(R.id.cities);
        nextPage = (Button) findViewById(R.id.next_btn);
        nextPage.setEnabled(false);
        start.setEnabled(false);
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSON_URL = "https://api.thecatapi.com/v1/images/search?breed_ids=" + ids[(int) spinner.getSelectedItemId()];
                populateData();
                initAdapter();
                addOnScroll();
            }
        });
        parseJSON2();
    }

    private void parseJSON2() {

        JsonArrayRequest request = new JsonArrayRequest(JSON_URL2, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        breedItems.add(new BreedItem(id, name));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                nextPage.setEnabled(true);
                start.setEnabled(true);
                ids = new String[breedItems.size()];
                String[] names = new String[breedItems.size()];
                for (int i = 0; i < breedItems.size(); i++) {
                    ids[i] = breedItems.get(i).getId();
                    names[i] = breedItems.get(i).getName();
                }
                initSpinnerAdapter(names);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        mRequestQueue1.add(request);
    }


    private void initSpinnerAdapter(String[] names) {
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void addOnScroll() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstCompletelyVisibleItemPosition();
                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    fetchData();
                }
            }
        });
    }

    private void initAdapter() {
        mExampleAdapter = new MainAdapter(MainActivity.this, mExampleList);
        mRecyclerView.setAdapter(mExampleAdapter);
    }

    private void fetchData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    parseJSON();
                }
            }
        }, 0);
    }

    private void populateData() {
        mExampleList = new ArrayList<>();
        int i = 0;
        while (i < 10) {
            parseJSON();
            i++;
        }

    }

    private void parseJSON() {
        JsonArrayRequest request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String imageUrl = jsonObject.getString("url");
                        mExampleList.add(new Item(imageUrl));
                        mExampleAdapter.notifyItemInserted(mExampleList.size() - 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        mRequestQueue.add(request);
    }
}
