package com.jacup101.homework2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    ImageView imageView_alcohol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create The Image
        AssetManager assetManager = getAssets();
        imageView_alcohol = findViewById(R.id.imageView_alcohol);
        try {
            InputStream ims = assetManager.open("alcohol.png");
            Drawable d = Drawable.createFromStream(ims, null);
            imageView_alcohol.setImageDrawable(d);
        } catch (IOException ex) {
            return;
        }
    }

    public void startSearch(View v) {
        Intent intent = new Intent(this,SearchActivity.class);
        startActivity(intent);
    }
}