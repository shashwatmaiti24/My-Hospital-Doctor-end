package com.col740.myhospital

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.booking.*
import kotlinx.android.synthetic.main.drawer.*
import kotlinx.android.synthetic.main.drawer_header.view.*

class Booking: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.booking)
        val firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser: FirebaseUser? = firebaseAuth.getCurrentUser()
        val email = firebaseUser?.email
//        val email = emailText!!.text.toString()
        val patientid = email!!.split(".").toTypedArray().get(0)
        val docid = intent.getStringExtra("doc")
        println(docid)
        toolbar.setTitle("Book Appointment")
        book.setOnClickListener {
            val database = FirebaseDatabase.getInstance("https://my-hospital-fce56.firebaseio.com/")
            val patient =
                database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Patient/$patientid")
            patient.child("Appointment").setValue(docid)
            val doctor =
                database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Doctorbookings/$docid")

            println("------------------------------------------0")
            doctor.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(data: DataSnapshot) {
                    println("------------------------------------------1")
                    var slot:String?
                    for (d in data.children){
                        println("------------------------------------------2")
                        if (d.child("maxbooking").value.toString().toInt()-d.child("booking").value.toString().toInt()>0){

                            println("------------------------------------------3")
                            slot = d.key
                            val setappointment =
                                database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Doctorbookings/$docid/$slot/bookedby")
                            setappointment.child(patientid).setValue(problem.text.toString())
                            setappointment.child("elements").setValue(
                                d.child("bookedby").child("elements").value.toString().toInt()+1)
                            break
                        }
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }
    }
}