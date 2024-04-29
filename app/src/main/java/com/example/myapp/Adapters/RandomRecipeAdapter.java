package com.example.myapp.Adapters;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx. annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.Model.recipes;
import com.example.myapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RandomRecipeAdapter extends RecyclerView.Adapter<RandomRecipeViewHolder>{
    Context context;
    List<recipes> list;

    public RandomRecipeAdapter(Context context, List<recipes> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RandomRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RandomRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.random_recipes_list,parent,false));
    }

//    @Override
//    public void onBindViewHolder(@NonNull RandomRecipeViewHolder holder, int position) {
//        holder.textView_title.setText(list.get(position).title);
//        holder.textView_rating.setText((int) list.get(position).rating);
//        Picasso.get().load(list.get(position).image).into (holder.imageView_food);
//        //holder.imageView_food.setImageResource(R.drawable.food);
//    }

    @Override
    public void onBindViewHolder(@NonNull RandomRecipeViewHolder holder, int position) {
        holder.textView_title.setText(list.get(position).title);
        holder.textView_rating.setText(String.valueOf(list.get(position).rating)); // Convert int to String
        Picasso.get().load(list.get(position).image).into(holder.imageView_food);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
class RandomRecipeViewHolder extends RecyclerView.ViewHolder {
    CardView randon_list_container;
    TextView textView_title, textView_rating;
    ImageView imageView_food;
    public RandomRecipeViewHolder (@NonNull View itemView) {
        super(itemView);
        randon_list_container = itemView.findViewById(R.id.random_list_container);
        textView_title = itemView.findViewById(R.id.textView_title);
        textView_rating = itemView.findViewById(R.id.textView_rating);
        imageView_food = itemView.findViewById(R.id.imageView_food);
    }
}