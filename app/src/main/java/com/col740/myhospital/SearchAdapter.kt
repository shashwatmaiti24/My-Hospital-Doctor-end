package com.col740.myhospital

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_searchitem.view.*

class SearchAdapter(val context: Context, val searchitems : List<SearchItem>) : RecyclerView.Adapter<SearchAdapter.MyViewHolder>()  {
   inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var currentitem : SearchItem? = null
        var currentposition : Int = 0

        init {
            itemView.setOnClickListener{
                Toast.makeText(context, currentitem!!.title +" was Clicked", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, ItemActivity::class.java)
                ContextCompat.startActivity(context, intent, null)
            }
        }
        fun setData(item: SearchItem?, pos: Int){
            itemView.textView3.text = item!!.title
            currentitem = item
            currentposition = pos
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_searchitem, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchitems.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val SearchItem = searchitems[position]
        holder.setData(SearchItem, position)
    }
}