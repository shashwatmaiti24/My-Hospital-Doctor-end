package com.col740.myhospital

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.drawer.*
import kotlinx.android.synthetic.main.drawer_header.view.*

class Drawer:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        drawerrecycler.layoutManager = layoutManager
        //   val im1: ImageView = toolbar.findViewById(R.id.logo)
        val adapter = DrawerAdapter(this,Supplier_Drawer.draweritems)
        drawerrecycler.adapter = adapter
        val info = intent.getStringArrayExtra("info")
        drawer_header.name.setText(info[0]);
        drawer_header.email.setText(info[1]);
    }
}