package com.finder.android.mbti.network

import com.finder.android.mbti.network.api.SignApiService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import com.finder.android.mbti.BuildConfig
import com.finder.android.mbti.DateDeserializer

object SignNetworkUtil {
    val api : SignApiService by lazy { apiInit() }
    private var finderRetrofit : Retrofit? = null
//    private const val SERVER_ADDR = "https://finder777.com"

    private const val SERVER_ADDR = "http://54.180.68.142:8080/"

    private fun apiInit() : SignApiService {
        val testRetrofit = finderRetrofit
        val retrofit = testRetrofit ?: run {
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(provideGson()))
                .build()
                .also { finderRetrofit = it }
        }
        return retrofit.create(SignApiService::class.java)
    }

    fun provideGson() = GsonBuilder().apply {
        this.registerTypeAdapter(Date::class.java, DateDeserializer())
    }.create()
}