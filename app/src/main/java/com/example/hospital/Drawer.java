package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class Drawer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);
        Intent intent=getIntent();
        boolean isDoctor=intent.getBooleanExtra("isDoctor",true);
        final String[] info=intent.getStringArrayExtra("info");

        Button logout=(Button) findViewById(R.id.buttonLogOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(Drawer.this,StartPage.class));
            }
        });

        TextView name=(TextView) findViewById(R.id.textView);
        name.setText(info[0]);
        TextView email=(TextView) findViewById(R.id.textView2);
        email.setText(info[1]);
        final String id=info[1].split("\\.")[0];
        TextView prob=(TextView) findViewById(R.id.problemSpec);
        prob.setText(info[2]);

        final Button btn=(Button) findViewById(R.id.bookORpatients);
        TextView btnText=(TextView) findViewById(R.id.bookORpatients);
        TextView chargeORappoint=(TextView) findViewById(R.id.chargeORAppoint);
        if(isDoctor){
            TextView phone=(TextView) findViewById(R.id.ifHosp);
            phone.setText("Hospital "+info[3]);
            chargeORappoint.setText("Base Charge "+info[5]);
            btnText.setText("View Patients");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //startActivity(new Intent(Drawer.this,ListDoctor.class));
                    btn.setText(info[7]);
                    LinearLayout buttonContainer = (LinearLayout) findViewById(R.id.buttonContainer);
                    Button button = new Button(Drawer.this);
                    button.setText("Close Appointment");
                    buttonContainer.addView(button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance("https://my-hospital-fce56.firebaseio.com/");
                            DatabaseReference doctor=database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Doctor/"+id);
                            doctor.child("Booking").setValue(0);
                            doctor.child("Booked By").setValue("None");
                        }
                    });
                }
            });
        }
        else{
            TextView phone=(TextView) findViewById(R.id.ifHosp);
            phone.setText("Phone No. "+info[3]);
            chargeORappoint.setText("Appointment With "+info[4]);
            btnText.setText("View Doctors");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Drawer.this,ListDoctor.class));
                }
            });
        }


    }
}
