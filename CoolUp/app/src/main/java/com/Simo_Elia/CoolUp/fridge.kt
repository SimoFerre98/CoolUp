package com.Simo_Elia.CoolUp
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [fridge.newInstance] factory method to
 * create an instance of this fragment.
 */
class fridge : Fragment(R.layout.fragment_fridge) {
    lateinit var recyclerView : RecyclerView
    lateinit var floatingActionButton: FloatingActionButton
    lateinit var Manual_Fab: FloatingActionButton
    lateinit var Bluetooth_Fab: FloatingActionButton
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_fridge,container,false)
        view.findViewById<RecyclerView>(R.id.FrigoRecycleView)
        view.findViewById<FloatingActionButton>(R.id.fab)
        view.findViewById<FloatingActionButton>(R.id.Manual_fab)
        view.findViewById<FloatingActionButton>(R.id.Bluetooth_Scan)
        floatingActionButton.setOnClickListener(View.OnClickListener {
            Manual_Fab!!.setVisibility(View.VISIBLE)
            Bluetooth_Fab!!.setVisibility(View.VISIBLE)
        })
        return view
    }
}