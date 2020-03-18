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

package com.example.android.trackmysleepquality.sleepquality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.PrimaryKey
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import kotlinx.coroutines.*


//INIT CLASS AND PASS 2 ARGS -> 1. RATE NUMBER FOR CURRENT SLEEP and reference to DAO
class SleepQualityViewModel(private val sleepNightKey: Long = 0L, val database: SleepDatabaseDao) : ViewModel() {


    //NEED TO CANCEL ALL COROUTINES
    private val viewModelJob = Job();


    //SET DEFAULT DISPATCHER FOR COROUTINES. YOU CAN CHANGE IT IN FUNCTIONS LATER FOR I/O OPERATIONS
    //BUT MOST OF TIME YOU WILL BE USING MAIN DISPATCHER
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob);


    //NAVIGATION OBSERVABLE
    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()
    val navigateToSleepTracker: LiveData<Boolean?> get() = _navigateToSleepTracker


    // TIME TO MESS AROUND WITH click handlers for each of sleep quality buttons

    //FOLLOW THESE EASY STEPS:
//    Launch a coroutine in the uiScope, and switch to the I/O dispatcher.
//    Get tonight using the sleepNightKey.
//    Set the sleep quality.
//    Update the database.
//    Trigger navigation.

    fun onSetSleepQuality(quality: Int) {

        //START COROUTINE
        uiScope.launch {
            // IO is a thread pool for running operations that access the disk, such as
            // our Room database.
            withContext(Dispatchers.IO) {
                val tonight = database.get(sleepNightKey) ?: return@withContext
                tonight.sleepQuality = quality
                database.update(tonight)
            }

            // Setting this state variable to true will alert the observer and trigger navigation.
            _navigateToSleepTracker.value = true
        }
    }


    


    //Navigation done Handler
    fun doneNavigating() {
        _navigateToSleepTracker.value = null
    }


    //CANCEL ALL COROUTINES
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}