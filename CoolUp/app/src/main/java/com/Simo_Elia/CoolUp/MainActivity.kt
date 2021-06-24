package com.Simo_Elia.CoolUp

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

var con:Connection?=null

class MainActivity : AppCompatActivity()  {
    // Connection SQL


    // Toolbar variable
    private lateinit var myToolbar: androidx.appcompat.widget.Toolbar

    var Toggle : Switch ?=null

    lateinit private var fab : FloatingActionButton
    lateinit private var fab_list : FloatingActionButton
    lateinit private var Manual_fab : FloatingActionButton
    lateinit private var Bluetooth_Scan : FloatingActionButton


    // Settings when launching the application
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Ricerca del fab
        fab = findViewById(R.id.fab)
        fab_list = findViewById(R.id.fab_list)
        Manual_fab = findViewById(R.id.Manual_fab)
        Bluetooth_Scan = findViewById(R.id.Bluetooth_Scan)


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
        Manual_fab.visibility = View.INVISIBLE
        Bluetooth_Scan.visibility = View.INVISIBLE
        fab_list.visibility = View.INVISIBLE

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
            @RequiresApi(Build.VERSION_CODES.Q)
            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                val fridge= fridge()
                val shoplist=shoplist()
                val search=search()
                val settings=settings()

                when (item.itemId) {
                    // Fridge Option
                    R.id.MyFridge ->
                    {
                        fab.visibility = View.VISIBLE
                        fab_list.visibility = View.INVISIBLE
                        Manual_fab.visibility = View.INVISIBLE
                        Bluetooth_Scan.visibility = View.INVISIBLE
                        supportFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, fridge)
                        commit()
                        }
                    }
                    // ShopList Option
                    R.id.MyShoppingList ->
                    {
                        fab.visibility = View.VISIBLE
                        fab_list.visibility = View.VISIBLE
                        Manual_fab.visibility = View.INVISIBLE
                        Bluetooth_Scan.visibility = View.INVISIBLE

                        supportFragmentManager.beginTransaction().apply {
                            replace(R.id.flFragment, shoplist)
                            commit()
                        }
                    }
                    // Search Option
                    R.id.Search ->
                    {
                        fab.visibility = View.INVISIBLE
                        fab_list.visibility = View.INVISIBLE
                        Manual_fab.visibility = View.INVISIBLE
                        Bluetooth_Scan.visibility = View.INVISIBLE
                        supportFragmentManager.beginTransaction().apply {
                            replace(R.id.flFragment, search)
                            commit()
                        }
                    }
                    // Settings Option
                    R.id.Settings ->
                    {
                        fab.visibility = View.INVISIBLE
                        fab_list.visibility = View.INVISIBLE
                        Manual_fab.visibility = View.INVISIBLE
                        Bluetooth_Scan.visibility = View.INVISIBLE
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, settings)
                        commit()}
                    }
                }
                return true
            }
        }





}