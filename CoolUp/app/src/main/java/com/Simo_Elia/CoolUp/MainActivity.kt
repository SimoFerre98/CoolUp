package com.Simo_Elia.CoolUp

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream
import java.io.OutputStream
import java.util.*

class MainActivity : AppCompatActivity()  {
    // Toolbar variable
    private lateinit var myToolbar: androidx.appcompat.widget.Toolbar

    // Bluetooth constant
    val mUUID : UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")


    // Settings when launching the application
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //*------------------------------------------------

        // BlueTooth Connections
        var btAdapter : BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        println(btAdapter.bondedDevices)
        var hc05 : BluetoothDevice  = btAdapter.getRemoteDevice("0020:12:08C2EB")
        println(hc05.name)

        // Try to connect with 3 attempts
        var btSocket:BluetoothSocket?= null
        var counter = 0;
        do{
            btSocket = hc05.createRfcommSocketToServiceRecord(mUUID);
            println(btSocket);
            btSocket.connect()
            println(btSocket.isConnected)

            counter++
        } while (!btSocket!!.isConnected && counter < 3)

        // Ricevere la Stringa

        var outputStream: OutputStream =btSocket.outputStream
        var inputStream :InputStream = btSocket.inputStream;


        /*
        // Stampa lettere dell'alfabeto

        var outputStream: OutputStream =btSocket.outputStream
        outputStream.write(40)

        var inputStream :InputStream = btSocket.inputStream;
        // Per pulire il buffer precedente mettendo lo skip passando per parametro la dimensione dello inputStream
        inputStream.skip(inputStream.available().toLong());

        for( i in 1..26){
            var b : Byte=  inputStream.read().toByte()
            println(b.toChar())
        }*/

        btSocket.close()
        println(btSocket.isConnected)


        //*------------------------------------------------

        // Toolbar association
        myToolbar = findViewById(R.id.myToolbar)
        myToolbar.title=""
        setSupportActionBar(myToolbar)

        // BottomNav middle Item disabled
        bottomNavigationView.menu.getItem(2).isEnabled = false

        // BottomNavigation association
        val bottomNav: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        // Launching the main fragment : fridge
        val fridge= fridge();
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fridge)
            commit()
        }
    }

    // toast message function
    private fun showToast (msg: String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }

    // Listener for BottomNavigation
    private val navListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                val fridge= fridge()
                val shoplist=shoplist()
                val search=search()
                val settings=settings()

                when (item.itemId) {
                    // Fridge Option
                    R.id.MyFridge ->
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, fridge)
                        commit()
                    }
                    // ShopList Option
                    R.id.MyShoppingList ->
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, shoplist)
                        commit()
                    }
                    // Search Option
                    R.id.Search ->
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, search)
                        commit()
                    }
                    // Settings Option
                    R.id.Settings ->
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, settings)
                        commit()
                    }
                }
                return true
            }
        }


}