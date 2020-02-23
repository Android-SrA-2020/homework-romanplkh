package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


//INHERIT FROM ViewModel
class GameViewModel : ViewModel() {

    // The current word
    private val _word = MutableLiveData<String>();
    val word: LiveData<String>
        get() = _word;


    // The current score
    private val _score = MutableLiveData<Int>();

    val score: LiveData<Int>
        get() = _score;


    private val _eventGameFinished = MutableLiveData<Boolean>();

    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished;

    // The list of words - the front of the list is the next word to guess
    lateinit var wordList: MutableList<String>


    fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }


    init {
        resetList()
        nextWord()

        //Initialize default values for mutable live data
        _word.value = "";
        _score.value = 0;


    }


    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed")
    }


    fun nextWord() {
        if (wordList.isEmpty()) {

            onGameFinished();
        } else {
            //Select and remove a word from the list
            _word.value = wordList.removeAt(0)
        }

    }

    fun onSkip() {
        _score.value = (score.value)?.minus(1);
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1);
        nextWord()
    }


    fun onGameFinished() {
        _eventGameFinished.value = true;
    }

    fun onGameFinishedComplete() {
        _eventGameFinished.value = false
    }

}