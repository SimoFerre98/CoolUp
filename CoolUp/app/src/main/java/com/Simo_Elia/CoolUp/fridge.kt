package com.Simo_Elia.CoolUp
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.security.AccessController.getContext

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

    var Toggle:Switch ?= null;

    var recyclerView : RecyclerView ?=null
    lateinit var fab: FloatingActionButton
    lateinit var Manual_Fab: FloatingActionButton
    lateinit var Bluetooth_Fab: FloatingActionButton
    var Clicked:Boolean =false
    var bluetoothAdapter= BluetoothAdapter.getDefaultAdapter()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view:View = inflater.inflate(R.layout.fragment_fridge,container,false)

        recyclerView=view.findViewById<RecyclerView>(R.id.FrigoRecycleView)
        //fab= view.findViewById(R.id.fab)

        fab= requireActivity().findViewById<FloatingActionButton>(R.id.fab)
        Manual_Fab= requireActivity().findViewById<FloatingActionButton>(R.id.Manual_fab)
        Bluetooth_Fab= requireActivity().findViewById<FloatingActionButton>(R.id.Bluetooth_Scan)
        fab.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view:View?){
                if(!Clicked) {
                    Manual_Fab.visibility = View.VISIBLE
                    Bluetooth_Fab.visibility = (View.VISIBLE)
                    Clicked=true
                    Bluetooth_Fab.setOnClickListener(object : View.OnClickListener{
                        override fun onClick(view:View?){
                            if(!bluetoothAdapter.isEnabled){
                                Toast.makeText(context,"Attivare il Bluetooth",Toast.LENGTH_SHORT).show()
                            }
                            else{
                                Toast.makeText(context,"Accensione Scanner",Toast.LENGTH_SHORT).show()
                                var result= GetProductString(view!!,context!!,Bluetooth_Fab)
                                result.execute()
                                var EAN: String?= result.EAN
                                println(EAN)
                            }

                        }
                    })
                }
                else{
                    Manual_Fab.visibility = View.INVISIBLE
                    Bluetooth_Fab.visibility = (View.INVISIBLE)
                    Clicked=false
                }
            }
        })
        return view
    }

    class GetProductString (view : View, context: Context, Bluetooth_Fab: FloatingActionButton ) : AsyncTask<String?, String?, String?>(){
        var context=context
        var Bluetooth_Fab= Bluetooth_Fab
        var EAN:String?=null
        var Success=false
        override fun onPreExecute() {
            Bluetooth_Fab.setImageResource(com.Simo_Elia.CoolUp.R.drawable.ic_action_on)
        }
        override fun onPostExecute(r: String?) {
            Bluetooth_Fab.setImageResource(com.Simo_Elia.CoolUp.R.drawable.scanner_blue_filled)
            if(Success==false) Toast.makeText(context,"Nessun Dispositivo/Prodotto Rilevato",Toast.LENGTH_LONG).show()
        }
        override fun doInBackground(vararg params: String?) : String? {
            //Toast.makeText(context,"Click",Toast.LENGTH_SHORT).show()
            try{
                EAN= barCode()
                Success=true
            }
            catch (ex: java.lang.Exception) {
                Success=false
            }
            return null
        }
    }
}



fun barCode() :String{
    // BlueTooth Connections
    var btAdapter : BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    println("************************* TUTTI I DISPOSITIVI :"+btAdapter.bondedDevices)
    var hc05 : BluetoothDevice = btAdapter.getRemoteDevice("00:20:12:08:C2:EB")
    println("************************* NOME DISPOSITIVO :"+hc05.name)

    // Try to connect with 3 attempts
    var btSocket: BluetoothSocket
    var counter = 0;
    do{
        btSocket = hc05.createRfcommSocketToServiceRecord(mUUID);
        println("************************* NOME SOCKET :"+btSocket);
        btSocket.connect()
        println("************************* SOCKET CONNESSO? :"+btSocket.isConnected)
        counter++
    } while (!btSocket.isConnected && counter < 3)

    // Ricevere la Stringa
    var Barcode: String=""
    var inputStream : InputStream = btSocket.inputStream;
    inputStream.skip(inputStream.available().toLong());
    do{
        var SingleChar: Char = inputStream.read().toChar()
        Barcode+=SingleChar
    }
    while(inputStream.available()>0)
    println("************************* BARCODE :"+Barcode)


    btSocket.close()
    println("************************* SOCKET CONNESSO? :"+btSocket.isConnected)
    return Barcode
}
