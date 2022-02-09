package com.peerbitskuldeep.newzpl.api

import com.peerbitskuldeep.newzpl.constants.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

//retrofit singleton class for access every where in project
class RetrofitInstance {

    companion object{

        private val retrofit by lazy {

            val loggingInterceptor = HttpLoggingInterceptor() //log responses of retrofit
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build()

        }

        val api by lazy {
            retrofit.create(NewsAPI::class.java)
        }


    }

}