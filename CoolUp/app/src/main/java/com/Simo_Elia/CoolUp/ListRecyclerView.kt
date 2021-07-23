package com.Simo_Elia.CoolUp

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListRecyclerView(
    context: Context?,
    Shoplist_Name: MutableList<String>,
    Shoplist_unit: MutableList<String>,
    fab:FloatingActionButton
):RecyclerView.Adapter<ListRecyclerView.ViewHolder>()
{

    var fab = fab
    var Handler = dbhandler(context)
    var Shoplist_Name = Shoplist_Name
    var Shoplist_Category = Shoplist_unit

    @RequiresApi(Build.VERSION_CODES.P)
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var Item_Shoplist_Name : TextView
        var Item_Shoplist_Unit : TextView

        init
        {


            Item_Shoplist_Name = itemView.findViewById(R.id.ShoplistCheckbox)
            Item_Shoplist_Unit = itemView.findViewById(R.id.ShoplistUnit)
            fab.setOnClickListener()
            {
                popupMenus(it)
            }
        }
    }

    private fun popupMenus(v:View) {
        val v = LayoutInflater.from(v.context).inflate(R.layout.add_shoplist,null)
        val ShoplistName = v.findViewById<EditText>(R.id.InsertShoplistName)
        val ShoplistUnit = v.findViewById<EditText>(R.id.InserShoplistUnit)

        AlertDialog.Builder(v.context)
            .setView(v)
            .setPositiveButton("Ok"){
                    dialog,_->

                var Name = ShoplistName.text.toString()
                var Unit = ShoplistUnit.text.toString()

                Shoplist_Name.add(Name)
                Shoplist_Category.add(Unit)

                var shoplist = dblist(Unit,
                    Name
                )

                Handler.InsertShopList(shoplist)

                Toast.makeText(v.context,"Le informazioni sono state modificate", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
            .setNegativeButton("Cancella"){
                    dialog,_->
                dialog.dismiss()

            }
            .create()
            .show()

    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRecyclerView.ViewHolder
    {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.shoplist_item,parent,false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.Item_Shoplist_Name.text = Shoplist_Name.get(position)
        holder.Item_Shoplist_Unit.text = Shoplist_Category.get(position)
    }

    override fun getItemCount(): Int
    {
        //return Handler.DimShopListTable()
        return Shoplist_Name.size
    }
}