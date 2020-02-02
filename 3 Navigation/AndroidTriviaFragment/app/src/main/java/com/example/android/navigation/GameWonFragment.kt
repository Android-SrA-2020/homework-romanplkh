/*
 * Copyright 2018, The Android Open Source Project
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

package com.example.android.navigation

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)
        binding.nextMatchButton.setOnClickListener { view: View ->
            view.findNavController()
                    .navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }


        //Extract arguments from binding
        val argsExtracted = GameWonFragmentArgs.fromBundle(arguments!!)

        //Show Toas with args
        Toast.makeText(context, "NumCorrect: ${argsExtracted.numCorrect}, NumQuestions: ${argsExtracted.numQuestions}", Toast.LENGTH_LONG).show()


        //Add options menu
        setHasOptionsMenu(true);

        return binding.root
    }


    //CREATE INTENT
    private fun getShareIntent(): Intent {

        //Extract arguments passed with navigation
        val args = GameWonFragmentArgs.fromBundle(arguments!!);

        //Create Intent of type to SHare
        val shareIntent = Intent(Intent.ACTION_SEND)

        //WHAT TYPE OF INTENT AND WHAT TO PASS WITH IT
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT,
                getString(R.string.share_success_text, args.numCorrect, args.numQuestions))

        return shareIntent;

    }


    //START ACTIVITY WITH MY INTENT
    private fun shareMyQuizResults() {
        //BEGIN SHARING
        startActivity(getShareIntent());
    }


    //INFLATE WINNER MENU
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        //SPECIFY THE MENU VIEW TO INFLATE
        inflater?.inflate(R.menu.winner_menu, menu);


        //IF WE CANNOT SHARE DO NOT DISPLAY IT
        if (getShareIntent().resolveActivity(activity!!.packageManager) == null) {
            menu?.findItem(R.id.share)?.setVisible(false);
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        //IF MENU HAS OPTION TO SELECT, WHEN I SELECT IT START MY SHARE INTENT
        when (item!!.itemId) {
            R.id.share -> shareMyQuizResults();
        }

        return super.onOptionsItemSelected(item);

    }

}
