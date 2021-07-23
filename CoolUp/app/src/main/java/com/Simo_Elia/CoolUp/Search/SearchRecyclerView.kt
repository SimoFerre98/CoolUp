package com.Simo_Elia.CoolUp.Search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.Simo_Elia.CoolUp.R
import com.Simo_Elia.CoolUp.Database.dbhandler
import java.util.*
import kotlin.collections.ArrayList

class SearchRecyclerView(
    context: Context?,
    Dowload_Name: MutableList<String>,
    Dowload_Category: MutableList<String>,
    Dowload_Description: MutableList<String>,
    Dowload_Allergens: MutableList<String>,
    Dowload_Unit: MutableList<String>,
    Dowload_Recyclable: MutableList<String>,
    Dowload_Freezable: MutableList<String>
) : RecyclerView.Adapter<SearchRecyclerView.ViewHolder>(),Filterable {


    var Handler = dbhandler(context)
    var Dowload_Name_List = Dowload_Name
    var Dowload_Category_List = Dowload_Category
    var Dowload_Description_List = Dowload_Description
    var Dowload_Allergens_List = Dowload_Allergens
    var Dowload_Unit_List = Dowload_Unit
    var Dowload_Recyclable_List = Dowload_Recyclable
    var Dowload_Freezable_List = Dowload_Freezable
    var countryFilterList = ArrayList<String>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var Item_Search_Name : TextView
        var Item_Search_Description : TextView
        var Item_Search_Category : TextView
        var Item_Search_Allergens : TextView
        var Item_Search_Recyclable : TextView
        var Item_Search_Freezable : TextView

        init
        {
            Item_Search_Name = itemView.findViewById(R.id.SearchNameItem)
            Item_Search_Description = itemView.findViewById(R.id.SearchDescriptioItem)
            Item_Search_Category = itemView.findViewById(R.id.SearchCategoryItem)
            Item_Search_Allergens = itemView.findViewById(R.id.SearchAllergensItem)
            Item_Search_Recyclable = itemView.findViewById(R.id.SearchRecyclableItem)
            Item_Search_Freezable = itemView.findViewById(R.id.SearchfreezableItem)

            countryFilterList = Dowload_Name_List as ArrayList<String>
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.search_item,parent,false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.Item_Search_Name.text = Dowload_Name_List.get(position)
        holder.Item_Search_Description.text = Dowload_Description_List.get(position)
        holder.Item_Search_Category.text = Dowload_Category_List.get(position)
        holder.Item_Search_Allergens.text = Dowload_Allergens_List.get(position)
        holder.Item_Search_Recyclable.text = Dowload_Recyclable_List.get(position)
        holder.Item_Search_Freezable.text = Dowload_Freezable_List.get(position)
    }

    override fun getItemCount(): Int {
        return Handler.DimDownloadTable()
    }

    fun filteredList(resultList:ArrayList<String>)
    {
        countryFilterList = resultList
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter? {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    countryFilterList = Dowload_Name_List as ArrayList<String>
                } else {
                    val resultList = ArrayList<String>()
                    for (row in Dowload_Name_List) {
                        if (row.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    countryFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = countryFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countryFilterList = results?.values as ArrayList<String>
                notifyDataSetChanged()
            }

        }
    }


}