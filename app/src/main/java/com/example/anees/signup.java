package com.example.anees;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signup extends AppCompatActivity {
    private EditText Name, User, Email, Password, Phone;
    private Button btnSend;
    private Button btnBack;
    userinfo userInfo;
    private FirebaseDatabase db;
    private DatabaseReference root;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        db = FirebaseDatabase.getInstance();
        root = db.getReference("Users");
        userInfo = new userinfo();
        Name = (EditText) findViewById(R.id.nameedit);
        User = (EditText) findViewById(R.id.useredit);
        Email = (EditText) findViewById(R.id.emailedit);
        Password = (EditText) findViewById(R.id.passwordedit);
        Phone = (EditText) findViewById(R.id.phoneedit);
        btnBack = (Button) findViewById(R.id.back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(signup.this,HomeActivity.class);
                startActivity(intent);
            }
        });


    }

    public void sendToDB(View view) {
        String name = Name.getText().toString();
        String user = User.getText().toString();
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        String phone = Phone.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(user) || TextUtils.isEmpty(password) || TextUtils.isEmpty(phone)) {
            Toast.makeText(signup.this, "Please Fill in all the fields",
                    Toast.LENGTH_SHORT).show();
        } else {
            if(email.contains(".")){
                int index=email.lastIndexOf(".");
                if(index>= 0){
                    email= email.substring(0 , index);
                }
            }
                registerUser(name, user, email, password, phone);
                sendDataToFirebase(name, user, email, password, phone);

        }
    }

    private void registerUser(String name, String user, String email, String password, String phone) {
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "Registration successful!",
                                            Toast.LENGTH_LONG)
                                    .show();
                            Intent intent
                                    = new Intent(signup.this,
                                    MainActivity.class);
                            startActivity(intent);
                        }
                        else {

                            // Registration failed
                            Toast.makeText(
                                            getApplicationContext(),
                                            "Registration failed!!"
                                                    + " Please try again later",
                                            Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress bar
                        }
                    }
                });
    }

    private void sendDataToFirebase(String name,String User,String email,String Password,String Phone) {
        userInfo.setUserName(name);
        userInfo.setUserUser(User);
        userInfo.setUserEmail(email);
        userInfo.setUserpassword(Password);
        userInfo.setUserphone(Phone);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                root.child(userInfo.getUserEmail()).setValue(userInfo);
                // after adding this data we are showing toast message.
                Toast.makeText(signup.this, "you have been sign up successfully ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(signup.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });


    }
}