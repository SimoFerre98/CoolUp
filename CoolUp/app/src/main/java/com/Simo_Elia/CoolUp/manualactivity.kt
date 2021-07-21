package com.Simo_Elia.CoolUp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class manualactivity : Activity() {

    var Manual_btn: Button? = null
    var Edite_Manual_Name: EditText? = null
    var Edite_Manual_Category: EditText? = null
    var Edite_Manual_Description: EditText? = null
    var Edite_Manual_Quantity: EditText? = null
    var Edite_Manual_Date: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manual_activity)

        Edite_Manual_Name?.findViewById<EditText>(R.id.Edit_Manual_Name)
        Edite_Manual_Category?.findViewById<EditText>(R.id.Edit_Manual_Category)
        Edite_Manual_Description?.findViewById<EditText>(R.id.Edit_Manual_Desciption)
        Edite_Manual_Quantity?.findViewById<EditText>(R.id.Edit_Manual_Quantity)
        Edite_Manual_Date?.findViewById<EditText>(R.id.Edit_Manual_Date)

        var Name : String = Edite_Manual_Name?.text.toString()

        Manual_btn?.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v:View?){

                var Handler = dbhandler(baseContext)
                var frigo = dbfridge("33224954578","Pesce","carne","carne molto buona","Nessuno","1.5Kg","umido","SI","12/78/55")
                Handler.InsertFridge(frigo)

                Toast.makeText(baseContext,"Il nome è: " + Name, Toast.LENGTH_SHORT).show()

                val intent = Intent(baseContext, fridge::class.java)
                startActivity(intent)
            }
        })
    }

}