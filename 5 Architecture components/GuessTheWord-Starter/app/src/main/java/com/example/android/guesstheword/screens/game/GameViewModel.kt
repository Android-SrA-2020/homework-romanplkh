package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.*


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


    //Add Observable for current time
    private val _currentTime = MutableLiveData<Long>();
    val currentTime: LiveData<Long>
        get() = _currentTime;


    //MAP ELAPSED TIME AND FORMAT IT
    //Allows us to convert LiveData object to another one
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time);
    }

    //ADD HIN
    val wordHint = Transformations.map(word) { word ->
        val randomPosition = (1..word.length).random()
        "Current word has " + word.length + " letters" +
                "\nThe letter at position " + randomPosition + " is " +
                word.get(randomPosition - 1).toUpperCase()
    }


    //Add variable for timer
    private val timer: CountDownTimer


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


    companion object {
        // Time when the game is over
        private const val DONE = 0L

        // Countdown time interval
        private const val ONE_SECOND = 1000L

        // Total time for the game
        private const val COUNTDOWN_TIME = 60000L
    }


    init {


        //Init timer

        //@param1 - TOTAL TIME
        //@param2 - TIME INTERVAL
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            //@param - the amount of time until the timer is finished in milliseconds
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished / ONE_SECOND;
            }

            override fun onFinish() {
                _currentTime.value = DONE;
                onGameFinished();
            }
        }

        //Start timer
        timer.start();

        //Initialize default values for mutable live data
        _word.value = "";
        _score.value = 0;

        resetList()
        nextWord()


    }


    //Unsubscribe from everything
    override fun onCleared() {
        super.onCleared()

        //Cancel timer
        timer.cancel();

    }


    fun nextWord() {
        if (wordList.isEmpty()) {
            resetList();
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