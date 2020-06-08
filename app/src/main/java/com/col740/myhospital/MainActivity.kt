package com.col740.myhospital

import android.app.SearchManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

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

        val adapter = HomeAdapter(this,Supplier.homeitems)
        homerecycler.adapter = adapter
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
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val database = FirebaseDatabase.getInstance()
        if(item.itemId==R.id.option1){
            //val database = FirebaseDatabase.getInstance()
            val myRef = database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/friend")

            myRef.setValue("Hello, Doctor!")
            // Read from the database
            // Read from the database
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) { // This method is called once with the initial value and again
// whenever data at this location is updated.
                    val value =
                        dataSnapshot.getValue(String::class.java)!!
                    //Log.d(FragmentActivity.TAG, "Value is: $value")
                    Toast.makeText(this@MainActivity,value , Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) { // Failed to read value
                    Toast.makeText(this@MainActivity,"None" , Toast.LENGTH_SHORT).show()
                    // Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
                }
            })
                    //Toast.makeText(this, "Option1 is selected", Toast.LENGTH_SHORT).show()
        }else if(item.itemId==R.id.option2){
            //val database = FirebaseDatabase.getInstance()
            val myRef2 = database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com")

            //myRef2.setValue("Hello, Friend!")
            // Read from the database
            // Read from the database
            myRef2.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) { // This method is called once with the initial value and again
// whenever data at this location is updated.
                    val value =
                        dataSnapshot.getValue()!!
                    //Log.d(FragmentActivity.TAG, "Value is: $value")
                    print(value.javaClass)
                    Toast.makeText(this@MainActivity, arrayOf(value)[0].toString(), Toast.LENGTH_LONG).show()
                }

                override fun onCancelled(error: DatabaseError) { // Failed to read value
                    Toast.makeText(this@MainActivity,"None" , Toast.LENGTH_SHORT).show()
                    //Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
                }
            })
            //Toast.makeText(this, "Option2 is selected", Toast.LENGTH_SHORT).show()
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
}
