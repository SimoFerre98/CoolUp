package com.Simo_Elia.CoolUp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class RecycleViewFridgeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var dbhandler : dbhandler
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var v = LayoutInflater.from(parent.context).inflate(R.layout.single_item_fridge,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return dbhandler.DimDownloadTable()
    }
//  https://www.youtube.com/watch?v=UCddGYMQJCo

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var Prodoct_Name : TextView
        var Prodoct_Date : TextView

        init {
            Prodoct_Name = itemView.findViewById(R.id.Name_Product)
            Prodoct_Date = itemView.findViewById(R.id.Date_Product)
        }
    }
}