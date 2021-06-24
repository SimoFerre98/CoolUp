package com.Simo_Elia.CoolUp

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import java.sql.*

class dbonline {


    class CheckLogin(view : View, context: Context,ProgressBar:Int,query:String) : AsyncTask<String?, String?, String?>() {
        var  ProgressBarIntId = ProgressBar
        var query = query
        var progressBar: ProgressBar = view.findViewById(ProgressBarIntId)

        var context = context
        var z: String? = ""
        var isSucces = false
        val rs: ResultSet ?= null
        var EAN = ""

        @SuppressLint("NewApi")
        fun connectionclass(): Connection? {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            var connection: Connection? = null
            var ConnectionURL: String? = null
            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver")
                ConnectionURL =
                    "jdbc:jtds:sqlserver://coolapp.database.windows.net:1433;DatabaseName=CoolUp;user=coolup_admin@coolapp;password=Eliaferre21;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;\n"
                connection = DriverManager.getConnection(ConnectionURL)


            } catch (se: SQLException) {
                Toast.makeText(context,"dbonline: Errore comando sql", Toast.LENGTH_SHORT).show()

            } catch (e: ClassNotFoundException) {
                Toast.makeText(context,"dbonline: Errore Classe non trovata", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context,"dbonline: Errore eccezione", Toast.LENGTH_SHORT).show()
            }
            return connection
        }


        override fun onPreExecute() {
            progressBar.setVisibility(View.VISIBLE)
        }

        override fun onPostExecute(r: String?) {
            progressBar.setVisibility(View.GONE)
        }

        override fun doInBackground(vararg params: String?): String? {
            try {
                con = connectionclass() // Connect to database
                if (con == null) {
                    z = "Connessione fallita!"
                } else {
                    val stmt: Statement = con!!.createStatement()
                    rs = stmt.executeQuery(query)
                    if (rs.next()) {
                        EAN =
                            rs.getString("EAN") //Name is the string label of a column in database, read through
                        isSucces = true
                        con!!.close()
                    }
                }
            } catch (ex: java.lang.Exception) {
                isSucces = false
                var z = "Errore"
                Toast.makeText(context,"dbonline: Errore sql", Toast.LENGTH_SHORT).show()
            }
            return z
        }

    }

}