package com.col740.myhospital

import android.app.SearchManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.ExpandableListAdapter
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import com.col740.myhospital.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.expandablelistview.*

class Expandablelistview : AppCompatActivity() {

    val header: MutableList<String> = ArrayList()
    val body: MutableList<MutableList<String>> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.searchbycategory)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        //   val im1: ImageView = toolbar.findViewById(R.id.logo)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)


        val Doctors: MutableList<String> = ArrayList()
        Doctors.add("Dentist")
        Doctors.add("ENT")


        val Labs: MutableList<String> = ArrayList()
        Labs.add("Blood Test")
        Labs.add("Blood Test")

        header.add("Doctors")
        header.add("Labs")

        body.add(Doctors)
        body.add(Labs)

        expandableListView.setAdapter(ExpandableListAdapter(this,expandableListView, header, body))
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
}