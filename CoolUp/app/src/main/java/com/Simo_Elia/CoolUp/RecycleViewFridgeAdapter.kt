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
import java.util.jar.Attributes

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
        // VALORI
        var Item_Product_Name : TextView
        var Item_Product_Date : TextView
        var Item_Product_Unit:TextView
        var Item_Product_Category:TextView
        //var Item_Product_EAN :TextView
        var Item_Product_Description :TextView
        var Item_Product_Allergens:TextView
        var Item_Product_Recycle:TextView
        var Item_Product_Freezable:TextView

        // RIGHE
        var Text_Product_Name : TextView
        var Text_Product_Date : TextView
        var Row_Product_Unit:LinearLayout
        var Row_Product_Category:LinearLayout
        //var Item_Product_EAN :TextView
        var Row_Product_Description :LinearLayout
        var Row_Product_Allergens:LinearLayout
        var Row_Product_Recycle:LinearLayout
        var Row_Product_Freezable:LinearLayout

        var Menu_Product : ImageView
        var LinearLayout_Item : LinearLayout
        init
        {
            // VALORI
            Item_Product_Name = itemView.findViewById(R.id.Name_Product)
            Item_Product_Date = itemView.findViewById(R.id.Date_Product)
            Item_Product_Unit = itemView.findViewById(R.id.Unit_show)
            //Item_Product_EAN = itemView.findViewById(R.id.EAN_show)
            Item_Product_Category = itemView.findViewById(R.id.Category_show)
            Item_Product_Description = itemView.findViewById(R.id.Description_show)
            Item_Product_Allergens = itemView.findViewById(R.id.Allergens_show)
            Item_Product_Recycle = itemView.findViewById(R.id.Recycle_show)
            Item_Product_Freezable = itemView.findViewById(R.id.Freezable_show)

            // RIGHE
            Text_Product_Name = itemView.findViewById(R.id.Name_Product_TextView)
            Text_Product_Date = itemView.findViewById(R.id.Date_Product_TextView)
            Row_Product_Unit = itemView.findViewById(R.id.UnitRow)
            Row_Product_Category = itemView.findViewById(R.id.CategoryRow)
            Row_Product_Description = itemView.findViewById(R.id.DescriptionRow)
            Row_Product_Allergens = itemView.findViewById(R.id.AllergensRow)
            Row_Product_Recycle = itemView.findViewById(R.id.RecycleRow)
            Row_Product_Freezable = itemView.findViewById(R.id.FreezableRow)


            LinearLayout_Item = itemView.findViewById(R.id.LinearLayout_Item)
            Menu_Product = itemView.findViewById(R.id.Menu_Product)
            Menu_Product.setOnClickListener()
            {
                popupMenus(it)
            }
            var Clicked= false
            itemView.setOnClickListener(){
                if(Clicked==false){
                    Item_Product_Name.setTextSize(15F)
                    Item_Product_Date.setTextSize(15F)
                    Text_Product_Name.visibility = View.VISIBLE
                    Text_Product_Date.visibility= View.VISIBLE
                    Row_Product_Unit.visibility= View.VISIBLE
                    Row_Product_Category.visibility= View.VISIBLE
                    Row_Product_Description.visibility= View.VISIBLE
                    Row_Product_Allergens.visibility= View.VISIBLE
                    Row_Product_Recycle.visibility= View.VISIBLE
                    Row_Product_Freezable.visibility= View.VISIBLE
                    Clicked=true
                }
                else{
                    Item_Product_Name.setTextSize(30F)
                    Item_Product_Date.setTextSize(30F)
                    Text_Product_Name.visibility = View.GONE
                    Text_Product_Date.visibility= View.GONE
                    Row_Product_Unit.visibility= View.GONE
                    Row_Product_Category.visibility= View.GONE
                    Row_Product_Description.visibility= View.GONE
                    Row_Product_Allergens.visibility= View.GONE
                    Row_Product_Recycle.visibility= View.GONE
                    Row_Product_Freezable.visibility= View.GONE
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
                        val v = LayoutInflater.from(Context).inflate(R.layout.add_item,null)
                        val Modifica_Name = v.findViewById<EditText>(R.id.Modifica_Name)
                        val Modifica_Category = v.findViewById<EditText>(R.id.Modifica_Category)
                        val Modifica_Description = v.findViewById<EditText>(R.id.Modifica_Descizione)
                        val Modifica_Allergens = v.findViewById<EditText>(R.id.Modifica_Allergens)
                        val Modifica_Unit = v.findViewById<EditText>(R.id.Modifica_Unit)
                        val Modifica_Recyclable = v.findViewById<EditText>(R.id.Modifica_Recyclable)
                        val Modifica_Freezable = v.findViewById<EditText>(R.id.MOdifica_Freezable)
                        val Modifica_Date = v.findViewById<EditText>(R.id.MOdifica_Date)


                        AlertDialog.Builder(Context)
                            .setView(v)
                            .setPositiveButton("Ok"){
                                    dialog,_->

                                var Name = Modifica_Name.text.toString()
                                var Category = Modifica_Category.text.toString()
                                var Description= Modifica_Description.text.toString()
                                var Allergens= Modifica_Allergens.text.toString()
                                var Unit = Modifica_Unit.text.toString()
                                var Recyclable = Modifica_Recyclable.text.toString()
                                var Freezable = Modifica_Freezable.text.toString()
                                var Date = Modifica_Date.text.toString()

                                Item_Product_Name.text = Name
                                Item_Product_Date.text = Date

                                var Fridge = dbfridge("Nessuno", Name,
                                    Category,
                                    Description,
                                    Allergens,
                                    Unit,
                                    Recyclable,
                                    Freezable,
                                    Date)

                                Fridge.SetId(Product_Id.get(adapterPosition))

                                Handler.UpdateFridge(Fridge)

                                notifyDataSetChanged()
                                Toast.makeText(Context,"Le informazioni sono state modificate",Toast.LENGTH_LONG).show()
                                dialog.dismiss()

                            }
                            .setNegativeButton("Cancella"){
                                    dialog,_->
                                dialog.dismiss()

                            }
                            .create()
                            .show()

                        true
                    }
                    R.id.delete->{

                        AlertDialog.Builder(Context)
                            .setTitle("Cancella")

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
        // Log.d("Giorno corrente:" , currentDate.substring(0,2) + currentDate.substring(3,5) + currentDate.substring(6,10))
        //  Log.d("Giorno prodotto:" , Products_Date.get(position).substring(0,2)+ Products_Date.get(position).substring(3,5) + Products_Date.get(position).substring(6,10))

        if (Products_Date.get(position).substring(6, 10) < currentDate.substring(6, 10))
        {
            holder.LinearLayout_Item.setBackgroundColor(ContextCompat.getColor(Context!!, R.color.mygray))
        } else if (Products_Date.get(position).substring(6, 10) > currentDate.substring(6, 10))
        {
            holder.LinearLayout_Item.setBackgroundColor(ContextCompat.getColor(Context!!, R.color.mygreen))
        } else if (Products_Date.get(position).substring(6, 10) == currentDate.substring(6, 10))
        {
            if (Products_Date.get(position).substring(3, 5) < currentDate.substring(3, 5))
            {
                holder.LinearLayout_Item.setBackgroundColor(ContextCompat.getColor(Context!!, R.color.mygray))
            } else if (Products_Date.get(position).substring(3, 5) > currentDate.substring(3, 5))
            {
                holder.LinearLayout_Item.setBackgroundColor(ContextCompat.getColor(Context!!, R.color.mygreen))
            } else if (Products_Date.get(position).substring(3, 5) == currentDate.substring(3, 5))
            {

                var DiffDay: Int =
                    Products_Date.get(position).substring(0, 2).toInt() - currentDate.substring(0,2).toInt()
                if (DiffDay > 7)
                {
                    holder.LinearLayout_Item.setBackgroundColor(ContextCompat.getColor(Context!!, R.color.mygreen))
                } else if (DiffDay == 0)
                {
                    holder.LinearLayout_Item.setBackgroundColor(ContextCompat.getColor(Context!!, R.color.myred))
                } else if (DiffDay > 3 && DiffDay < 7)
                {
                    holder.LinearLayout_Item.setBackgroundColor(ContextCompat.getColor(Context!!, R.color.myyellow))
                } else if (DiffDay > 0 && DiffDay <= 3)
                {
                    holder.LinearLayout_Item.setBackgroundColor(Color.rgb(255, 128, 0))
                } else if (DiffDay < 0)
                {
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
