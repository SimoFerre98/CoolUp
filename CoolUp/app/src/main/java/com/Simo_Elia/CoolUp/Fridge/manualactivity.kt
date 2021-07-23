package com.Simo_Elia.CoolUp.Fridge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.Simo_Elia.CoolUp.Database.dbdownload
import com.Simo_Elia.CoolUp.Database.dbfridge
import com.Simo_Elia.CoolUp.Database.dbhandler
import com.Simo_Elia.CoolUp.MainActivity
import com.Simo_Elia.CoolUp.R
import java.text.SimpleDateFormat
import java.util.*

class manualactivity : AppCompatActivity() {

    var Edite_Manual_Name: EditText? = null
    var Edite_Manual_Category: EditText? = null
    var Edite_Manual_Description: EditText? = null
    var Edite_Manual_Allergens: EditText? = null
    var Edite_Manual_Quantity: EditText? = null
    var Edite_Manual_Date: EditText? = null
    var Edite_Manual_Recyclable: CheckBox? = null
    var Edite_Manual_Freezable: CheckBox? = null
    var Check:Boolean = true
    var Manual_btn: Button? = null

    //  Variabili per il controllo degli inserimentii
    var Check_Name:String = "Nessun Nome"
    var Check_Category : String = "Nessuna Categorya"
    var Check_Description : String = "Nessuna Descrizione"
    var Check_Allergens : String = "Nessun Allergene"
    var Check_Unit : String = "Nessuna Quantità"
    var Check_Date : String = "Nessuna Data"
    var Check_Recyclable : String = "No"
    var Check_Freezable : String = "No"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manual_activity)

        Edite_Manual_Name = findViewById(R.id.Edit_Manual_Name)
        Edite_Manual_Category = findViewById(R.id.Edit_Manual_Category)
        Edite_Manual_Description = findViewById(R.id.Edit_Manual_Desciption)
        Edite_Manual_Quantity = findViewById(R.id.Edit_Manual_Quantity)
        Edite_Manual_Date = findViewById(R.id.Edit_Manual_Date)
        Edite_Manual_Allergens = findViewById(R.id.Edit_Manual_Allergens)
        Edite_Manual_Recyclable = findViewById(R.id.checkBox_Manual_Recycle)
        Edite_Manual_Freezable = findViewById(R.id.checkBox_Manual_Freezable)
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

                CheckInsert(v)

                Log.d("Name: ", Check_Name)
                Log.d("Categoria: ", Check_Category)
                Log.d("Descrizione: ", Check_Description)
                Log.d("Allergeni: ", Check_Allergens)

                if(Check) {
                    var frigo = dbfridge(
                        "0000000000000000",
                        Check_Name,
                        Check_Category,
                        Check_Description,
                        Check_Allergens,
                        Check_Unit,
                        Check_Recyclable,
                        Check_Freezable,
                        Edite_Manual_Date?.text.toString()
                    )

                    var download = dbdownload(
                        "0000000000000000",
                        Check_Name,
                        Check_Category,
                        Check_Description,
                        Check_Allergens,
                        Check_Unit,
                        Check_Recyclable,
                        Check_Freezable
                    )
                    Handler.InsertFridge(frigo)
                    Handler.InsertDownload(download)
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

    fun CheckInsert(v:View?)
    {
        //  Controllo del nome
        if(Edite_Manual_Name?.text!!.isEmpty() == false)
        {
            Check_Name = Edite_Manual_Name?.text.toString()

        }else
        {
            Toast.makeText(v?.context, "Inserisci il nome del prodotto", Toast.LENGTH_LONG).show()
            Check = false
        }

        //  Controllo della categoria
        if(Edite_Manual_Category?.text!!.isEmpty() == false)
        {
            Check_Category = Edite_Manual_Category?.text.toString()
        }

        //  Controllo della descrizione
        if(Edite_Manual_Description?.text!!.isEmpty() == false)
        {
           Check_Description = Edite_Manual_Description?.text.toString()
        }

        //  Controllo allergeni
        if(Edite_Manual_Allergens?.text!!.isEmpty() == false)
        {
            Check_Allergens = Edite_Manual_Allergens?.text.toString()
        }

        //  Controllo della quantità
        if(Edite_Manual_Quantity?.text!!.isEmpty() == false)
        {
            Check_Unit = Edite_Manual_Quantity?.text.toString()
        }

        //  Controllo della data
        if(Edite_Manual_Date?.text!!.isEmpty() == false)
        {
            Check_Date = Edite_Manual_Date?.text.toString()
        }

        //  Controllo reciclabile
        if(Edite_Manual_Recyclable?.isChecked == true)
        {
            Check_Recyclable = "Si"
        }else
        {
            Check_Recyclable = "No"
        }

        //  Controllo freezable
        if(Edite_Manual_Freezable?.isChecked == true)
        {
            Check_Freezable = "Si"
        }else
        {
            Check_Freezable = "No"
        }
    }
}