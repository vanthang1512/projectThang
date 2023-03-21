package com.example.fastfoodorderstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class LoginActivity extends AppCompatActivity {
    private EditText editEmail;
    private EditText editPassword;
    private Button buttonSignIn;
    // private Button buttonSignUp;
    private TextView textNoHaveAccount;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        // buttonSignUp = findViewById(R.id.buttonSignUp);
        textNoHaveAccount = findViewById(R.id.textNoHaveAccount);

        if (mAuth.getCurrentUser()!=null){
            Intent intent = new Intent(
                    LoginActivity.this,
                    MainActivity.class
            );
            startActivity(intent);
            finish();
        }
        buttonSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mAuth.signInWithEmailAndPassword(
                        editEmail.getText().toString(),
                        editPassword.getText().toString()
                ).addOnCompleteListener(new OnCompleteListener<AuthResult>(){

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Đăng nhập thành công",
                                    Toast.LENGTH_LONG
                            ).show();
                            Intent intent = new Intent(
                                    LoginActivity.this,
                                    MainActivity.class
                            );
                            startActivity(intent);
                            finish();

                        }
                        else{

                            Toast.makeText(getApplicationContext(),
                                    "Tài khoản không đúng",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        textNoHaveAccount.setOnClickListener(new View.OnClickListener() {//chuyển sáng đk
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        LoginActivity.this,
                        SinUpActivity.class
                );
                startActivity(intent);
            }
        });
    }
}