/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.game

import android.hardware.camera2.TotalCaptureResult
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {


    //REF to game fragment so we can access methods in props in it
    private lateinit var viewModel: GameViewModel;

    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        //INIT VIEW MODEL
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )


        //Pass value of viewModel to binding data to xml view
        binding.gameViewModelVar = viewModel;


        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = viewLifecycleOwner




        //ATTACH OBSERVERS TO OBSERVABLE
//        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
//            binding.scoreText.text = newScore.toString();
//
//        })


//        viewModel.word.observe(viewLifecycleOwner, Observer { word ->
//            binding.wordText.text = word;
//        })


        //Observer that will move to the score if no more questions left
        viewModel.eventGameFinished.observe(viewLifecycleOwner, Observer<Boolean> { gameIsNotPlaying ->
            if (gameIsNotPlaying) gameFinished();
        })


        //AFTER ATTACHING BINDING LISTENERS WE NO LONGER NEED THIS BINDINGS
//        binding.correctButton.setOnClickListener { onCorrect() }
//        binding.skipButton.setOnClickListener { onSkip() }
//        binding.endGameButton.setOnClickListener { onEndGame() }

        return binding.root
    }


    /** Methods for buttons presses **/

//    private fun onSkip() {
//        viewModel.onSkip()
//    }

//    private fun onCorrect() {
//        viewModel.onCorrect()
//    }
//
//    private fun onEndGame() {
//
//        gameFinished();
//    }

    /**
     * Moves to the next word in the list
     */


    /** Methods for updating the UI **/


    private fun gameFinished() {
        Toast.makeText(activity, "Game has just finished", Toast.LENGTH_SHORT).show();

        val action = GameFragmentDirections.actionGameToScore();
        //Add arguments to action to pass to view
        action.score = viewModel.score.value ?: 0;
        NavHostFragment.findNavController(this).navigate(action);

        viewModel.onGameFinishedComplete()

    }


}
