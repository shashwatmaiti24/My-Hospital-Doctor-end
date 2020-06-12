package com.col740.myhospital

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_draweritem.view.*
import kotlinx.android.synthetic.main.list_item.view.*

class HomeAdapter(val context: Context, val homeitems : List<HomeItem>): RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var currentitem : HomeItem? = null
        var currentposition : Int = 0

        init {
            itemView.setOnClickListener{
                Toast.makeText(context, currentitem!!.title +" was Clicked", Toast.LENGTH_SHORT).show()
            }
            itemView.imgShare.setOnClickListener {
                val message : String = currentitem!!.title
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, message)
                intent.type = "text/plain"
                context.startActivity(Intent.createChooser(intent, "Share to :"))
            }
        }
        fun setData(item: HomeItem?, pos: Int){
            itemView.txvTitle.text = item!!.title
            currentitem = item
            currentposition = pos
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return homeitems.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hobby = homeitems[position]
        holder.setData(hobby, position)
    }
}