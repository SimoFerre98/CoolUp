package com.Simo_Elia.CoolUp.Search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.Simo_Elia.CoolUp.R

class search : Fragment()  {

    lateinit var SearchView:SearchView
    //lateinit var SearchRecycleView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View?
    {
        val view:View = inflater.inflate(R.layout.fragment_search,container,false)



        return  view
    }

}

