package com.finder.android.mbti.database

import com.finder.android.mbti.App
import com.finder.android.mbti.database.dao.SearchWordDao
import com.finder.android.mbti.database.entity.SearchWordEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.*

object DataBaseUtil {
    private var searchWordDao: SearchWordDao
    private const val MAX_SIZE = 5

    init {
        val db = SearchWordDB.getInstance(App.applicationContext())!!
        searchWordDao = db.wordDao()
    }

    fun getList(): List<SearchWordEntity> {
        return searchWordDao.getAll().sorted().reversed().take(MAX_SIZE)
    }

    fun addWord(word: String): Boolean {
        return try {
            val findWord = searchWordDao.findByKeyword(word)
            if (findWord != null) {
                findWord.lastSearchDate = Calendar.getInstance().time
                searchWordDao.updateWord(findWord)
            } else searchWordDao.insertWord(
                SearchWordEntity(
                    id = 0,
                    searchWord = word,
                    lastSearchDate = Calendar.getInstance().time
                )
            )
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun addWordData(word: SearchWordEntity): Boolean {
        return try {
            val findWord = searchWordDao.findByKeyword(word.searchWord)
            if (findWord != null) {
                findWord.lastSearchDate = Calendar.getInstance().time
                searchWordDao.updateWord(findWord)
            } else searchWordDao.insertWord(word)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun deleteWordData(word: SearchWordEntity): Boolean {
        return try {
            searchWordDao.deleteWord(word)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun deleteAllWord() : Boolean {
        return try {
            searchWordDao.deleteAll()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun updateWordData(word: SearchWordEntity): Boolean {
        return try {
            searchWordDao.updateWord(word)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}