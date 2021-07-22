package com.Simo_Elia.CoolUp

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fridge.newInstance] factory method to
 * create an instance of this fragment.
 */

// Bluetooth constant
val mUUID : UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")


class settings : Fragment(R.layout.fragment_settings)  {

    var Toggle:Switch ?= null;
    val BLUETOOTH_REQ_CODE = 1
    lateinit var buttonBlue: Button
    lateinit var bluetoothAdapter: BluetoothAdapter
    lateinit var ButtonSite : Button
    lateinit var ButtonLight:Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View?
    {
        val view:View = inflater.inflate(R.layout.fragment_settings,container,false)

        buttonBlue = view.findViewById(R.id.btnBlue)
        ButtonSite = view.findViewById(R.id.Visit_Site_Btn)
        ButtonLight= view.findViewById(R.id.Scanner_Light_Button)

        bluetoothAdapter= BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            Toast.makeText(context,"Questo dispositivo non supporta il Bluetooth",Toast.LENGTH_SHORT).show()
        }

        if (!bluetoothAdapter!!.isEnabled) {
            buttonBlue.text = "OFF"
            buttonBlue.setBackgroundColor(resources.getColor(R.color.red))
        } else {
            buttonBlue.text = "ON"
            buttonBlue.setBackgroundColor(resources.getColor(R.color.green))
        }

        buttonBlue.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v:View)
            {
                if (!bluetoothAdapter.isEnabled) {

                    val bluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    startActivityForResult(bluetoothIntent, BLUETOOTH_REQ_CODE)

                } else {
                    val bluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    startActivityForResult(bluetoothIntent, 0)
                    bluetoothAdapter!!.disable()
                    buttonBlue.text = "OFF"
                    buttonBlue.setBackgroundColor(resources.getColor(R.color.red))
                }
            }
        })

        ButtonSite.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(context, coolupWabView::class.java)
                startActivity(intent)
            }
        })

        ButtonLight.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                changeFragment()
            }
        })
        return  view
    }


    private fun changeFragment() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.flFragment, lightScanner())
            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode==1) {
            Toast.makeText(context, "Bluetooth Abilitato", Toast.LENGTH_SHORT).show()
            buttonBlue.text = "ON"
            buttonBlue.setBackgroundColor(resources.getColor(R.color.green))
        }
        else if(resultCode == Activity.RESULT_OK && requestCode==0) {
            if(bluetoothAdapter.isEnabled){
                Toast.makeText(context, "Bluetooth Abilitato", Toast.LENGTH_SHORT).show()
                buttonBlue.text = "ON"
                buttonBlue.setBackgroundColor(resources.getColor(R.color.green))
            }
            else{
                Toast.makeText(context, "Bluetooth Disabilitato", Toast.LENGTH_SHORT).show()
                buttonBlue.text = "OFF"
                buttonBlue.setBackgroundColor(resources.getColor(R.color.red))
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED && requestCode==1) {

                Toast.makeText(context, "Bluetooth Disabilitato", Toast.LENGTH_SHORT).show()
                buttonBlue.text = "OFF"
                buttonBlue.setBackgroundColor(resources.getColor(R.color.red))

        }
    }
}




