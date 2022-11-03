package com.example.driedpork.coingecko

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object CoingeckoAPI {

    private const val BASE_URL = "https://api.coingecko.com/api/v3/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
//        .addCallAdapterFactory(CoroutineCallAdapterFactory())
//        .client(clientBuilder.build())
        .baseUrl(BASE_URL)
        .build()

    val retrofitService: CoingeckoAPIService by lazy {
        retrofit.create(CoingeckoAPIService::class.java)
    }

}

// To use it you just need to do:
// MyApi.retrofitService
// MyApi.otherService