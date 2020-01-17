package com.example.aboutme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // it - refers to the done button
        findViewById<Button>(R.id.done_button).setOnClickListener { addNickname(it) };
        findViewById<TextView>(R.id.nickname_text).setOnClickListener { updateNickname(it) }
    }


    //BUTTON  DONE CLICK
    private fun addNickname(view: View) {
        val editText = findViewById<EditText>(R.id.nickname_edit);
        val nickNameTextView = findViewById<TextView>(R.id.nickname_text);

        //SET TEXT
        nickNameTextView.text = editText.text;

        //HIDE EDIT FIELD
        editText.visibility = View.GONE
        //HIDE BUTTON (view will be a button I click)
        view.visibility = View.GONE

        //MAKE NICKNAME TEXT VISIBLE
        nickNameTextView.visibility = View.VISIBLE


        // Hide the keyboard.
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)


    }


    //TEXT VIEW CLICK
    private fun updateNickname(view: View) {

        //GET REFS FOR CONTROLS
        val editText = findViewById<EditText>(R.id.nickname_edit)
        val doneButton = findViewById<Button>(R.id.done_button)


        //SET VISIBILITY
        editText.visibility = View.VISIBLE
        doneButton.visibility = View.VISIBLE
        view.visibility = View.GONE


        // Set the focus to the edit text.
        editText.requestFocus()


        // Show the keyboard.
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, 0)


    }




}
