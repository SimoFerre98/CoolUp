package com.Simo_Elia.CoolUp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class manualactivity : AppCompatActivity() {

    var Edite_Manual_Name: EditText? = null
    var Edite_Manual_Category: EditText? = null
    var Edite_Manual_Description: EditText? = null
    var Edite_Manual_Quantity: EditText? = null
    var Edite_Manual_Date: EditText? = null

    var Manual_btn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manual_activity)

        Edite_Manual_Name = findViewById(R.id.Edit_Manual_Name)
        Edite_Manual_Category = findViewById(R.id.Edit_Manual_Category)
        Edite_Manual_Description = findViewById(R.id.Edit_Manual_Desciption)
        Edite_Manual_Quantity = findViewById(R.id.Edit_Manual_Quantity)
        Edite_Manual_Date = findViewById(R.id.Edit_Manual_Date)

        Manual_btn = findViewById(R.id.Manual_Btn)
    }

    override fun onStart() {
        super.onStart()
        Log.d("FUORI: ","Fuori dal click")
        Manual_btn?.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v:View?){
                Log.d("Cliccato: ","CLICK DENTRO IL CLICK LISTENER")

                var Handler = dbhandler(this@manualactivity)

                var frigo = dbfridge("33224954578", Edite_Manual_Name?.text.toString(),
                    Edite_Manual_Category?.text.toString(),
                    Edite_Manual_Description?.text.toString(),
                    Edite_Manual_Quantity?.text.toString(),
                    Edite_Manual_Quantity?.text.toString(),
                    "Nessuno",
                    "Nessuno",
                    Edite_Manual_Date?.text.toString())

                Handler.InsertFridge(frigo)

                Toast.makeText(this@manualactivity,"Inserimento corretto", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@manualactivity, MainActivity::class.java)
                startActivity(intent)
            }
        })
    }
}