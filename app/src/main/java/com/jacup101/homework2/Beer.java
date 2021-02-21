package com.jacup101.homework2;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

public class Beer {
    private String name;
    private String picture_url;
    private boolean favorited = false;
    private String desc;
    double abv;
    JSONArray foodPairing;
    String tips;
    String first;
    public Beer(String name, String picture_url, String desc,double abv, JSONArray foodPairing, String tips,String first) {
        this.name = name;
        this.picture_url = picture_url;
        this.desc = desc;
        this.abv = abv;
        this.foodPairing = foodPairing;
        this.tips = tips;
        this.first = first;
    }

    public String getName() {
        return name;
    }

    public double getAbv() {
        return abv;
    }

    public void setAbv(double abv) {
        this.abv = abv;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture_url() {
        if(picture_url == null) return "null";
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFoodPairings() {
        String pairings = "Food Pairings: ";
        for(int i = 0; i < foodPairing.length(); i++) {
            try {
                pairings += foodPairing.getString(i);
                if(i != foodPairing.length()-1) pairings += ", ";
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("beer_pairing","Loaded an improper food pairing");
            }

        }
        return pairings;
    }
}
