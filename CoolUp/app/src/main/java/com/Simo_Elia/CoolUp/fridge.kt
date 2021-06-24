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
class fridge : Fragment() {
    var recyclerView : RecyclerView ?=null
    var fab: FloatingActionButton ?=null
    var Manual_Fab: FloatingActionButton ?=null
    var Bluetooth_Fab: FloatingActionButton ?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        val view:View = inflater.inflate(R.layout.fragment_fridge,container,false)
        recyclerView=view.findViewById<RecyclerView>(R.id.FrigoRecycleView)
        fab= view.findViewById(R.id.fab)



        var Handler = dbhandler(context)
        var frigo = dbfridge("33224954578","pesce","carne","carne molto buona","Nessuno","1.5L","umido","SI","NO")
        Handler.InsertFridge(frigo)

        Manual_Fab= view.findViewById<FloatingActionButton>(R.id.Manual_fab)
        Bluetooth_Fab= view.findViewById<FloatingActionButton>(R.id.Bluetooth_Scan)
        view.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view:View?){
                Manual_Fab!!.setVisibility(View.VISIBLE)
                Bluetooth_Fab!!.setVisibility(View.VISIBLE)
            }
        })
        return view
    }
}


