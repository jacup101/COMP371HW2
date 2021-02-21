package com.jacup101.homework2;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.ViewHolder> {

    private List<Beer> beers;
    private List<Beer> finalBeer;
    private Context context;
    public BeerAdapter(Context context, List<Beer> beers) {
        this.context = context;
        this.finalBeer = beers;
        this.beers = new ArrayList<Beer>();
        for(int i = 0; i < finalBeer.size(); i ++ ) {
            this.beers.add(finalBeer.get(i));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View beerView = inflater.inflate(R.layout.item_beer,parent,false);
        ViewHolder viewHolder = new ViewHolder(beerView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Beer beer = beers.get(position);
        holder.nameText.setText(beer.getName());
        holder.descText.setText(beer.getDesc());
        AssetManager assetManager = context.getAssets();


        //Load Favorite Image
        try {
            InputStream ims;
            if(!beer.isFavorited()) {
                ims = assetManager.open("unfavorite.png");
            }
            else {
                ims = assetManager.open("favorite.png");
            }
            Drawable d = Drawable.createFromStream(ims, null);
            holder.favorite.setImageDrawable(d);
        } catch (IOException exception) {
            Log.e("favorite_load","Failed to load the image for favorite");
        }
        //Load Beer Image
        if(!(beer.getPicture_url().equals("null"))) {
            Picasso.get().load(beer.getPicture_url()).into(holder.picture);
        } else {
            try {
                InputStream ims = assetManager.open("alcohol.png");
                Drawable d = Drawable.createFromStream(ims, null);
                holder.picture.setImageDrawable(d);
                holder.picture.setScaleType(ImageView.ScaleType.CENTER);
            } catch(IOException exception) {
                Log.e("alcohol_load","Failed to load the image for alcohol");
            }
        }

        Log.d("url",beer.getPicture_url());
    }

    @Override
    public int getItemCount() {
        return beers.size();
    }

    public int updateList(String text) {
        if(text.equals("") || text.length() == 0 ) {
            beers = new ArrayList<Beer>();
            for(int i = 0; i < finalBeer.size(); i ++ ) {
                beers.add(finalBeer.get(i));
            }
        } else {
            beers = new ArrayList<Beer>();
            for(int i = 0; i < finalBeer.size(); i ++ ) {
                if(finalBeer.get(i).getName().toLowerCase().contains(text.toLowerCase())) beers.add(finalBeer.get(i));
            }
        }
        notifyDataSetChanged();
        return beers.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView descText;
        ImageView favorite;
        ImageView picture;
        public ViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.textView_name);
            descText = itemView.findViewById(R.id.textView_description);
            favorite = itemView.findViewById(R.id.imageView_favorite);
            picture = itemView.findViewById(R.id.imageView_picture);

            //On Click Listeners
            favorite.setOnClickListener(v -> onFavorite(v));
            picture.setOnClickListener(v -> onImageClick(v));

        }
        public void onFavorite(View v) {
            Log.d("favorite","favorite clicked");
            int selectedBeer = getAdapterPosition();
            Beer beer = beers.get(selectedBeer);
            beer.setFavorited(!beer.isFavorited());
            notifyItemChanged(selectedBeer);
        }
        public void onImageClick(View v) {
            Log.d("image","image clicked");
            int selectedBeer = getAdapterPosition();
            Beer beer = beers.get(selectedBeer);
            Intent intent = new Intent(context,BeerViewActivity.class);
            intent.putExtra("name",beer.getName());
            intent.putExtra("abv","ABV: " + beer.getAbv() + "%");
            intent.putExtra("fb","First Brewed: " + beer.getFirst());
            if(!beer.getPicture_url().equals("null")) intent.putExtra("url",beer.getPicture_url());
            else intent.putExtra("url","null");
            intent.putExtra("desc","Description: " + beer.getDesc());
            intent.putExtra("pairings",beer.getFoodPairings());
            intent.putExtra("tips","Brewer\'s Tips: " + beer.getTips());

            context.startActivity(intent);
        }
    }

}
