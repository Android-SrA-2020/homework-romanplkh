/*
 * Copyright 2019, The Android Open Source Project
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

package com.example.android.trackmysleepquality.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.formatNights
import kotlinx.coroutines.*

/**
 * ViewModel for SleepTrackerFragment.
 */
class SleepTrackerViewModel(
        val database: SleepDatabaseDao,
        application: Application) : AndroidViewModel(application)// it takes the application context as a parameter and makes it available as a property.
{

    //This viewModelJob allows you to cancel all coroutines started by this view model
    // when the view model is no longer used and is destroyed.
    private var viewModelJob = Job();


    //PREPARE COROUTINES SO WE CAN USE THEM IN APPLICATION
    //scope determines what thread the coroutine will run on, and the scope also needs to know about the job
    //Dispatchers.Main means that coroutines launched in the uiScope will run on the main thread.
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    //GET ALL NIGHTS AND ASSIGN THEM IN VARIABLE
    private val nights = database.getAllNights()


    //THIS METHOD IN Util.kt transforms objects in variable nights to String Values
    //THIS BECOMES A LVIE DATA WITH TYPE OF SPANNED SO WE CAN OBSERVE IT
    val nightsString = Transformations.map(nights) { nights ->
        formatNights(nights, application.resources)
    }


    //HOLDS REFERENCE TO ENTITY MODEL
    private var tonight = MutableLiveData<SleepNight?>()


    //Initialize enttity reference live data
    init {
        initializeTonight()
    }


    private fun initializeTonight() {

        //GET DATA IN ASYNC WAY AND STORE IT IN OBSERVABLE
        uiScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }


    //ASYNC FUNCTION
    private suspend fun getTonightFromDatabase(): SleepNight? {
        //return the result from a coroutine that runs in the Dispatchers. IO context.
        // Use the I/O dispatcher, because getting data from the database is an I/O operation
        // and has nothing to do with the UI.
        return withContext(Dispatchers.IO) {


            //If the start and end times are not the same, meaning that the night has already been completed, return null.
            // Otherwise, return the night.
            var night = database.getTonight()
            if (night?.endTimeMilli != night?.startTimeMilli) {
                night = null
            }
            night
        }
    }




    //RECORD SLEEP TIME
    fun onStartTracking() {
        //launch a coroutine in the uiScope, because you need this result to continue and update the UI
        uiScope.launch {
            //Create instance of entity
            val newNight = SleepNight()

            //Add record to db
            insert(newNight)

            //Update observable value when record was inserted
            tonight.value = getTonightFromDatabase()
        }

    }


    //INSERT DATA TO DB
    private suspend fun insert(night: SleepNight) {
        //SINCE IT IS I/O OPERATION USE CONTEXT OF I/O DISPATCHERS
        withContext(Dispatchers.IO) {
            //Insert
            //DB is available through constructor
            database.insert(night)
        }
    }



    //STOP RECORDING
    fun onStopSTracking(){
        uiScope.launch {
            val oldNight = tonight.value ?: return@launch; //syntax specifies the function from which this statement returns,
            // among several nested functions. syntax is return@label. In our case label is @launch


            //Set new value for entity
            oldNight.endTimeMilli = System.currentTimeMillis();

            //Update Database
            update(oldNight);
        }
    }


    //UPDATE IN DB
    private suspend fun update(night: SleepNight) {
        //SINCE IT IS I/O OPERATION USE CONTEXT OF I/O DISPATCHERS
        withContext(Dispatchers.IO) {
            //DB is available through constructor
            database.update(night)
        }
    }




    //CLEAR SCREEN AND ALL IN DB
    fun onClear() {
        uiScope.launch {
            clear()
            tonight.value = null
        }
    }


    //CALL DB METHOD DEFINED IN SleepDatabaseDao Interface
    suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }


    //CANCEL ALL COROUTINES
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}

