package com.col740.myhospital

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.itemview.*

class ItemActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.itemview)
        val spinner: Spinner = this.findViewById(R.id.spinner)
        val id: String = intent.getStringExtra("id")

//        val id = email.split(".").toTypedArray()[0]
        val database = FirebaseDatabase.getInstance("https://my-hospital-fce56.firebaseio.com/")
        val doctor =
            database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Doctor/$id")
         println("-------------------------------------------1")
        var slot: Int? = null
//        var slotselected = false
        doctor.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
//                val info =
//                    arrayOf(
//                        "Name",
//                        "E-mail",
//                        "Specialisation",
//                        "Hospital",
//                        "Rating",
//                        "Base Charge",
//                        "Booking",
//                        "Booked By"
//                    )
//                for (i in info.indices) {
//                    println(data.child(info[i]).value.toString())
//                    println("__________________________")
//                    info[i] =
//                        data.child(info[i]).value.toString()
//                }
//                println(id)

                println("-------------------------------------------2")
                name.text = data.child("Name").value.toString()
//                println(data.child("Rating").value.toString())
//                println("_______________________________________________________")
                ratingBar.rating = data.child("Rating").value.toString().toFloat()
                specialization.text = data.child("Specialisation").value.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        val doctorbooking =
                    database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Doctorbookings/$id")
        val slotarray:MutableList<String> = ArrayList()
        slotarray.add("Please select a slot")
        doctorbooking.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) { // This method is called once with the initial value and again
// whenever data at this location is updated.
//                s = Array<String>(l.toInt() + 1){""}
//                email = Array<String>(l.toInt() + 1){""}

                println("-------------------------------------------5")
//                        var j = 0
                for (d in dataSnapshot.children) {
//                    println(d.child("Name").getValue(String::class.java).toString())
//                    println(d.key.toString())
//                    println("_*_*_*_*_*_*_*_*_*__*_*__*_*__*_*")
                    //       if(d.child("Name").value!=null) {
                    //            slotarray.add(
                    if(d.child("maxbooking").value.toString().toInt() - d.child("bookedby").child("elements").value.toString().toInt()>0) {
                        slotarray.add(
                            d.child("timing").value.toString()
                        )
                    }
                    //           )
////                    email[j] = d.key.toString()
//                            j++
                }
                val adapter = ArrayAdapter(
                    this@ItemActivity,
                    android.R.layout.simple_spinner_item, slotarray
                )
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner

                println("-------------------------------------------7")
                spinner.adapter = adapter
                println("-------------------------------------------8")

                //                for (int i=1;i<(int)l;i++){
//                    DataSnapshot d=dataSnapshot.child(Integer.toString(i));
//                    s[i]=d.child("Name").getValue(String.class)+" - "+d.child("Specialisation").getValue(String.class)+" - "+d.child("Hospital").getValue(String.class);
//                    email[i]=d.child("E-mail").getValue(String.class);
//                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
//                var slotarr:Array<String?> = arrayOfNulls<String>(slotarray.size)
//                var j = 0
//                for (k in slotarray){
//                    slotarr[j] = k
//                    println(k)
//                    j++
//                }
//                intent.putExtra("slotarray", slotarray)

            book.setOnClickListener {
//                println(slotselected)
                val intent = Intent(this, Booking::class.java)
                if (slot != 0) {
                    intent.putExtra("doc", id)
                    intent.putExtra("slot", slot!!)
                    ContextCompat.startActivity(this, intent, null)
                }else{
                    Toast.makeText(this, "Please select a slot", Toast.LENGTH_SHORT).show()
                }
             }

        println("-------------------------------------------3")
//
//
//
//        println("-------------------------------------------4")
//
//        println("-------------------------------------------6")

// Create an ArrayAdapter using the string array and a default spinner layout

  spinner.onItemSelectedListener = object:
        AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
//                println("position")
                slot = position
//                slotselected = true
            }
        }
    }
}

