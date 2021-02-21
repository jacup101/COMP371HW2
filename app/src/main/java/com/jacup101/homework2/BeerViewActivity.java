package com.jacup101.homework2;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;

public class BeerViewActivity extends AppCompatActivity {
    TextView textView_name;
    TextView textView_abv;
    TextView textView_first;
    TextView textView_desc;
    TextView textView_pairs;
    TextView textView_tips;

    ImageView imageView_picture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beerview);
        Intent intent = getIntent();
        //Grab Text Views and Assign Corresponding Values
        textView_name = findViewById(R.id.textView_beerName);
        textView_name.setText(intent.getStringExtra("name"));

        textView_abv = findViewById(R.id.textView_beerABV);
        textView_abv.setText(intent.getStringExtra("abv"));

        textView_first = findViewById(R.id.textView_beerFB);
        textView_first.setText(intent.getStringExtra("fb"));

        textView_desc = findViewById(R.id.textView_beerDesc);
        textView_desc.setText(intent.getStringExtra("desc"));

        textView_pairs = findViewById(R.id.textView_beerFoodPair);
        textView_pairs.setText(intent.getStringExtra("pairings"));

        textView_tips = findViewById(R.id.textView_beerBTips);
        textView_tips.setText(intent.getStringExtra("tips"));

        //Grab Image View And Set It
        imageView_picture = findViewById(R.id.imageView_beer);
        String url = intent.getStringExtra("url");
        if(!url.equals("null")) {
            Picasso.get().load(url).into(imageView_picture);
        } else {
            try {
                InputStream ims = getAssets().open("alcohol.png");
                Drawable d = Drawable.createFromStream(ims, null);
                imageView_picture.setImageDrawable(d);
            } catch (IOException exception) {
                Log.e("alcohol_load", "Failed to load the image for alcohol");
            }
        }
    }
}
