package com.android.finder

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*
class DateDeserializer : JsonDeserializer<Date> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date {
        if (json == null) { return Date() }
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:SS", Locale.KOREAN)

        return try {
            format.parse(json.asString) ?: Date()
        } catch (e: Exception) {
            try {
                val format2 = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN)
                format2.parse(json.asString) ?: Date()
            } catch (e: java.lang.Exception) {
                Date()
            }
        }
    }
}
