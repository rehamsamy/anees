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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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
    userinfo userInfo;
    private FirebaseDatabase db;
    private DatabaseReference root;
    private FirebaseAuth mAuth;
    LoginData loginData;

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
        btnLogin = (Button) findViewById(R.id.login);
        btnBack = (Button) findViewById(R.id.back);
        User = (EditText) findViewById(R.id.useredit);
        Password = (EditText) findViewById(R.id.passwordedit);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

    }



    public void sendToDB(View view) {
        String user = User.getText().toString();
        String password = Password.getText().toString();

        if ( TextUtils.isEmpty(user) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Please Fill in all the fields",
                    Toast.LENGTH_SHORT).show();
        } else {

            sendDataToFirebase( user,  password);

        }
    }

    private void sendDataToFirebase(String User,String Password) {
        userInfo.setUserUser(User);
        userInfo.setUserpassword(Password);


        mAuth.signInWithEmailAndPassword(User,Password )
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            Toast.makeText(LoginActivity.this, "you have been sign up successfully ", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }

}