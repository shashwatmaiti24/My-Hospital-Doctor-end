package com.example.hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class ListDoctor extends AppCompatActivity {
    public void showList(String[] s,String[] email){
        RecyclerView docList=(RecyclerView) findViewById(R.id.recyclerView);
        docList.setLayoutManager(new LinearLayoutManager(this));

        docList.setAdapter(new DoctorAdapter(s,email));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_doctor);

        Button logout=(Button) findViewById(R.id.buttonLogOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(ListDoctor.this,StartPage.class));
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://my-hospital-fce56.firebaseio.com/");
        DatabaseReference doctors=database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Doctor");

        doctors.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                long l=dataSnapshot.getChildrenCount();
                String[] s=new String[(int) l+1];
                String[] email=new String[(int)l+1];
                int j=0;
                for (DataSnapshot d:dataSnapshot.getChildren()){
                    s[j]=d.child("Name").getValue(String.class)+" - "+d.child("Specialisation").getValue(String.class)+" - "+d.child("Hospital").getValue(String.class);
                    email[j]=d.getKey();
                    j++;
                }
//                for (int i=1;i<(int)l;i++){
//                    DataSnapshot d=dataSnapshot.child(Integer.toString(i));
//                    s[i]=d.child("Name").getValue(String.class)+" - "+d.child("Specialisation").getValue(String.class)+" - "+d.child("Hospital").getValue(String.class);
//                    email[i]=d.child("E-mail").getValue(String.class);
//                }
                showList(s,email);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
