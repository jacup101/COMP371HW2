package com.jacup101.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    TextView textView_amount;
    RecyclerView recyclerView;
    EditText search;
    ArrayList<Beer> beers = new ArrayList<Beer>();
    BeerAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = findViewById(R.id.recyclerView);
        textView_amount = findViewById(R.id.textView_amount);
        search = findViewById(R.id.search_bar);
        Intent intent = getIntent();
        beers = new ArrayList<Beer>();
        try {
            JSONArray beerList = new JSONArray(intent.getStringExtra("json"));
            for(int i = 0; i < beerList.length();i++) {
                JSONObject obj = beerList.getJSONObject(i);
                Beer beer = new Beer(obj.getString("name"),obj.getString("image_url"),obj.getString("description"),obj.getDouble("abv"),obj.getJSONArray("food_pairing"),obj.getString("brewers_tips"),obj.getString("first_brewed"));
                beers.add(beer);
            }
            adapter = new BeerAdapter(this,beers);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            textView_amount.setText("We found " + beers.size() + " results.");
            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String text = search.getText().toString();
                    int num;
                    if(text.length() == 0) {
                        num = adapter.updateList("");
                    } else {
                        num = adapter.updateList(text);
                    }
                    textView_amount.setText("We found " + num + " results.");
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
