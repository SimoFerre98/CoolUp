package com.Simo_Elia.CoolUp

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_search.*
import java.sql.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fridge.newInstance] factory method to
 * create an instance of this fragment.
 */




class search : Fragment()  {

    var Toggle: Switch?= null;
    var progressBar: ProgressBar?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View?
    {
        val view:View = inflater.inflate(R.layout.fragment_settings,container,false)

        Toggle = view.findViewById<Switch>(R.id.ToggleDB)

        Toggle!!.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener{
                buttonView, isChecked ->
            if(isChecked)
            {
                Toast.makeText(context,"DB Launching",Toast.LENGTH_LONG).show()
                val checkLogin = CheckLogin(view,requireContext())
                checkLogin.execute("")
            }
            else
            {
                Toast.makeText(context,"DB Disabled",Toast.LENGTH_LONG).show()
            }
        })
        return  view
    }

    class CheckLogin(view : View, context: Context) : AsyncTask<String?, String?, String?>() {
        var progressBar: ProgressBar= view.findViewById(R.id.progressBar)
        var message: TextView= view.findViewById<View>(R.id.message) as TextView
        var context = context
        var z: String? = ""
        var isSucces = false
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
                    "jdbc:jtds:sqlserver://coolup.database.windows.net:1433;DatabaseName=CoolUp;user=CoolUp_Admin@coolup;password=EliaFerre21;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;\n"
                connection = DriverManager.getConnection(ConnectionURL)


            } catch (se: SQLException) {
                Log.e("error here 1 : ", se.message!!)
            } catch (e: ClassNotFoundException) {
                Log.e("error here 2 : ", e.message!!)
            } catch (e: Exception) {
                Log.e("error here 3 : ", e.message!!)
            }
            return connection
        }


        override fun onPreExecute() {
            progressBar.setVisibility(View.VISIBLE)
        }

        override fun onPostExecute(r: String?) {
            progressBar.setVisibility(View.GONE)
            Toast.makeText(context, r, Toast.LENGTH_LONG).show()
            if (isSucces) {
                message.setText(EAN)
            }
        }

        override fun doInBackground(vararg params: String?): String? {
            try {
                con = connectionclass() // Connect to database
                if (con == null) {
                    z = "Check Your Internet Access!"
                } else {
                    val query = "select * from Product"
                    val stmt: Statement = con!!.createStatement()
                    val rs: ResultSet = stmt.executeQuery(query)
                    if (rs.next()) {
                        EAN =
                            rs.getString("EAN") //Name is the string label of a column in database, read through
                        isSucces = true
                        con!!.close()
                    }
                }
            } catch (ex: java.lang.Exception) {
                isSucces = false
                z = ex.message
                Log.d("sql error", z!!)
            }
            return z
        }
    }
}

