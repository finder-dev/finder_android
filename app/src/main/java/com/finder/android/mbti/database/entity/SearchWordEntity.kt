package com.finder.android.mbti.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "recentSearchWord")
data class SearchWordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "search_word") val searchWord: String,
    @ColumnInfo(name = "word_type") val type: Int = 1, //어디 부분에서 사용한 검색 단어인지, 일단 1은 커뮤니티 검색
    @ColumnInfo(name = "last_search_date") var lastSearchDate: Date
) : Comparable<SearchWordEntity> {
    override fun compareTo(other: SearchWordEntity): Int {
        return this.lastSearchDate.compareTo(other.lastSearchDate)
    }
}