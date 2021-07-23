package com.Simo_Elia.CoolUp.Settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.Simo_Elia.CoolUp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


/**
 * A simple [Fragment] subclass.
 * Use the [lightScanner.newInstance] factory method to
 * create an instance of this fragment.
 */
class lightScanner : Fragment() {
    lateinit var BackButton: FloatingActionButton


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.scannerlight,container,false)

        BackButton= view.findViewById(R.id.Indietro_Button)

        BackButton.setOnClickListener(object :View.OnClickListener{
        override fun onClick(v: View?) {
            changeFragment()
            }
        })
        return view
    }

    private fun changeFragment() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.flFragment, settings())
            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}