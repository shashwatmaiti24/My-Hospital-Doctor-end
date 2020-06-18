package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PatientRegister extends AppCompatActivity {
    private int elems=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);
        Intent intent = getIntent();
        int e=intent.getIntExtra("Elements",0);
        final String[] data=intent.getStringArrayExtra("data");
        elems=e;

        Button logout=(Button) findViewById(R.id.buttonLogOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(PatientRegister.this,StartPage.class));
            }
        });
        //FirebaseDatabase database = FirebaseDatabase.getInstance("https://my-hospital-fce56.firebaseio.com/");
        Button submit=(Button) findViewById(R.id.submitPatient);
        submit.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EditText Ename=(EditText) findViewById(R.id.pname);
                //EditText Eemail=(EditText) findViewById(R.id.Pemail);
                //EditText Ephone=(EditText) findViewById(R.id.phone1);
                String name=data[1];
                String email=data[0].split("\\.")[0];
                String phone=data[2];
                String appoint="No Appointments";
                EditText Eprob=(EditText) findViewById(R.id.editProblem);
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://my-hospital-fce56.firebaseio.com/");
                DatabaseReference id=database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Patient/elements");

                String url="https://my-hospital-fce56.firebaseio.com/Patient/"+email;
                DatabaseReference myRef = database.getReferenceFromUrl(url);
                String prob=Eprob.getText().toString();
                DatabaseReference nameChild=myRef.child("Name");
                DatabaseReference emailChild=myRef.child("E-mail");
                DatabaseReference phoneChild=myRef.child("Phone");
                DatabaseReference probChild=myRef.child("Problem");
                DatabaseReference appointChild=myRef.child("Appointment");
                nameChild.setValue(name);
                emailChild.setValue(email+".com");
                phoneChild.setValue(phone);
                probChild.setValue(prob);
                appointChild.setValue(appoint);
                id.setValue(elems+1);
                String[] info={name,email,prob,phone,appoint};
                Intent intent=new Intent(PatientRegister.this, Drawer.class);
                intent.putExtra("isDoctor",false);
                intent.putExtra("info",info);
                startActivity(intent);
            }
        }));
    }
}
