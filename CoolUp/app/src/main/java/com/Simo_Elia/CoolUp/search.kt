package com.Simo_Elia.CoolUp

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_search.*
import java.sql.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fridge.newInstance] factory method to
 * create an instance of this fragment.
 */




class search : Fragment()  {

    var Toggle: Switch?=null
    var progressBar: ProgressBar?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View?
    {
        val query = "select * from Products"
        val view:View = inflater.inflate(R.layout.fragment_search,container,false)

       Toggle = view.findViewById(R.id.ToggleDB)

        Toggle!!.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if(isChecked)
                {
                    Toast.makeText(context,"DB Launching",Toast.LENGTH_SHORT).show()
                    var progressBar: ProgressBar =view.findViewById(R.id.progressBar)
                    var DBOnline = dbonline.CheckLogin(view,requireContext(),progressBar,query)
                    DBOnline.execute("")
                }
                else
                {
                    Toast.makeText(context,"DB Disabled",Toast.LENGTH_SHORT).show()
                }
            }
        })
        return  view
    }

}

