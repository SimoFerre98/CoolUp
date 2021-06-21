package com.Simo_Elia.CoolUp

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import java.io.InputStream
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

       var value: Any=BarCode()


}

fun BarCode(){
    // BlueTooth Connections
    var btAdapter : BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    println("************************* TUTTI I DISPOSITIVI :"+btAdapter.bondedDevices)
    var hc05 : BluetoothDevice = btAdapter.getRemoteDevice("00:20:12:08:C2:EB")
    println("************************* NOME DISPOSITIVO :"+hc05.name)

    // Try to connect with 3 attempts
    var btSocket: BluetoothSocket
    var counter = 0;
    do{
        btSocket = hc05.createRfcommSocketToServiceRecord(mUUID);
        println("************************* NOME SOCKET :"+btSocket);
        btSocket.connect()
        println("************************* SOCKET CONNESSO? :"+btSocket.isConnected)
        counter++
    } while (!btSocket.isConnected && counter < 3)

    // Ricevere la Stringa
    var Barcode: String=""
    var inputStream : InputStream = btSocket.inputStream;
    inputStream.skip(inputStream.available().toLong());
    do{
        var SingleChar: Char = inputStream.read().toChar()
        Barcode+=SingleChar
    }
    while(inputStream.available()>0)
    println("************************* BARCODE :"+Barcode)


    btSocket.close()
    println("************************* SOCKET CONNESSO? :"+btSocket.isConnected)

}

