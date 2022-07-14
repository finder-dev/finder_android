package com.android.finder.network

import android.util.Log
import com.android.finder.App
import com.android.finder.network.api.MainApiService
import com.android.finder.network.api.SignApiService
import com.android.finder.util.SecureManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MainNetWorkUtil {
    val api : MainApiService by lazy { apiInit() }
    private var finderRetrofit : Retrofit? = null
    private const val SERVER_ADDR = "https://finder777.com"

    private val httpClientBuilder = OkHttpClient().newBuilder().apply {
        this.addInterceptor {
            val token =
                SecureManager(App.instance.applicationContext).getToken()
            val builder = it.request().newBuilder()
            if (token.isNotEmpty()) {
                builder.addHeader("Authorization", "Bearer $token")
            }
            Log.e("확인", it.request().url.toString())
            val request = builder.build()

            return@addInterceptor it.proceed(request)
        }
    }

    private fun apiInit() : MainApiService {
        val testRetrofit = finderRetrofit
        val retrofit = testRetrofit ?: run {
            Retrofit.Builder()
                .baseUrl("${SERVER_ADDR}/")
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(SignNetworkUtil.provideGson()))
                .build()
                .also { finderRetrofit = it }
        }
        return retrofit.create(MainApiService::class.java)
    }
}