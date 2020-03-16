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

package com.example.android.trackmysleepquality.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


//Add ANNOTATION TO CONVERT DATA CLASS TO ENTITY
@Entity(tableName = "daily_sleep_quality_table")
//CREATE MODEL ENTITY FOR TABLE
data class SleepNight(
        //SET PRIMARY KEY
        @PrimaryKey(autoGenerate = true)
        var nightId: Long = 0L,

        //GIVE MORE FRIENDLY NAMES TO COLUMNS IN DATABASE
        @ColumnInfo(name = "start_time_milli")
        val startTimeMilli: Long = System.currentTimeMillis(),  // init to current time
        @ColumnInfo(name = "end_time_milli")
        var endTimeMilli: Long = startTimeMilli,// this will tell that it is not initialized yet
        @ColumnInfo(name = "quality_rating")
        var sleepQuality: Int = -1// it is not tracked
)

