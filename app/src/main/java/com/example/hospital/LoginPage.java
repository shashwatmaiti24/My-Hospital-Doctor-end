package com.example.hospital;

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

public class LoginPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailText;
    private EditText passwordText;
    private Button buttonLogin;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        mAuth = FirebaseAuth.getInstance();
        emailText=(EditText) findViewById(R.id.editEmail);
        passwordText=(EditText) findViewById(R.id.editPass);


        buttonLogin=(Button) findViewById(R.id.buttonLoginIn);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//        mAuthListener=new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                if(firebaseAuth.getCurrentUser()!=null){
//                    String idtemp=firebaseAuth.getCurrentUser().getEmail();
//                    final String id=idtemp.split("\\.")[0];
//                    System.out.println("ID "+id);
//                    final FirebaseDatabase database = FirebaseDatabase.getInstance("https://my-hospital-fce56.firebaseio.com/");
//                    final Intent intent=new Intent(LoginPage.this, Drawer.class);
//                    DatabaseReference doctor=database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Doctor/"+id);
//                    doctor.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot d) {
//                            if(d.hasChildren()) {
//                                String[] info = {"Name", "E-mail", "Specialisation", "Hospital", "Rating", "Base Charge", "Booking"};
//                                for (int i = 0; i < info.length; i++) {
//                                    info[i] = d.child(info[i]).getValue().toString();
//                                }
//                                intent.putExtra("isDoctor", true);
//                                intent.putExtra("info", info);
//                                startActivity(intent);
//                            }
//                            else{
//                                final DatabaseReference patient=database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Patient/"+id);
//                                patient.addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot data) {
//                                        System.out.println("KYUN?? "+data.toString());
//                                        String[] info={"Name","E-mail","Problem","Phone","Appointment"};
//                                        for(int i=0;i<info.length;i++){
//                                            info[i]=data.child(info[i]).getValue().toString();
//                                        }
//                                        intent.putExtra("isDoctor",false);
//                                        intent.putExtra("info",info);
//                                        startActivity(intent);
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                    }
//                                });
//
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//            }
//        };
//    }

    private void startSignIn(){
        final String email=emailText.getText().toString();
        String password=passwordText.getText().toString();

        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
            Toast.makeText(LoginPage.this,"Fields Empty",Toast.LENGTH_LONG).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(LoginPage.this,"Sign In Problem",Toast.LENGTH_LONG).show();
                    }
                    else{
                        String idtemp=email;
                        final String id=idtemp.split("\\.")[0];
                        System.out.println("ID "+id);
                        final FirebaseDatabase database = FirebaseDatabase.getInstance("https://my-hospital-fce56.firebaseio.com/");
                        final Intent intent=new Intent(LoginPage.this, Drawer.class);
                        DatabaseReference doctor=database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Doctor/"+id);
                        doctor.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot d) {
                                if(d.hasChildren()) {
                                    String[] info = {"Name", "E-mail", "Specialisation", "Hospital", "Rating", "Base Charge", "Booking","Booked By"};
                                    for (int i = 0; i < info.length; i++) {
                                        info[i] = d.child(info[i]).getValue().toString();
                                    }
                                    intent.putExtra("isDoctor", true);
                                    intent.putExtra("info", info);
                                    startActivity(intent);
                                }
                                else{
                                    DatabaseReference patient=database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Patient/"+id);
                                    patient.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot data) {
                                            String[] info={"Name","E-mail","Problem","Phone","Appointment"};
                                            for(int i=0;i<info.length;i++){
                                                info[i]=data.child(info[i]).getValue().toString();
                                            }
                                            intent.putExtra("isDoctor",false);
                                            intent.putExtra("info",info);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            });
        }

    }
}
