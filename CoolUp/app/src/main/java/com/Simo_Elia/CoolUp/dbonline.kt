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


    class CheckLogin(view : View, context: Context,ProgressBar:ProgressBar,query:String) : AsyncTask<String?, String?, String?>() {
        var query = query
        var progressBar=ProgressBar

        var context = context
        var z: String? = ""
        var isSucces = false
        var rs: ResultSet ?= null
        var EAN = ""

        @SuppressLint("NewApi")
        fun connectionclass(): Connection? {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            var connection: Connection? = null
            var ConnectionURL: String? = null
            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver")
                //  Porta 3306 nel caso non funzioni la 1433
                //
                ConnectionURL =
                    "jdbc:jtds:sqlserver://ns1.educationhost.cloud:3306;DatabaseName=zznohcja_coolup;user=zznohcja;password=9L-:yimL34WR3l;hostNameInCertificate=*.database.windows.net;\n"
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

        override fun doInBackground(vararg params: String?): String? {
            try {
                con = connectionclass() // Connect to database
                if (con == null) {
                    z = "Connessione fallita!"
                } else
                {
                    val stmt: Statement = con!!.createStatement()
                    rs = stmt.executeQuery(query)
                    if(rs!!.next())
                    {
                        EAN = rs!!.getString(1) //Name is the string label of a column in database, read through
                        isSucces = true
                        con!!.close()
                    }
                }
            } catch (ex: java.lang.Exception) {
                isSucces = false
                var z = "Errore"

            }
            return z
        }
        override fun onPostExecute(r: String?) {
            progressBar.setVisibility(View.GONE)
        }

    }

}