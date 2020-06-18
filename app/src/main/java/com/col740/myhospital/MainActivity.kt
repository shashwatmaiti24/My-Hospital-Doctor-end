package com.col740.myhospital

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.SearchEvent
import android.widget.EditText
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {
    private var id: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        homerecycler.layoutManager = layoutManager
        val toolbar: Toolbar = findViewById(R.id.toolbar)
     //   val im1: ImageView = toolbar.findViewById(R.id.logo)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val adapter = HomeAdapter(this,Supplier_Home.homeitems)
        homerecycler.adapter = adapter
        id = intent.getStringExtra("id")
        toolbar_drawer.setOnClickListener ({ startDrawer() })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = getMenuInflater()
        inflater.inflate(R.menu.moreoptions, menu)
        // Retrieve the SearchView and plug it into SearchManager
        val searchView : SearchView =
            MenuItemCompat.getActionView(menu!!.findItem(R.id.search_button)) as SearchView;
        val searchManager : SearchManager = getSystemService(SEARCH_SERVICE) as SearchManager;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText=="ok"){
                    return false
                }
                return true
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(intent)
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.option1){
            Toast.makeText(this, "Option1 is selected", Toast.LENGTH_SHORT).show()
        }else if(item.itemId==R.id.option2){
            Toast.makeText(this, "Option2 is selected", Toast.LENGTH_SHORT).show()
        }else if(item.itemId==R.id.option3){
            Toast.makeText(this, "Option3 is selected", Toast.LENGTH_SHORT).show()
        }else if(item.itemId==R.id.suboption1){
            Toast.makeText(this, "Sub Option1 is selected", Toast.LENGTH_SHORT).show()
        }else if(item.itemId==R.id.suboption2){
            Toast.makeText(this, "Sub Option2 is selected", Toast.LENGTH_SHORT).show()
        }else if(item.itemId==R.id.suboption3){
            Toast.makeText(this, "Sub Option3 is selected", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startDrawer() {
        println("ID $id")
        val database = FirebaseDatabase.getInstance("https://my-hospital-fce56.firebaseio.com/")
        val intent = Intent(this, Drawer::class.java)
//                    val doctor =
//                        database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Doctor/$id")
//                    doctor.addValueEventListener(object : ValueEventListener {
//                        override fun onDataChange(d: DataSnapshot) {
//                            if (d.hasChildren()) {
//                                val info = arrayOf(
//                                    "Name",
//                                    "E-mail",
//                                    "Specialisation",
//                                    "Hospital",
//                                    "Rating",
//                                    "Base Charge",
//                                    "Booking",
//                                    "Booked By"
//                                )
//                                for (i in info.indices) {
//                                    info[i] = d.child(info[i]).value.toString()
//                                }
//                                intent.putExtra("isDoctor", true)
//                                intent.putExtra("info", info)
//                                startActivity(intent)
//                            } else {
        val patient =
            database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Patient/$id")
        patient.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                val info =
                    arrayOf(
                        "Name",
                        "E-mail",
                        "Problem",
                        "Phone",
                        "Appointment"
                    )
                for (i in info.indices) {
                    println(data.child(info[i]).value.toString())
                    println("__________________________")
                    info[i] =
                        data.child(info[i]).value.toString()
                }
                intent.putExtra("info", info)
                startActivity(intent)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}

            //             override fun onCancelled(databaseError: DatabaseError) {}
//        }
//    }
//}
