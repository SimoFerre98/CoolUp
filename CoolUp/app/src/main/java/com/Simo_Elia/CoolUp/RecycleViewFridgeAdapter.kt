package com.Simo_Elia.CoolUp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.logging.Handler

class RecycleViewFridgeAdapter(context: Context?, Products_Name: MutableList<String>,Products_Date: MutableList<String>) : RecyclerView.Adapter<RecycleViewFridgeAdapter.ViewHolder>() {

    private lateinit var dbhandler : dbhandler
    var Products_Name = Products_Name
    var Products_Date = Products_Date
    var Context = context
    //  Una inner class può accederer agli elementi della classe esterna
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var Item_Product_Name : TextView
        var Item_Product_Date : TextView

        init
        {
            Item_Product_Name = itemView.findViewById(R.id.Name_Product)
            Item_Product_Date = itemView.findViewById(R.id.Date_Product)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var v = LayoutInflater.from(parent.context).inflate(R.layout.single_item_fridge,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.Item_Product_Name.text = Products_Name.get(position)
        holder.Item_Product_Date.text = Products_Date.get(position)
    }

    override fun getItemCount(): Int {
        return dbhandler.DimDownloadTable()
    }



}