package com.example.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //specify which layout is called when activity is started
        setContentView(R.layout.activity_main)

        //R class allows us to access files in folders [drawable, layout, minimap, values]
        //To access strings in values folder --- >  var my_app_name =  R.string.app_name;

        val rollButton: Button = findViewById(R.id.roll_button);
        val incrementButton: Button = findViewById(R.id.incrementer_button)
        val resetButton: Button = findViewById(R.id.reset_button);



        rollButton.setOnClickListener { rollDice() }
        incrementButton.setOnClickListener { incrementResult() }
        resetButton.setOnClickListener{resetResult()}

    }


    private fun rollDice() {


        val randomInt = Random().nextInt(6) + 1;
        val resultText: TextView = findViewById(R.id.result_text);

        resultText.text = randomInt.toString()

//        val resultText: TextView = findViewById(R.id.result_text);
//        resultText.text = getString(R.string.roll_message)

//        Toast.makeText(this, "button clicked",
//            Toast.LENGTH_SHORT).show()
    }


    private fun incrementResult() {
        val resultText: TextView = findViewById(R.id.result_text);
        val stringTextValue = resultText.text.toString();

        when {
            stringTextValue.toIntOrNull() == null -> {
                resultText.text = "1";
            }
            stringTextValue.toInt() == 6 -> {
                return;
            }
            else -> {
                var numValue = stringTextValue.toInt();
                numValue++;

                resultText.text = numValue.toString();


            }
        }


    }

    private fun resetResult(){

        val displayText: TextView = findViewById(R.id.result_text);


        val stringText:String = displayText.text.toString();


        if(stringText.toIntOrNull() != null){
            displayText.text = "0";
        }





    }
}
