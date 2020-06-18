package com.example.hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        Button login=(Button)findViewById(R.id.loginbutton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUp.this, LoginPage.class);
                startActivity(intent);
            }
        });
        Button signup=(Button)findViewById(R.id.submitsp);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password,repassword;
                email=((EditText)findViewById(R.id.email)).getText().toString();
                password=((EditText)findViewById(R.id.password)).getText().toString();
                repassword=((EditText)findViewById(R.id.retypepassword)).getText().toString();
                if(!repassword.equals(password)){
                    Toast.makeText(SignUp.this, "Passwords don't match",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                final String name=((EditText)findViewById(R.id.name)).getText().toString();
                final String phoneNo=((EditText)findViewById(R.id.mobilenumber)).getText().toString();
                final String dob=((EditText)findViewById(R.id.dob)).getText().toString();
                final String usertype=((EditText)findViewById(R.id.usertype1)).getText().toString();
                System.out.println(name);
                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user,name,phoneNo,dob,usertype);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignUp.this, task.getException().toString(),
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                            }
                        });
            }
        });

    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }
    private void updateUI(FirebaseUser User,String name,String phoneNo,String dob,String usertype){

        String email=User.getEmail();
        String[] data={email,name,phoneNo,dob};
        if(usertype.toLowerCase().equals("doctor")||usertype.toLowerCase().equals("patient")) {
            Intent intent=new Intent(SignUp.this, TypeSelection.class);
            intent.putExtra("usertype",usertype);
            intent.putExtra("data",data);
            startActivity(intent);
        }
        else{
            Toast.makeText(SignUp.this, "Please fill User Type correctly",
                    Toast.LENGTH_LONG).show();
        }
    }
}
