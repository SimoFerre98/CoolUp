package com.Simo_Elia.CoolUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var myToolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*getSupportActionBar()?.setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_cool_up_white_official)*/


        setContentView(R.layout.activity_main)


        myToolbar = findViewById(R.id.myToolbar)
        myToolbar.title=""
        setSupportActionBar(myToolbar)

        bottomNavigationView.menu.getItem(2).isEnabled = false
        val bottomNav: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        val fridge= fridge();

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fridge)
            commit()


        }
    }

    private val navListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                val selectedFragment: Fragment?=null
                val fridge= fridge()
                val shoplist=shoplist()
                val search=search()
                val settings=settings()

                when (item.itemId) {

                    R.id.MyFridge -> selectedFragment@
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, fridge)
                        commit()
                    }
                    R.id.MyShoppingList -> selectedFragment@
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, shoplist)
                        commit()
                    }
                    R.id.Search -> selectedFragment@
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, search)
                        commit()
                    }
                    R.id.Settings -> selectedFragment@
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, settings)
                        commit()
                    }
                }
                return true
            }
        }

}