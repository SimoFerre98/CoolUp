package com.Simo_Elia.CoolUp

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.Visibility.MODE_OUT
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream
import java.io.OutputStream
import java.util.*

class MainActivity : AppCompatActivity()  {
    // Toolbar variable
    private lateinit var myToolbar: androidx.appcompat.widget.Toolbar

    var Toggle : Switch ?=null
    private var fab : FloatingActionButton ?= null

    // Settings when launching the application
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Ricerca del fab
        fab = findViewById(R.id.fab)
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
                        fab!!.setVisibility(View.VISIBLE)
                        supportFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, fridge)
                        commit()
                        }
                    }
                    // ShopList Option
                    R.id.MyShoppingList ->
                    {
                        fab!!.setVisibility(View.VISIBLE)
                        supportFragmentManager.beginTransaction().apply {
                            replace(R.id.flFragment, shoplist)
                            commit()
                        }
                    }
                    // Search Option
                    R.id.Search ->
                    {
                            fab!!.setVisibility(View.INVISIBLE)
                        supportFragmentManager.beginTransaction().apply {
                            replace(R.id.flFragment, search)
                            commit()
                        }
                    }
                    // Settings Option
                    R.id.Settings ->
                    {
                        fab!!.setVisibility(View.INVISIBLE)
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, settings)
                        commit()}
                    }
                }
                return true
            }
        }


}