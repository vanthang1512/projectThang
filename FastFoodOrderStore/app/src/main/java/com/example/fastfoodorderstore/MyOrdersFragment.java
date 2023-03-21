package com.example.fastfoodorderstore;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fastfoodorderstore.adapter.MyCartAdapter;
import com.example.fastfoodorderstore.adapter.MyOrdersAdapter;
import com.example.fastfoodorderstore.models.MyCartModel;
import com.example.fastfoodorderstore.models.MyOrdersModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class MyOrdersFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth auth;

    TextView overTotalAmount;

    RecyclerView recyclerView;
    MyOrdersAdapter cartAdapter;
    List<MyOrdersModel> cartModelList;

    int totalBill;

    public MyOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_orders, container, false);


        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();



        recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        overTotalAmount = root.findViewById(R.id.textView6);


        cartModelList = new ArrayList<>();
        cartAdapter = new MyOrdersAdapter(getActivity(),cartModelList);
        recyclerView.setAdapter(cartAdapter);

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("MyOrder").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                        String documentId = documentSnapshot.getId();

                        MyOrdersModel cartModel = documentSnapshot.toObject(MyOrdersModel.class);

                        cartModel.setDocumentId(documentId);

                        cartModelList.add(cartModel);
                        cartAdapter.notifyDataSetChanged();

                        recyclerView.setVisibility(View.VISIBLE);
                    }

                    caculateTotalAmount(cartModelList);
                }
            }
        });




        return root;
    }

    private void caculateTotalAmount(List<MyOrdersModel> cartModelList) {
        NumberFormat formatter = new DecimalFormat("#,###");


        double totalAmount = 0.0;
        for (MyOrdersModel myCartModel:cartModelList){
            totalAmount+=myCartModel.getTotalPrice();
        }
        String formattedNumber = formatter.format(totalAmount);
        overTotalAmount.setText("Tổng Giá:  " + formattedNumber + "VNĐ");
    }
}
