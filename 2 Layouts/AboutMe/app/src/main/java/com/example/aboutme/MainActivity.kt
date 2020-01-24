package com.example.aboutme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.example.aboutme.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val myNameThatHoldsValueForView: MyName = MyName("Roman Pelikh")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.myName = myNameThatHoldsValueForView


        //Associate activity_main xml with MainActivity.kt
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        binding.doneButton.setOnClickListener{addNickname(it)}
        binding.nicknameText.setOnClickListener { updateNickname(it) }

        // it - refers to the done button
//        findViewById<Button>(R.id.done_button).setOnClickListener { addNickname(it) };
//        findViewById<TextView>(R.id.nickname_text).setOnClickListener { updateNickname(it) }
    }


    //BUTTON  DONE CLICK
    private fun addNickname(view: View) {

        //KOTLIN VERSION
        binding.apply {
            myName?.nickname = nicknameEdit.text.toString()

            //REFRESH VIEW TO UPDATE NEW VALUE
            invalidateAll()

            nicknameEdit.visibility = View.GONE
            doneButton.visibility = View.GONE
            nicknameText.visibility = View.VISIBLE
        }

//        val editText = findViewById<EditText>(R.id.nickname_edit);
//        val nickNameTextView = findViewById<TextView>(R.id.nickname_text);

        //SET TEXT
        //The nicknameText requires a String, and nicknameEdit.text is an Editable.
        // When using data binding, it is necessary to explicitly convert the Editable to a String.
//        binding.nicknameText.text = binding.nicknameEdit.text.toString();
//
//        //HIDE EDIT FIELD
//        binding.nicknameEdit.visibility = View.GONE
//        //HIDE BUTTON (view will be a button I click)
//        binding.doneButton.visibility = View.GONE
//
//        //MAKE NICKNAME TEXT VISIBLE
//        binding.nicknameText.visibility = View.VISIBLE
//
//
//        // Hide the keyboard.
//        val inputMethodManager =
//            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
//
//
    }


    //TEXT VIEW CLICK
    private fun updateNickname(view: View) {

        /*
        GET REFS FOR CONTROLS
        val editText = findViewById<EditText>(R.id.nickname_edit)
        val doneButton = findViewById<Button>(R.id.done_button)
        SET VISIBILITY
        */

        //Kotlin version
        //KOTLIN VERSION
        binding.apply {
            nicknameEdit.visibility = View.VISIBLE
            doneButton.visibility = View.VISIBLE
            nicknameText.visibility = View.GONE
            nicknameText.visibility = View.VISIBLE
            nicknameEdit.requestFocus()
        }

//        binding.nicknameEdit.visibility = View.VISIBLE
//        binding.doneButton.visibility = View.VISIBLE
//
//        binding.nicknameText.visibility = View.GONE
//
//
//        // Set the focus to the edit text.
//        binding.nicknameEdit.requestFocus()


        // Show the keyboard.
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.nicknameEdit, 0)


    }




}
