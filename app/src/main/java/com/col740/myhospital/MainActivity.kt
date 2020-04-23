package com.col740.myhospital

import android.app.SearchManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.SearchEvent
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
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
}
