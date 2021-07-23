package com.Simo_Elia.CoolUp

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Simo_Elia.CoolUp.Search.SearchRecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class shoplist : Fragment() {

    lateinit var fab: FloatingActionButton
    var Clicked:Boolean =false
    lateinit private var Delete_Btn : ImageView
    val Shoplist_Name: MutableList<String> = ArrayList()
    val Shoplist_unit: MutableList<String> = ArrayList()
    var LayoutManager : RecyclerView.LayoutManager ? = null
    var adapter : RecyclerView.Adapter<ListRecyclerView.ViewHolder>?= null
    lateinit var ShoplistRecyclerView : RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View?
    {
        val view: View = inflater.inflate(R.layout.fragment_shoplist, container, false)

        var Handler = dbhandler(context)
/*
        var frigo1 = dblist("2","Prosciutto")
        var frigo2 = dblist("2","Anguria")
        var frigo = dblist("2","Mela")
        Handler.InsertShopList(frigo)
        Handler.InsertShopList(frigo1)
        Handler.InsertShopList(frigo2)
*/
        var ShopList = Handler.ShopListLists

        ShoplistRecyclerView = view.findViewById<RecyclerView>(R.id.ShopListRecyclerView)
        Delete_Btn = requireActivity().findViewById(R.id.ShoplistDeleteAll)

        fab = requireActivity().findViewById(R.id.fab_list)

        for(i in ShopList.iterator())
        {
            Shoplist_Name.add(i.Name)
            Shoplist_unit.add(i.Unit)
        }

        LayoutManager = LinearLayoutManager(context)
        ShoplistRecyclerView.layoutManager = LayoutManager

        adapter = ListRecyclerView(
            context,
            Shoplist_Name,
            Shoplist_unit,
            fab
        )
        ShoplistRecyclerView.adapter = adapter

        if(Handler.DimShopListTable() == 0)
        {
            Toast.makeText(context,"Non ci sono prodotti nella tua lista", Toast.LENGTH_SHORT).show()
        }

        Delete_Btn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v:View?){
                if(!Clicked)
                {
                    Handler.DeleteAllShoplist()
                    Clicked=true
                } else
                {
                    Clicked=false
                }
                }
        })

        return view
    }

}