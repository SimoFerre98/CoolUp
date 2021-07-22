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
import androidx.core.text.isDigitsOnly
import java.text.SimpleDateFormat
import java.util.*

class manualactivity : AppCompatActivity() {

    var Edite_Manual_Name: EditText? = null
    var Edite_Manual_Category: EditText? = null
    var Edite_Manual_Description: EditText? = null
    var Edite_Manual_Quantity: EditText? = null
    var Edite_Manual_Date: EditText? = null
    var Check:Boolean = true
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

        Manual_btn?.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v:View?){

                Check = true
                var Handler = dbhandler(this@manualactivity)

                val currentDate: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                    Date()
                )
                var Date = Edite_Manual_Date?.text.toString()

                ParseDate(Date,currentDate,v)

                if(Check) {
                    var frigo = dbfridge(
                        "Nessuno", Edite_Manual_Name?.text.toString(),
                        Edite_Manual_Category?.text.toString(),
                        Edite_Manual_Description?.text.toString(),
                        "Nessuno",
                        Edite_Manual_Quantity?.text.toString(),
                        "Nessuno",
                        "Nessuno",
                        Edite_Manual_Date?.text.toString()
                    )

                    Handler.InsertFridge(frigo)

                    Toast.makeText(this@manualactivity, "Inserimento corretto", Toast.LENGTH_SHORT)
                        .show()

                    val intent = Intent(this@manualactivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }
    fun ParseDate(Date:String,currentDate:String,v:View?):Boolean
    {
            if(Date.length == 10)
            {
                if (Date.substring(6, 10).toInt() < currentDate.substring(6, 10).toInt()) {
                    Check = false
                    Toast.makeText(
                        v?.context,
                        "Anno minore dell'anno corrente",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (Date.substring(3, 5).toInt() > 12 || Date.substring(3, 5)
                        .toInt() < 1
                ) {
                    Check = false
                    Toast.makeText(v?.context, "Mese errato", Toast.LENGTH_LONG).show()
                } else if (Date.substring(0, 2).toInt() > 31 || Date.substring(0, 2)
                        .toInt() < 1
                ) {
                    Check = false
                    Toast.makeText(v?.context, "Giorno errato", Toast.LENGTH_LONG).show()
                }
            }else
            {
                Toast.makeText(v?.context, "Data errata", Toast.LENGTH_LONG).show()
                Check = false
                return false
            }

        return true
    }
}