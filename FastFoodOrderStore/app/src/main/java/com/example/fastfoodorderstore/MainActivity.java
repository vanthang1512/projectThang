package com.example.fastfoodorderstore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fastfoodorderstore.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding; 
    FirebaseDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();

//        StorageReference storageReference  = FirebaseStorage.getInstance().getReference().child(retrievedName).child("images/profile_image");

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;//ràng buột ngăn kéo layout
        NavigationView navigationView = binding.navView;// ràng buột điều hướng ngxem
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations



        mAppBarConfiguration = new AppBarConfiguration.Builder(//tạo mới và xây dựng cấu hình thanh ứng dụng
                R.id.nav_home, R.id.nav_category, R.id.nav_profile,R.id.nav_offers,R.id.nav_new_products,
                R.id.nav_my_orders,R.id.nav_my_carts)
                .setOpenableLayout(drawer)//đặt bố cục có thể mở vô trong
                .build();//xây dựng
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);//bộ điều hướng tìm đến trang content
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);//thiết lập cấu hình thanh giao diện ứng dụng
        NavigationUI.setupWithNavController(navigationView, navController);//thiết lập giao diện với bộ điều khiển(điều hướng ng xem)

        View headerView = navigationView.getHeaderView(0);//xem tiêu đề
        TextView headerName = headerView.findViewById(R.id.nav_header_name);//tìm tên  tiêu đề nav_header_main
        TextView headerEmail = headerView.findViewById(R.id.nav_header_email);//tìm email  tiêu đề nav_header_main
        CircleImageView headerImg = headerView.findViewById(R.id.nav_header_img);//tìm hình ảnh  tiêu đề nav_header_main

        database.getReference().child("account").child(FirebaseAuth.getInstance().getUid())//lấy dữ liệu trong account
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Acount account = snapshot.getValue(Acount.class);

                        headerName.setText(account.getUsername());//cập nhật và lấy dữu liệu trong account
                        headerEmail.setText(account.getEmail());//cập nhật và lấy dữu liệu trong account


                        Glide.with(MainActivity.this).load(account.getPhoto()).into(headerImg);//lưới bắt lấy ảnh
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Thổi phồng menu; điều này sẽ thêm các mục vào thanh hành động nếu nó hiện diện.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);//tìm đên content_main
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)//điều hướng lên giao diện cấu hình thanh ưng dụng
                || super.onSupportNavigateUp();//hỏ trợ điều hướng
    }



}