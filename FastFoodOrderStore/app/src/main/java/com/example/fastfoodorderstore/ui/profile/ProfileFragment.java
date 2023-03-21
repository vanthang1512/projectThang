package com.example.fastfoodorderstore.ui.profile;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.app.appsearch.StorageInfo;
import android.content.Intent;
import android.location.Criteria;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.fastfoodorderstore.Acount;
import com.example.fastfoodorderstore.LoginActivity;
import com.example.fastfoodorderstore.MainActivity;
import com.example.fastfoodorderstore.R;
import com.example.fastfoodorderstore.SinUpActivity;
import com.example.fastfoodorderstore.databinding.FragmentHomeBinding;
import com.example.fastfoodorderstore.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private Button DangXuat;



    ImageView profileImg;
    EditText name,email,number,address;
    Button update;
    DatabaseReference refAccount;
    //StorageReference storage;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;
   // private final int REQUEST_SELECT_PHOTO = 1;
  //  private static Uri pathPhotoFromDevice;
  // FirebaseFirestore db = FirebaseFirestore.getInstance();

//    private FirebaseAuth mAuth;
//    private FirebaseDatabase mDatabase;
//    private DatabaseReference refAccount;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //StorageReference storageReference  = FirebaseStorage.getInstance().getReference().child(retrievedName).child("images/profile_image");

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();




        profileImg = root.findViewById(R.id.profile_img);
        name = root.findViewById(R.id.profile_name);
        email = root.findViewById(R.id.profile_email);
        number = root.findViewById(R.id.profile_number);
        address = root.findViewById(R.id.profile_address);
        update = root.findViewById(R.id.update);
        DangXuat = root.findViewById(R.id.Sigout);

        DangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mauth = FirebaseAuth.getInstance();
                mauth.signOut();
                Intent intent = new Intent(
                        getContext(),
                        LoginActivity.class
                );
                startActivity(intent);
            }
        });

        database.getReference().child("account").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Acount account = snapshot.getValue(Acount.class);
                        Glide.with(getContext()).load(account.getPhoto()).into(profileImg);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,33);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserProfile();

            }
        });


        return root;
    }


    private void updateUserProfile() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("username", name.getText().toString());
        hashMap.put("phone", number.getText().toString());
        hashMap.put("address", address.getText().toString());
       // hashMap.put("email", email.getText().toString());

        refAccount = database
                .getReference()
                .child("account");

        refAccount.child(auth.getCurrentUser().getUid())
                .updateChildren(hashMap);

        Toast.makeText(getContext(),
                " Lưu Thành Công",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (auth.getCurrentUser() != null) {
            refAccount = database
                    .getReference()
                    .child("account");
            refAccount.child(auth.getCurrentUser().getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Acount account = snapshot.getValue(Acount.class);
                            if (account != null) {
                                email.setText(account.getEmail());
                                name.setText(account.getUsername());
                                number.setText(account.getPhone());
                                address.setText(account.getAddress());
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData()!=null){
            Uri profileUri = data.getData();
            profileImg.setImageURI(profileUri);
            final StorageReference storageRef = storage.getReference().child("profile_picture")
                    .child(FirebaseAuth.getInstance().getUid());

            storageRef.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();

                   storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                       @Override
                       public void onSuccess(Uri uri) {
                           database.getReference().child("account").child(FirebaseAuth.getInstance().getUid())
                                   .child("photo").setValue(uri.toString());
                           Toast.makeText(getContext(), "ProFile Picture Update", Toast.LENGTH_SHORT).show();
                       }
                   });
                }


            });
            //StorageReference storageReference  = FirebaseStorage.getInstance().getReference().child("").child("images/profile_image");
        }


    }
}

