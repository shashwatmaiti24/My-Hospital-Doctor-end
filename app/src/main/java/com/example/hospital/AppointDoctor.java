package com.example.hospital;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AppointDoctor {
    private String doctor;
    private String patient;
    AppointDoctor(String doctor,String patient){
        this.doctor=doctor;
        this.patient=patient.split("\\.")[0];
    }
    public void appoint(final DoctorDetails doctorDetails){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://my-hospital-fce56.firebaseio.com/");
        DatabaseReference editPatient=database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Patient/"+patient);
        final DatabaseReference appointChild=editPatient.child("Appointment");
        final DatabaseReference editDoctor=database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Doctor/"+doctor);
        editDoctor.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot data) {
                if(data.child("Booking").getValue(Integer.class)==0){
                    appointChild.setValue(doctor+".com");
                    editDoctor.child("Booking").setValue(1);
                    editDoctor.child(("Booked By")).setValue(patient+".com");
                }
                else{
                    Toast.makeText(doctorDetails,"Not Available",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
