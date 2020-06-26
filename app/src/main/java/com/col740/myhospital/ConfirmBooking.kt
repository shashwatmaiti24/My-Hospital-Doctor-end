package com.col740.myhospital

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.booking.*
import kotlinx.android.synthetic.main.confirmbooking.*

class ConfirmBooking: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirmbooking)
        toolbar2.title = "Confirm Booking"
        val patientid = intent.getStringExtra("patientid")
        val docid = intent.getStringExtra("docid")
        val slotid = intent.getStringExtra("slotid")
        println(slotid)
        val problem = intent.getStringExtra("problem")
        val database = FirebaseDatabase.getInstance("https://my-hospital-fce56.firebaseio.com/")
        val patient =
            database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Patient/$patientid")
        patient.child("Appointment").setValue(docid)
        val doctor =
        database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Doctor/$docid")
        doctor.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                doctorname.text = data.child("Name").value.toString()
                speciality.text = data.child("Specialisation").value.toString()
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
        val setappointment =
            database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Doctorbookings/$docid/$slotid")
        var bookings = 0
        setappointment.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                selectedslot.text = data.child("timing").value.toString()
                bookings = data.child("bookedby").child("elements").value.toString().toInt()
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
        patproblem.text = problem
        confirm.setOnClickListener {
            setappointment.child("bookedby").child((bookings + 1).toString())
                .child("patientid").setValue(patientid)
            setappointment.child("bookedby").child((bookings + 1).toString())
                .child("problem").setValue(problem.toString())
            setappointment.child("bookedby").child("elements").setValue(
                bookings + 1
            )
        val intent = Intent(this, BookingConfirm::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            ContextCompat.startActivity(this, intent, null)
            finish()
        }
//        println("------------------------------------------0")
//        doctor.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(data: DataSnapshot) {
//                println("------------------------------------------1")
//                var slot: String?
//                for (d in data.children) {
//                    println("------------------------------------------2")
//                    if (d.child("maxbooking").value.toString().toInt() - d.child("bookedby").child("elements").value.toString().toInt() > 0) {
//
//                        println("------------------------------------------3")
//                        slot = d.key
//                        val setappointment =
//                            database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Doctorbookings/$docid/$slot/bookedby")
//                        setappointment.child((d.child("bookedby").child("elements").value.toString().toInt() + 1).toString())
//                            .child("patientid").setValue(patientid)
//                        setappointment.child((d.child("bookedby").child("elements").value.toString().toInt() + 1).toString())
//                            .child("problem").setValue(problem.text.toString())
//                        setappointment.child("elements").setValue(
//                            d.child("bookedby").child("elements").value.toString().toInt() + 1
//                        )
//                        break
//                    }
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {}
//        })
    }
}