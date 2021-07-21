package com.Simo_Elia.CoolUp

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class RecycleViewFridgeAdapter(
    context: Context?,
    Products_Name: MutableList<String>,
    Products_Date: MutableList<String>,
    Product_Id: MutableList<Int>,
    Products_Unit: MutableList<String>,
    Products_Category: MutableList<String>,
    Products_Description: MutableList<String>,
    Products_Allergens: MutableList<String>,
    Products_Recycle: MutableList<String>,
    Products_Freezable: MutableList<String>
) : RecyclerView.Adapter<RecycleViewFridgeAdapter.ViewHolder>() {

    var Handler = dbhandler(context)
    var Products_Name = Products_Name
    var Products_Date = Products_Date
    var Product_Id = Product_Id
    var Products_Unit = Products_Unit
    var Products_Category = Products_Category
    var Products_Description = Products_Description
    var Products_Allergens = Products_Allergens
    var Products_Recycle = Products_Recycle
    var Products_Freezable = Products_Freezable


    var Context = context
    //  Una inner class può accederer agli elementi della classe esterna
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var Item_Product_Name : TextView
        var Item_Product_Date : TextView
        var Item_Product_Unit:TextView
        var Item_Product_Category:TextView
        //var Item_Product_EAN :TextView
        var Item_Product_Description :TextView
        var Item_Product_Allergens:TextView
        var Item_Product_Recycle:TextView
        var Item_Product_Freezable:TextView

        var Menu_Product : ImageView
        var LinearLayout_Item : LinearLayout
        init
        {
            Item_Product_Name = itemView.findViewById(R.id.Name_Product)
            Item_Product_Date = itemView.findViewById(R.id.Date_Product)
            Item_Product_Unit = itemView.findViewById(R.id.Unit_show)
            //Item_Product_EAN = itemView.findViewById(R.id.EAN_show)
            Item_Product_Category = itemView.findViewById(R.id.Category_show)
            Item_Product_Description = itemView.findViewById(R.id.Description_show)
            Item_Product_Allergens = itemView.findViewById(R.id.Allergens_show)
            Item_Product_Recycle = itemView.findViewById(R.id.Recycle_show)
            Item_Product_Freezable = itemView.findViewById(R.id.Freezable_show)


            LinearLayout_Item = itemView.findViewById(R.id.LinearLayout_Item)
            Menu_Product = itemView.findViewById(R.id.Menu_Product)
            Menu_Product.setOnClickListener()
            {
                popupMenus(it)
            }
            var Clicked= false
            itemView.setOnClickListener(){
                if(Clicked==false){
                    Item_Product_Unit.visibility = View.VISIBLE
                    //Item_Product_EAN.visibility = View.VISIBLE
                    Item_Product_Category.visibility = View.VISIBLE
                    Item_Product_Description.visibility = View.VISIBLE
                    Item_Product_Allergens.visibility = View.VISIBLE
                    Item_Product_Recycle.visibility = View.VISIBLE
                    Item_Product_Freezable.visibility = View.VISIBLE
                    Clicked=true
                }
                else{
                    Item_Product_Unit.visibility = View.GONE
                    //Item_Product_EAN.visibility = View.GONE
                    Item_Product_Category.visibility = View.GONE
                    Item_Product_Description.visibility = View.GONE
                    Item_Product_Allergens.visibility = View.GONE
                    Item_Product_Recycle.visibility = View.GONE
                    Item_Product_Freezable.visibility = View.GONE
                    Clicked=false
                }
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
                        /**set delete*/
                        AlertDialog.Builder(Context)
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("Sei sicuro di eliminarlo?")
                            .setPositiveButton("Si"){
                                    dialog,_->
                                Products_Name.removeAt(adapterPosition)
                                Products_Date.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(Context,"Prodotto cancellato",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        Handler.DeleteFridge(Product_Id.get(adapterPosition))
                        Product_Id.removeAt(adapterPosition)
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
        holder.Item_Product_Unit.text = Products_Unit.get(position)
        holder.Item_Product_Category.text = Products_Category.get(position)
        holder.Item_Product_Allergens.text = Products_Allergens.get(position)
        holder.Item_Product_Description.text = Products_Description.get(position)
        if(Products_Recycle.get(position)=="Si"){
            holder.Item_Product_Recycle.text = "Reciclabile"
        }
        else{
            holder.Item_Product_Recycle.text = "Non Reciclabile"
        }

        if(Products_Freezable.get(position)=="Si"){
            holder.Item_Product_Freezable.text = "Congelabile"
        }
        else{
            holder.Item_Product_Freezable.text = "Non Congelabile"
        }




        //  currentDate è un oggetto contenente la data corrente
        val currentDate: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        Log.d("Giorno corrente:" , currentDate.substring(6,10))
        Log.d("Giorno prodotto:" , Products_Date.get(position).substring(6,10))

        if (Products_Date.get(position).substring(6, 10) < currentDate.substring(6, 10)) {
            holder.LinearLayout_Item.setBackgroundColor(ContextCompat.getColor(Context!!, R.color.mygray))
        } else if (Products_Date.get(position).substring(6, 10) > currentDate.substring(6, 10)) {
            holder.LinearLayout_Item.setBackgroundColor(ContextCompat.getColor(Context!!, R.color.mygreen))
        } else if (Products_Date.get(position).substring(6, 10) == currentDate.substring(6, 10)) {
            if (Products_Date.get(position).substring(4, 6) < currentDate.substring(4, 6)) {
                holder.LinearLayout_Item.setBackgroundColor(ContextCompat.getColor(Context!!, R.color.mygray))
            } else if (Products_Date.get(position).substring(4, 6) > currentDate.substring(4, 6)) {
                holder.LinearLayout_Item.setBackgroundColor(ContextCompat.getColor(Context!!, R.color.mygreen))
            } else if (Products_Date.get(position).substring(4, 6) == currentDate.substring(4, 6)) {

                var DiffDay: Int =
                    Products_Date.get(position).substring(0, 2).toInt() - currentDate.substring(0,2).toInt()

                if (DiffDay > 7) {
                    holder.LinearLayout_Item.setBackgroundColor(ContextCompat.getColor(Context!!, R.color.mygreen))
                } else if (DiffDay == 0) {
                    holder.LinearLayout_Item.setBackgroundColor(ContextCompat.getColor(Context!!, R.color.myred))
                } else if (DiffDay > 3 && DiffDay < 7) {
                    holder.LinearLayout_Item.setBackgroundColor(ContextCompat.getColor(Context!!, R.color.myyellow))
                } else if (DiffDay > 0 && DiffDay <= 3) {
                    holder.LinearLayout_Item.setBackgroundColor(Color.rgb(255, 128, 0))
                } else if (DiffDay < 0) {
                    holder.LinearLayout_Item.setBackgroundColor(ContextCompat.getColor(Context!!, R.color.mygray))
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
