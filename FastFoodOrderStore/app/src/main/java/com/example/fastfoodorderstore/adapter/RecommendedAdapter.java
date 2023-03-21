package com.example.fastfoodorderstore.adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fastfoodorderstore.DetailedActivity;
import com.example.fastfoodorderstore.R;
import com.example.fastfoodorderstore.ViewAllActivity;
import com.example.fastfoodorderstore.ViewRecActivity;
import com.example.fastfoodorderstore.models.RecommendedModel;
import com.example.fastfoodorderstore.models.ViewAllModel;

import java.util.List;



public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder> {
   Context context;
   List<RecommendedModel> list;




    public RecommendedAdapter(Context context, List<RecommendedModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
      Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
      holder.name.setText(list.get(position).getName());
      holder.description.setText(list.get(position).getDescription());
      holder.rating.setText(list.get(position).getRating());
     // holder.price.setText(list.get(position).getPrice());
        holder.price.setText(list.get(position).getPrice()+"VNĐ");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewRecActivity.class);
                intent.putExtra("detail",list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,description,rating,price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.view_img);
            name = itemView.findViewById(R.id.view_name);
            description = itemView.findViewById(R.id.view_description);
            rating = itemView.findViewById(R.id.view_rating);
            price = itemView.findViewById(R.id.view_price);
        }
    }
}
