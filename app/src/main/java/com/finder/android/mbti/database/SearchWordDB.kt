package com.finder.android.mbti.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.finder.android.mbti.database.dao.SearchWordDao
import com.finder.android.mbti.database.entity.SearchWordEntity

@Database(entities = [SearchWordEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class SearchWordDB : RoomDatabase() {
    abstract fun wordDao() : SearchWordDao

    companion object {
        private var INSTANCE : SearchWordDB? = null

        fun getInstance(context : Context) : SearchWordDB? {
            if(INSTANCE == null) {
                synchronized(SearchWordDB::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        SearchWordDB::class.java, "recentSearchWord"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }

    fun destroyInstance() {
        INSTANCE = null
    }
}