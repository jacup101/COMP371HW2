package com.jacup101.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    EditText editText_beer;
    EditText editText_startDate;
    EditText editText_endDate;
    Switch switch_highPoint;
    //Uses Android Volley asyng requests
    RequestQueue queue;

    private final String url = "https://api.punkapi.com/v2/beers";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editText_beer = findViewById(R.id.editText_beer);
        editText_startDate = findViewById(R.id.editText_startDate);
        editText_endDate = findViewById(R.id.editText_endDate);
        switch_highPoint = findViewById(R.id.switch_highPoint);
        queue = Volley.newRequestQueue(this);

    }

    public void startSearch(View view) {

        String dateStart = editText_startDate.getText().toString();
        String dateEnd = editText_endDate.getText().toString();

        //They Have Entered Date Criteria
        if(dateStart.length()!= 0 || dateEnd.length()!=0) {
            if (checkValidDate(dateStart) && checkValidDate(dateEnd)) {
                if (checkValidDates(dateStart, dateEnd)) {
                    //Send request with date params
                    sendReq(true,dateStart.replace('/','-'),dateEnd.replace('/','-'));
                } else {
                    Toast.makeText(this, "Invalid Date", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Invalid Date", Toast.LENGTH_SHORT).show();
            }
        } else {
            //Send request without date params
            sendReq(false,"null","null");
        }


    }
    private void sendReq(boolean addDate,String dateStart, String dateEnd) {
        //As far as I could tell, Android Volley does not have RequestParams like AsyncHttpClient, which wouldnt work with my phone for some reason
        //So instead, I have parsed the data using strings
        String beerName = editText_beer.getText().toString();
        beerName = beerName.replace(" ","_");
        String reqUrl = url;
        reqUrl += "?";
        if(beerName.length()!=0) {
            reqUrl += "&beer_name=" + beerName;
        }
        if(addDate) {
            reqUrl += "&brewed_before=" + dateEnd;
            reqUrl += "&brewed_after=" + dateStart;
        }
        //For Inclusivity, abv_gt > 3.99
        if(switch_highPoint.isChecked()) {
            reqUrl += "&abv_gt=3.99";
        } else {
            reqUrl += "&abv_lt=4.0";
        }

        reqUrl = reqUrl.substring(0,reqUrl.indexOf('?')+1) + reqUrl.substring(reqUrl.indexOf('?')+2);
        Log.d("url",reqUrl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, reqUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("json_response",response);
                        Intent intent = new Intent(SearchActivity.this,ListActivity.class);
                        intent.putExtra("json",response);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("connection_failed",":(");
                Toast.makeText(SearchActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);

    }
    private boolean checkValidDate(String dateText) {
        int month = 13;
        int year = 2021;
        if(dateText.length() != 7) {
            return false;
        }
        if(dateText.charAt(2) != '/') {
            return false;
        }
        try {
            month = Integer.parseInt(dateText.substring(0,2));
            year = Integer.parseInt(dateText.substring(3,7));
        } catch(Exception e) {
            return false;
        }
        return month <= 12 && year <= 2021;
    }
    private boolean checkValidDates(String dateStart, String dateEnd) {
        int monthStart;
        int monthEnd;
        int yearStart;
        int yearEnd;
        try {
            monthStart = Integer.parseInt(dateStart.substring(0,2));
            yearStart = Integer.parseInt(dateStart.substring(3,7));
            monthEnd = Integer.parseInt(dateEnd.substring(0,2));
            yearEnd = Integer.parseInt(dateEnd.substring(3,7));
        } catch(Exception e) {
            return false;
        }
        if(yearEnd < yearStart) return false;
        if(monthEnd < monthStart &&  yearEnd == yearStart) return false;
        return true;
    }
}
