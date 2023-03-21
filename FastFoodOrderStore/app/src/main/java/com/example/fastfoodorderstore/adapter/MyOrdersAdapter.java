package com.example.fastfoodorderstore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fastfoodorderstore.R;
import com.example.fastfoodorderstore.models.MyCartModel;
import com.example.fastfoodorderstore.models.MyOrdersModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.ViewHolder> {
    Context context;
    List<MyOrdersModel> cartModelList;
    int totalPrice = 0;
    TextView overTotalAmount;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public MyOrdersAdapter(Context context, List<MyOrdersModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_oders_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        NumberFormat formatter = new DecimalFormat("#,###");
        //double myNumber = 1000000;

        Glide.with(context).load(cartModelList.get(position).getCurrentImg()).into(holder.current_Img);

        holder.name.setText(cartModelList.get(position).getProductName());

        //String formattedNumber = cartModelList.get(position).getProductPrice();
        //formatter.format();
        holder.price.setText(cartModelList.get(position).getProductPrice());

        holder.date.setText(cartModelList.get(position).getCurrentDate());
        holder.time.setText(cartModelList.get(position).getCurrentTime());
        holder.quantity.setText(cartModelList.get(position).getTotalQuantity());
        holder.totalPrice.setText(String.valueOf(cartModelList.get(position).getTotalPrice()));



    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,price,date,time,quantity,totalPrice;

        ImageView deleteItem,current_Img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.products_name);
            price = itemView.findViewById(R.id.products_price);
            date = itemView.findViewById(R.id.current_date);
            time = itemView.findViewById(R.id.current_time);
            quantity = itemView.findViewById(R.id.total_quantity);
            totalPrice = itemView.findViewById(R.id.total_price);
            deleteItem = itemView.findViewById(R.id.delete);
            current_Img = itemView.findViewById(R.id.current_Img);

            overTotalAmount = itemView.findViewById(R.id.textView6);

        }
    }
}
