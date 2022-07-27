package com.finder.android.mbti.database.dao

import androidx.room.*
import com.finder.android.mbti.database.entity.SearchWordEntity

@Dao
interface SearchWordDao {
    companion object {
        const val TABLE_NAME = "recentSearchWord"
    }

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll() : List<SearchWordEntity>

    @Query("SELECT * FROM $TABLE_NAME WHERE id=:id")
    fun findById(id : Int) : SearchWordEntity?

    @Query("SELECT * FROM $TABLE_NAME WHERE search_word=:keyword")
    fun findByKeyword(keyword: String): SearchWordEntity?

    @Query("SELECT * FROM $TABLE_NAME WHERE search_word=:keyword AND word_type=:type")
    fun findByKeywordAndType(keyword: String, type : Int) : SearchWordEntity?
    @Insert
    fun insertWord(searchWord : SearchWordEntity)

    @Delete
    fun deleteWord(searchWord: SearchWordEntity)

    @Update
    fun updateWord(searchWord: SearchWordEntity)

    @Query("DELETE FROM $TABLE_NAME")
    fun deleteAll()
}