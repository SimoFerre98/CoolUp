package com.Simo_Elia.CoolUp.Search

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.Simo_Elia.CoolUp.R
import com.Simo_Elia.CoolUp.RecycleViewFridgeAdapter
import com.Simo_Elia.CoolUp.dbhandler

class search : Fragment()  {

    lateinit var SearchView:SearchView
    val Download_Name: MutableList<String> = ArrayList()
    val Download_Category: MutableList<String> = ArrayList()
    val Download_Description: MutableList<String> = ArrayList()
    val Download_Allergens: MutableList<String> = ArrayList()
    val Download_Unit: MutableList<String> = ArrayList()
    val Download_Recyclable: MutableList<String> = ArrayList()
    val Download_Freezable: MutableList<String> = ArrayList()
    var LayoutManager : RecyclerView.LayoutManager ? = null
    var adapter : RecyclerView.Adapter<SearchRecyclerView.ViewHolder>?= null



    lateinit var Search_RecyclerView : RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View?
    {
        val view:View = inflater.inflate(R.layout.fragment_search,container,false)


        var Handler = dbhandler(context)
        var DownloadList = Handler.DownloadLists
        Log.d("Lista download",DownloadList.toString())
        Log.d("Dimensione download",Handler?.DimDownloadTable().toString())
        Log.d("Dimensione download",Handler?.DimShopListTable().toString())
        for(i in DownloadList.iterator())
        {
            Download_Name.add(i.GetName())
            Download_Category.add(i.GetCategory())
            Download_Description.add(i.GetDescription())
            Download_Allergens.add(i.GetAllergens())
            Download_Unit.add(i.GetUnit())
            Download_Recyclable.add(i.GetRecyclable())
            Download_Freezable.add(i.GetFreezable())
        }
        Search_RecyclerView=view.findViewById<RecyclerView>(R.id.SearchRecycleView)
        Search_RecyclerView.setHasFixedSize(true)
        LayoutManager = LinearLayoutManager(context)
        Search_RecyclerView.layoutManager = LayoutManager
        adapter = SearchRecyclerView(
            context,
            Download_Name,
            Download_Category,
            Download_Description,
            Download_Allergens,
            Download_Unit,
            Download_Recyclable,
            Download_Freezable
        )
        Search_RecyclerView.adapter = adapter


        return  view
    }
/*
    @SuppressLint("ResourceType")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.id.SearchView,menu)

        SearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                TODO("Not yet implemented")
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }*/
}

