package com.finder.android.mbti.network

import com.finder.android.mbti.network.api.MainApiService
import com.finder.android.mbti.network.response.MessageResponse
import com.finder.android.mbti.util.SecureManager
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.finder.android.mbti.BuildConfig
import com.finder.android.mbti.App

object MainNetWorkUtil {
    val api : MainApiService by lazy { apiInit() }
    private var finderRetrofit : Retrofit? = null

    private val httpClientBuilder = OkHttpClient().newBuilder().apply {
        this.addInterceptor {
            val token =
                SecureManager(App.instance.applicationContext).getToken()
            val builder = it.request().newBuilder()
            if (token.isNotEmpty()) {
                builder.addHeader("Authorization", "Bearer $token")
            }
            val request = builder.build()

            return@addInterceptor it.proceed(request)
        }
    }

    private fun apiInit() : MainApiService {
        val testRetrofit = finderRetrofit
        val retrofit = testRetrofit ?: run {
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(SignNetworkUtil.provideGson()))
                .build()
                .also { finderRetrofit = it }
        }
        return retrofit.create(MainApiService::class.java)
    }

    fun errorMessage(errorBody : ResponseBody?) : MessageResponse? {
        errorBody?.string()?.let {
            return Gson().fromJson(it, MessageResponse::class.java)
        }
        return null
    }
}