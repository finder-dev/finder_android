package com.finder.android.mbti.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

object SettingUtil {
    private val Context.dataStore by preferencesDataStore(name = "onBoarding")

    private val onBoardingKey = booleanPreferencesKey("onBoardingKey")
    private val isAutoLogin = booleanPreferencesKey("isAutoLogin")

    suspend fun setAutoLoginKey(context: Context, isAutoLogin : Boolean) {
        context.dataStore.edit {  it[SettingUtil.isAutoLogin] = isAutoLogin }
    }

    suspend fun getAutoLoginData(context: Context) : Boolean {
        val autoLoginData: Flow<Boolean> = context.dataStore.data
            .catch { emit(emptyPreferences()) }
            .map { it[isAutoLogin] ?: false }
        return try {
            autoLoginData.first()
        } catch (e : Exception) {
            false
        }
    }

    suspend fun setOnBoardingKey(context: Context) {
        context.dataStore.edit {  it[onBoardingKey] = true }
    }

    suspend fun getOnBoardingData(context: Context) : Boolean {
        val onBoardingKeyData: Flow<Boolean> = context.dataStore.data
            .catch { emit(emptyPreferences()) }
            .map { it[onBoardingKey] ?: false }
        return try {
            onBoardingKeyData.first()
        } catch (e : Exception) {
            false
        }
    }

}