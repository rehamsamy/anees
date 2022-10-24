package com.example.anees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.jar.Attributes;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private Button btnBack;
    private EditText  User, Password;
    private FirebaseDatabase db;
    private DatabaseReference root;
    private FirebaseAuth mAuth;
    LoginData loginData;
     ProgressBar progressbar;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginData=new LoginData();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        db = FirebaseDatabase.getInstance();
        root = db.getReference("Users");
        progressbar = findViewById(R.id.progressBar);
        btnLogin = (Button) findViewById(R.id.login);
        btnBack = (Button) findViewById(R.id.back);
        User = (EditText) findViewById(R.id.emailedit);
        Password = (EditText) findViewById(R.id.passwordedit);
        Toast.makeText(LoginActivity.this, "you have been sign up successfully ", Toast.LENGTH_LONG).show();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = User.getText().toString();
                String password = Password.getText().toString();
                Log.d("TAG => ", user);

                if ( TextUtils.isEmpty(user) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Please Fill in all the fields",
                            Toast.LENGTH_LONG).show();
                } else {
                    if(user.contains(".")){
                        int index=user.lastIndexOf(".");
                        if(index>= 0){
                            user= user.substring(0 , index);
                        }
                    }
                    progressbar.setVisibility(View.VISIBLE);
                    sendDataToFirebase( user,  password);

                }

            }
        });

    }





     void sendDataToFirebase(String User,String Password) {
      loginData.setPassword(Password);
      loginData.setUser(User);
        mAuth.signInWithEmailAndPassword(User,Password )
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "you have been sign up successfully ", Toast.LENGTH_LONG).show();
                            progressbar.setVisibility(View.GONE);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                        } else {
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressbar.setVisibility(View.GONE);

                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG eror ", e.toString());
                    }
                });
        ;


    }

}