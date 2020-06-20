package com.col740.myhospital

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.drawer.*
import kotlinx.android.synthetic.main.searchview.*

data class SearchItem(var title:String, var id:String)
class SearchActivity:AppCompatActivity() {
    object Supplier_Search{
        //    val searchitems = listOf<SearchItem>(
//        SearchItem(s[0]),
//        SearchItem(s[1]))
        var searchitems: MutableList<SearchItem> = ArrayList()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.searchview)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        searchrecycler.layoutManager = layoutManager
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val database =
            FirebaseDatabase.getInstance("https://my-hospital-fce56.firebaseio.com/")
        val doctors =
            database.getReferenceFromUrl("https://my-hospital-fce56.firebaseio.com/Doctor")
//        var s : Array<String> = emptyArray()
//        var email : Array<String> = emptyArray()
        doctors.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) { // This method is called once with the initial value and again
// whenever data at this location is updated.
//                s = Array<String>(l.toInt() + 1){""}
//                email = Array<String>(l.toInt() + 1){""}
//                var j = 0
                if(!Supplier_Search.searchitems.isEmpty()){
                    Supplier_Search.searchitems = ArrayList()}
                for (d in dataSnapshot.children) {
//                    println(d.child("Name").getValue(String::class.java).toString())
//                    println(d.key.toString())
//                    println("_*_*_*_*_*_*_*_*_*__*_*__*_*__*_*")
                    if(d.child("Name").value!=null) {
                        Supplier_Search.searchitems.add(
                            SearchItem(
                                d.child("Name").getValue(String::class.java).toString(),
                                d.getKey()!!
                            )
                        )
//                    email[j] = d.key.toString()
                        //                  j++
                    }
                }
                //                for (int i=1;i<(int)l;i++){
//                    DataSnapshot d=dataSnapshot.child(Integer.toString(i));
//                    s[i]=d.child("Name").getValue(String.class)+" - "+d.child("Specialisation").getValue(String.class)+" - "+d.child("Hospital").getValue(String.class);
//                    email[i]=d.child("E-mail").getValue(String.class);
//                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })


//        Supplier_Search.searchitems = listOf<com.col740.myhospital.SearchItem>(SearchItem("jhesf"),
//        SearchItem("awjekfh"))
        val adapter = SearchAdapter(this,Supplier_Search.searchitems)
        searchrecycler.adapter = adapter
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
                val intent = Intent(this@SearchActivity, SearchActivity::class.java)
                startActivity(intent)
                return true
            }
        })
        return true
    }

}