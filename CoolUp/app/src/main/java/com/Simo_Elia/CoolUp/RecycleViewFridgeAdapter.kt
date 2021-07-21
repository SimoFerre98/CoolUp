package com.Simo_Elia.CoolUp

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class RecycleViewFridgeAdapter(context: Context?, Products_Name: MutableList<String>,Products_Date: MutableList<String>) : RecyclerView.Adapter<RecycleViewFridgeAdapter.ViewHolder>() {

    var Handler = dbhandler(context)
    var Products_Name = Products_Name
    var Products_Date = Products_Date
    var Context = context
    //  Una inner class può accederer agli elementi della classe esterna
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var Item_Product_Name : TextView
        var Item_Product_Date : TextView
        var LinearLayout_Item : ConstraintLayout
        init
        {
            Item_Product_Name = itemView.findViewById(R.id.Name_Product)
            Item_Product_Date = itemView.findViewById(R.id.Date_Product)
            LinearLayout_Item = itemView.findViewById(R.id.LinearLayout_Item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var v = LayoutInflater.from(parent.context).inflate(R.layout.single_item_fridge,parent,false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.Item_Product_Name.text = Products_Name.get(position)
        holder.Item_Product_Date.text = Products_Date.get(position)

        //  currentDate è un oggetto contenente la data corrente
        val currentDate: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
         Log.d("Giorno corrente:" , currentDate.substring(0,10))
           Log.d("Giorno prodotto:" , Products_Date.get(position).substring(0,10))

        if (Products_Date.get(position).substring(7, 10) < currentDate.substring(7, 10)) {
            holder.LinearLayout_Item.setBackgroundColor(Color.RED)
        } else if (Products_Date.get(position).substring(7, 10) > currentDate.substring(7, 10)) {
            holder.LinearLayout_Item.setBackgroundColor(Color.GREEN)
        } else if (Products_Date.get(position).substring(7, 10) == currentDate.substring(7, 10)) {
            if (Products_Date.get(position).substring(4, 6) < currentDate.substring(4, 6)) {
                holder.LinearLayout_Item.setBackgroundColor(Color.RED)
            } else if (Products_Date.get(position).substring(4, 6) > currentDate.substring(4, 6)) {
                holder.LinearLayout_Item.setBackgroundColor(Color.GREEN)
            } else if (Products_Date.get(position).substring(4, 6) == currentDate.substring(4, 6)) {

                var DiffDay: Int =
                    Products_Date.get(position).substring(0, 2).toInt() - currentDate.substring(
                        0,
                        2
                    ).toInt()

                if (DiffDay > 7) {
                    holder.LinearLayout_Item.setBackgroundColor(Color.GREEN)
                } else if (DiffDay == 0) {
                    holder.LinearLayout_Item.setBackgroundColor(Color.RED)
                } else if (DiffDay > 3 && DiffDay < 7) {
                    holder.LinearLayout_Item.setBackgroundColor(Color.YELLOW)
                } else if (DiffDay > 0 && DiffDay <= 3) {
                    holder.LinearLayout_Item.setBackgroundColor(Color.rgb(255, 128, 0))
                } else if (DiffDay < 0) {
                    holder.LinearLayout_Item.setBackgroundColor(Color.GRAY)
                }
            }
        }

    }

        //((currentDate.substring(0,2).toInt() - 3).toString() > Products_Date.get(position).substring(0,2) )
// Log.d("Giorno corrente:" , currentDate.substring(0,2))
//            Log.d("Giorno prodotto:" , Products_Date.get(position).substring(0,2))

    override fun getItemCount(): Int
    {
        Log.d("Tabella Download: ",Handler.DimDownloadTable().toString())
        //Log.d("Tabella Download: ",Handler.
        Log.d("Tabella Fridge: ",Handler.DimFridgeTable().toString())
        Log.d("Tabella ShopList: ",Handler.DimShopListTable().toString())
        //return Handler.DimFridgeTable()
        return Handler.DimFridgeTable()
    }
}