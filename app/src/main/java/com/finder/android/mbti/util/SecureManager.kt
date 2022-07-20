package com.finder.android.mbti.util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.finder.android.mbti.BuildConfig

class SecureManager(context: Context) {

    private val token = "token"
    private val prefs: SharedPreferences by lazy {
        val masterKey =
            MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

        EncryptedSharedPreferences.create(
            context,
            BuildConfig.APPLICATION_ID,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun getToken() :String {
        return prefs.getString(token, "").toString()
    }

    fun setToken(value : String) {
        prefs.edit().putString(token, value).apply()
    }

    fun removeToken() {
        prefs.edit().remove(token).apply()
    }
}