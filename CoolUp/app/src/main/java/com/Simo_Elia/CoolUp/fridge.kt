package com.Simo_Elia.CoolUp
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Switch
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [fridge.newInstance] factory method to
 * create an instance of this fragment.
 */
class fridge : Fragment(){

    var Toggle:Switch ?= null;

    lateinit var Fridge_RecyclerView : RecyclerView
    lateinit var fab: FloatingActionButton
    lateinit var Manual_Fab: FloatingActionButton
    lateinit var Bluetooth_Fab: FloatingActionButton
    var Clicked:Boolean =false
    var bluetoothAdapter= BluetoothAdapter.getDefaultAdapter()
    val Products_Name: MutableList<String> = ArrayList()
    val Products_Date: MutableList<String> = ArrayList()
    val Products_Unit: MutableList<String> = ArrayList()
    //val Products_EAN: MutableList<String> = ArrayList()
    val Products_Category: MutableList<String> = ArrayList()
    val Products_Description: MutableList<String> = ArrayList()
    val Products_Allergens: MutableList<String> = ArrayList()
    val Products_Recycle: MutableList<String> = ArrayList()
    val Products_Freezable: MutableList<String> = ArrayList()
    val Product_Id :MutableList<Int> = ArrayList()
    var LayoutManager : RecyclerView.LayoutManager ? = null
    var adapter : RecyclerView.Adapter<RecycleViewFridgeAdapter.ViewHolder>? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_fridge,container,false)
        //  Imposta la progressobar Insivibile appena viene cambiata l'activity, farlo direttamente nel xml e renderla invisibile sempre tranne quando si deve mostrare a schertmo

        //fab= view.findViewById(R.id.fab)

        var Handler = dbhandler(context)
        /*
            var frigo1 = dbfridge("33224954577","Carne","carne","carne molto buona","Nessuno","1.5kg","umido","SI","22/77/44")
            var frigo2 = dbfridge("33224954576","Acqua","Bevande","Acqua molto buonan","Nessuno","1.5L","Plastica","NO","14/07/98")
            var frigo = dbfridge("33224954578","Pesce","carne","carne molto buona","Nessuno","1.5Kg","umido","SI","12/78/55")
            Handler.InsertFridge(frigo)
            Handler.InsertFridge(frigo1)
            Handler.InsertFridge(frigo2)
        */

        //  Creazione degli oggetti che puntano ai widget nei fragment
        Fridge_RecyclerView=view.findViewById<RecyclerView>(R.id.FrigoRecycleView)
        Fridge_RecyclerView.setNestedScrollingEnabled(false);
        fab= requireActivity().findViewById<FloatingActionButton>(R.id.fab)
        Manual_Fab= requireActivity().findViewById<FloatingActionButton>(R.id.Manual_fab)
        Bluetooth_Fab= requireActivity().findViewById<FloatingActionButton>(R.id.Bluetooth_Scan)

        //  Viene creato un oggetto che contiene tutte le tuple presenti nella table download
        var fridgelists = Handler.FridgeLists
        for(i in fridgelists.iterator())
        {
            //  Ognu tupla viene iterata e il nome della tupla viene inserito in un arraylist che contiene i nomi di tutti i prodotti
            Products_Name.add(i.GetName())
            //  Identica cosa viene fatta per la data
            Products_Date.add(i.GetDate())
            Product_Id.add(i.GetId())
            Products_Unit.add(i.GetUnit())
            Products_Description.add(i.GetDescription())
            Products_Allergens.add(i.GetAllergens())
            Products_Recycle.add(i.GetRecyclable())
            Products_Freezable.add(i.GetFreezable())
            Products_Category.add(i.GetCategory())
            // Products_EAN.add(i.GetEAN())
        }

        LayoutManager = LinearLayoutManager(context)
        //FrigoRecycleView.setLayoutManager(LinearLayoutManager(context));
        Fridge_RecyclerView.layoutManager = LayoutManager
        adapter = RecycleViewFridgeAdapter(context,
            Products_Name,
            Products_Date,
            Product_Id,
            //Product_EAN,
            Products_Unit,
            Products_Category,
            Products_Description,
            Products_Allergens,
            Products_Recycle,
            Products_Freezable,
        )
        Fridge_RecyclerView.adapter = adapter

        // Controlla se la table adibita al frigo è vuota, se è vuota notifica con un toast
        if(Handler.DimFridgeTable() == 0)
        {
            Toast.makeText(context,"Non ci sono prodotti nel tuo frigo",Toast.LENGTH_SHORT).show()
        }

        //  Se viene cliccato il fab centrale con il '+' vengono fatti visualizzare gli altri due fab
        fab.setOnClickListener(object : View.OnClickListener
        {
            override fun onClick(v:View?){
                if(!Clicked) {
                    //  Se viene cliccato, si impostano a visible i due fab
                    Manual_Fab.visibility = View.VISIBLE
                    Bluetooth_Fab.visibility = (View.VISIBLE)
                    Clicked=true

                    //  Listener bluetooth fab - Se viene cliccato il fab riferito al bluetooth
                    Bluetooth_Fab.setOnClickListener(object : View.OnClickListener{
                        override fun onClick(v:View?){
                            if(!bluetoothAdapter.isEnabled){
                                //  Se il bluetooth non è attivo, viene visualizzato un toast che impone di attivarlo
                                Toast.makeText(context,"Attivare il Bluetooth",Toast.LENGTH_SHORT).show()
                            }
                            else{
                                //  Viene richiamato il metodo GetProductString che si occupa di ottenere dal dispositivo bluetooth un codice ean e di inserirlo nel db locale
                                Toast.makeText(context,"Accensione Scanner",Toast.LENGTH_SHORT).show()
                                var result= GetProductString(view!!,context!!,Bluetooth_Fab)
                                result.execute()
                                var EAN: String?= result.EAN
                                println(EAN)
                            }
                        }
                    })
                    //  Listener inserimento manuale - Se viene cliccato il fab riferito all'inserimento manuale
                    Manual_Fab.setOnClickListener(object : View.OnClickListener{
                        override fun onClick(v:View?){
                            //  Viene cambiata l'activity e rimandato all'inserimento manuale di un nuovo prodotto
                            val intent = Intent(context, manualactivity::class.java)

                            startActivity(intent)
                        }
                    })
                }
                else{
                    //  Altrimenti se non viene cliccato i due fab soprastanti al fab '+' rimangono invisibili
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
        var view = view
        var Bluetooth_Fab= Bluetooth_Fab
        var EAN:String=""
        var Success=false
        var fridge = dbfridge()
        var db = dbhandler(context)
        var query : String ?=null
        var progressBar: ProgressBar =view.findViewById(R.id.progressBarFridge)

        override fun onPreExecute() {
            Bluetooth_Fab.setImageResource(com.Simo_Elia.CoolUp.R.drawable.ic_action_on)
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

            //  Se allinterno del db download non è già presente il codice EAN
            query = "select * from Products "//where EAN = $EAN

            if(db.GetDownload(EAN) == null)
            {
                Log.d(TAG,"Non ho trovato EAN")
                //  Viene fatta una query al db azure chiedendo l'intera tupla del prodotto


                val checkLogin = dbonline.CheckLogin(view, context,progressBar,query!!)
                checkLogin.execute("")
                /*
                var fridge = dbfridge(checkLogin.EAN,
                    checkLogin.rs.getString("Name"),
                    checkLogin.rs.getString("Category"),
                            checkLogin.rs.getString("Allergens"),
                            checkLogin.rs.getString("Unit"),
                            checkLogin.rs.getString("Recyclable"),
                            checkLogin.rs.getString("Freezable"),
                            checkLogin.rs.getString("Date"))

                 */

                checkLogin.rs?.getString("EAN")?.let { fridge.SetEAN(it) }
                checkLogin.rs?.getString("Name")?.let { fridge.SetName(it) }
                checkLogin.rs?.getString("Category")?.let { fridge.SetCategory(it) }
                checkLogin.rs?.getString("Allergens")?.let { fridge.SetAllergens(it) }
                checkLogin.rs?.getString("Unit")?.let { fridge.SetUnit(it) }
                checkLogin.rs?.getString("Recyclable")?.let { fridge.SetRecyclable(it) }
                checkLogin.rs?.getString("Freezable")?.let { fridge.SetFreezable(it) }
                checkLogin.rs?.getString("Date")?.let { fridge.SetDate(it) }



                //  Inserimento dell'oggetto fridge scaricato al db azure e inserito sia nel db download che nel db fridge
                db.InsertFridge(fridge)
                db.InsertDownload(fridge)
            }else// Se ci fosse già l'oggetto in locale allora viene solo passato dentro la tabella fridge
            {
                Log.d(TAG,"Ho trovato EAN")
                //  Metodo che cerca dentro il db download il prodotto in base al codice EAN e ritorna l'oggetto con l'intera tupla
                var download = db.GetDownload(EAN!!)

                //  Si inseriscono dentro l'oggetto fridge i valori dell'oggetto download attraverso modificatori e selettori
                download?.let { fridge.SetEAN(it.GetEAN()) }
                download?.let { fridge.SetName(it.GetName()) }
                download?.let { fridge.SetCategory(it.GetCategory()) }
                download?.let { fridge.SetDescription(it.GetDescription()) }
                download?.let { fridge.SetAllergens(it.GetAllergens()) }
                download?.let { fridge.SetUnit(it.GetUnit()) }
                download?.let { fridge.SetRecyclable(it.GetRecyclable()) }
                download?.let { fridge.SetFreezable(it.GetFreezable()) }

                //  L'oggetto fridge appena creato viene inserito dentro il db fridge
                db.InsertFridge(fridge)
            }
            return null
        }

        override fun onPostExecute(r: String?) {
            Bluetooth_Fab.setImageResource(com.Simo_Elia.CoolUp.R.drawable.scanner_blue_filled)
            if(Success==false) Toast.makeText(context,"Nessun Dispositivo/Prodotto Rilevato",Toast.LENGTH_LONG).show()
        }
        companion object {
            private const val TAG = "fridge"
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
