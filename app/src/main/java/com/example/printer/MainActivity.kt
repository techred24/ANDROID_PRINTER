package com.example.printer

import android.content.Context
import android.os.Bundle
import android.os.RemoteException
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.sunmi.peripheral.printer.InnerPrinterCallback
import com.sunmi.peripheral.printer.InnerPrinterException
import com.sunmi.peripheral.printer.InnerPrinterManager
import com.sunmi.peripheral.printer.InnerResultCallback
import com.sunmi.peripheral.printer.SunmiPrinterService


//import com.sunmi.externalprinterlibrary.api.SunmiPrinter

class MainActivity : AppCompatActivity() {

    var sunmiPrinterService: SunmiPrinterService? = null;

    var NoSunmiPrinter = 0x00000000;
    var CheckSunmiPrinter = 0x00000001;
    var FoundSunmiPrinter = 0x00000002;
    var LostSunmiPrinter = 0x00000003;

    var sunmiPrinter = CheckSunmiPrinter


    //boolean result = InnerPrinterManager.getInstance().bindService(context, innerPrinterCallback);
    //InnerPrinterCallback innerPrinterCallback = new InnerPrinterCallback()
    //InnerPrinterManager.getInstance().bindService(context, innerPrinterCallback)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        val innerPrinterCallback: InnerPrinterCallback = object : InnerPrinterCallback() {
            override fun onConnected(service: SunmiPrinterService) {
                println("onConnected")
                sunmiPrinterService = service
                println("$sunmiPrinterService EL SUNMIPRINTERSERVICE ASIGNADO");
                //checkSunmiPrinterService(service)
            }

            override fun onDisconnected() {
                println("onDisconnected");
                sunmiPrinterService = null
                sunmiPrinter = LostSunmiPrinter
            }
        }
        var result = InnerPrinterManager.getInstance().bindService(applicationContext, innerPrinterCallback);
        println("$result THE RESULT WHEN GETTING THE INSTANCE TO BIND SERVICE");

    }

    override fun onResume() {
        super.onResume();


        /*fun initSunmiPrinterService(context: Context?) {
            try {
                val ret = InnerPrinterManager.getInstance().bindService(
                    context,
                    innerPrinterCallback
                )
                if (!ret) {
                    sunmiPrinter = NoSunmiPrinter
                }
            } catch (e: InnerPrinterException) {
                println("SOME ERROR OCURRED");
                e.printStackTrace()
            }
        }*/

        fun imprimir() {
            println("$applicationContext APPLICATION CONTEXT");
            //initSunmiPrinterService(applicationContext);
            sunmiPrinterService?.printText("Printing test", object : InnerResultCallback() {
                override fun onRunResult(isSuccess: Boolean) {
                    //YYYYTODO("Not yet implemented")
                    println("$isSuccess THE RESULT TRYING TO PRINT SOMETHING. WITHIN THE CALLBACK");
                }

                override fun onReturnString(result: String?) {
                    //YYTODO("Not yet implemented")
                    println("onReturnString - result: $result")
                }

                override fun onRaiseException(code: Int, msg: String?) {
                    //YYTODO("Not yet implemented")
                    println("onRaiseException - code: $code , msg: $msg");
                }

                override fun onPrintResult(code: Int, msg: String?) {
                    //YYTODO("Not yet implemented")
                    println("onPrintResult - code: $code , smg: $msg");
                }

            });
        }
        var myButton = findViewById<Button>(R.id.printTest);
        myButton.setOnClickListener(View.OnClickListener {
            println("PRINTINGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
            imprimir();
        });

    }
}