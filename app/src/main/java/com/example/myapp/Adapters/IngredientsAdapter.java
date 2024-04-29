package com.example.myapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsViewHolder> {
    Context context;
    List<String> list;

    public IngredientsAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_meal_ingredients, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        holder.textView_ingredients_name.setText(list.get(position));
        holder.textView_ingredients_name.setSelected(true);
        //Picasso.get().load(list.get(position)).into(holder.imageView_ingredients_image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class IngredientsViewHolder extends RecyclerView.ViewHolder {
    TextView textView_ingredients_name;
    ImageView imageView_ingredients_image;
    public IngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_ingredients_name = itemView.findViewById(R.id.textView_ingredient_name);
        imageView_ingredients_image = itemView.findViewById(R.id.imageView_ingredient);
    }
}
