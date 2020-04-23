package com.col740.myhospital

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class HomeAdapter(val context: Context, val hobbies : List<HomeItem>): RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var currenthobby : HomeItem? = null
        var currentposition : Int = 0

        init {
            itemView.setOnClickListener{
                Toast.makeText(context, currenthobby!!.title +" was Clicked", Toast.LENGTH_SHORT).show()
            }
            itemView.imgShare.setOnClickListener {
                val message : String = currenthobby!!.title
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, message)
                intent.type = "text/plain"
                context.startActivity(Intent.createChooser(intent, "Share to :"))
            }
        }
        fun setData(hobby: HomeItem?, pos: Int){
            itemView.txvTitle.text = hobby!!.title
            currenthobby = hobby
            currentposition = pos
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hobbies.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hobby = hobbies[position]
        holder.setData(hobby, position)
    }
}