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
 *
 */

package com.example.android.marsrealestate.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = " https://android-kotlin-fun-mars-server.appspot.com/"


//JSON INSTNCE
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

//use a Retrofit builder to create a Retrofit object.
private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()


//define an interface that defines how Retrofit talks to the web server using HTTP requests
interface MarsApiService {
    @GET("realestate")
    fun getProperties():
            Deferred<List<MarsProperty>>   //The Deferred interface defines a coroutine job
    // that returns a result value (Deferred inherits from Job).
    // The Deferred interface includes a method called await(),
    // which causes your code to wait without blocking until the value is ready, and then that value is returned.
}


// interface, define a public object called MarsApi to initialize the Retrofit service
//expose the service to the rest of the app using a public object called MarsApi, and
// lazily initialize the Retrofit service there.
object MarsApi {
    val retrofitService : MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java) }
}