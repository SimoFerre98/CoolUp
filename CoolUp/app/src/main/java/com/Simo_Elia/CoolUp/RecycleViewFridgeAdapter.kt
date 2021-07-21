package com.Simo_Elia.CoolUp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
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
        var Menu_Product : ImageView
        var LinearLayout_Item : LinearLayout
        init
        {
            Item_Product_Name = itemView.findViewById(R.id.Name_Product)
            Item_Product_Date = itemView.findViewById(R.id.Date_Product)
            LinearLayout_Item = itemView.findViewById(R.id.LinearLayout_Item)
            Menu_Product = itemView.findViewById(R.id.Menu_Product)
            Menu_Product.setOnClickListener()
            {
                popupMenus(it)
            }
        }

        private fun popupMenus(v:View) {
            // val position = userList[adapterPosition]
            val position = adapterPosition
            val popupMenus = PopupMenu(Context,v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editText->{
                        true
                    }
                    R.id.delete->{

                        true
                    }
                    else-> true
                }

            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)
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
        Log.d("Giorno corrente:" , currentDate.substring(6,10))
        Log.d("Giorno prodotto:" , Products_Date.get(position).substring(6,10))

        if (Products_Date.get(position).substring(6, 10) < currentDate.substring(6, 10)) {
            holder.LinearLayout_Item.setBackgroundColor(Color.GRAY)
        } else if (Products_Date.get(position).substring(6, 10) > currentDate.substring(6, 10)) {
            holder.LinearLayout_Item.setBackgroundColor(Color.GREEN)
        } else if (Products_Date.get(position).substring(6, 10) == currentDate.substring(6, 10)) {
            if (Products_Date.get(position).substring(4, 6) < currentDate.substring(4, 6)) {
                holder.LinearLayout_Item.setBackgroundColor(Color.GRAY)
            } else if (Products_Date.get(position).substring(4, 6) > currentDate.substring(4, 6)) {
                holder.ConstraintLayout_Item.setBackgroundColor(ContextCompat.getColor(Context!!, R.color.mygreen))
            } else if (Products_Date.get(position).substring(4, 6) == currentDate.substring(4, 6)) {

                var DiffDay: Int =
                    Products_Date.get(position).substring(0, 2).toInt() - currentDate.substring(0,2).toInt()

                if (DiffDay > 7) {
                    holder.ConstraintLayout_Item.setBackgroundColor(ContextCompat.getColor(Context!!, R.color.mygreen))
                } else if (DiffDay == 0) {
                    holder.ConstraintLayout_Item.setBackgroundColor(ContextCompat.getColor(Context!!, R.color.myred))
                } else if (DiffDay > 3 && DiffDay < 7) {
                    holder.ConstraintLayout_Item.setBackgroundColor(ContextCompat.getColor(Context!!, R.color.myyellow))
                } else if (DiffDay > 0 && DiffDay <= 3) {
                    holder.ConstraintLayout_Item.setBackgroundColor(Color.rgb(255, 128, 0))
                } else if (DiffDay < 0) {
                    holder.ConstraintLayout_Item.setBackgroundColor(ContextCompat.getColor(Context!!, R.color.mygray))
                }
            }
        }

    }

    override fun getItemCount(): Int
    {
        //return Handler.DimFridgeTable()
        return Handler.DimFridgeTable()
    }
}
