package com.android.finder.network

import com.android.finder.DateDeserializer
import com.android.finder.network.api.SignApiService
import com.android.finder.network.response.ErrorResponse
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

object SignNetworkUtil {
    val api : SignApiService by lazy { apiInit() }
    private var finderRetrofit : Retrofit? = null
    private const val SERVER_ADDR = "https://finder777.com"

    private fun apiInit() : SignApiService {
        val testRetrofit = finderRetrofit
        val retrofit = testRetrofit ?: run {
            Retrofit.Builder()
                .baseUrl("$SERVER_ADDR/")
                .addConverterFactory(GsonConverterFactory.create(provideGson()))
                .build()
                .also { finderRetrofit = it }
        }
        return retrofit.create(SignApiService::class.java)
    }
    fun getErrorResponse(errorBody: ResponseBody): ErrorResponse? {
        return finderRetrofit?.responseBodyConverter<ErrorResponse>(
            ErrorResponse::class.java,
            ErrorResponse::class.java.annotations
        )?.convert(errorBody)
    }

    fun provideGson() = GsonBuilder().apply {
        this.registerTypeAdapter(Date::class.java, DateDeserializer())
    }.create()
}