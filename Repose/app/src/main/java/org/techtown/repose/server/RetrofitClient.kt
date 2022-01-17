package org.techtown.repose.server

import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var instance: Retrofit?=null
    private val gson = GsonBuilder().setLenient().create()

    private const val BASE_URL = "http://34.64.195.161:3000" //에뮬 > laptop 내부ip 10.0.2.2

    //SingleTon

    fun getInstance(): Retrofit{
        if(instance == null){
            instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        Log.e("server","instance OK!")
        return instance!!
    }
}