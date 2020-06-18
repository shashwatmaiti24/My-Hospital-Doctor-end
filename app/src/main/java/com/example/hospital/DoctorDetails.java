package com.example.hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.BatchUpdateException;

public class DoctorDetails extends AppCompatActivity {
    private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_appoint);
        Intent intent=getIntent();
        mAuth = FirebaseAuth.getInstance();
        String info=intent.getStringExtra("info");
        final String email=intent.getStringExtra("email");
        String[] data=info.split(" - ");
        TextView name=(TextView) findViewById(R.id.textView5);
        TextView hosp=(TextView) findViewById(R.id.textView6);
        TextView spec=(TextView) findViewById(R.id.textView7);
        TextView E=(TextView) findViewById(R.id.textView9);
        name.setText(data[0]);
        hosp.setText(data[2]);
        spec.setText(data[1]);
        E.setText(email+".com");

        Button btn=(Button) findViewById(R.id.buttonAppoint);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(DoctorDetails.this,"Hello",Toast.LENGTH_LONG).show();
                AppointDoctor a=new AppointDoctor(email,mAuth.getCurrentUser().getEmail());
                a.appoint(DoctorDetails.this);
//                mAuthListener=new FirebaseAuth.AuthStateListener() {
//                    @Override
//                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                        System.out.println(firebaseAuth.getCurrentUser().getEmail());
//                    }
//                };
            }
        });

    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
}
