package com.example.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //specify which layout is called when activity is started
        setContentView(R.layout.activity_main)

        //R class allows us to access files in folders [drawable, layout, minimap, values]
        //To access strings in values folder --- >  var my_app_name =  R.string.app_name;
    }
}
