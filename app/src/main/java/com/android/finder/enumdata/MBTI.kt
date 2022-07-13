package com.android.finder.enumdata

enum class MBTI(val value : String) {
    INFJ("INFJ"),
    INFP("INFP"),
    INTJ("INTJ"),
    INTP("INTP"),
    ISFJ("ISFJ"),
    ISFP("ISFP"),
    ISTJ("ISTJ"),
    ISTP("ISTP"),
    ENFJ("ENFJ"),
    ENFP("ENFP"),
    ENTJ("ENTJ"),
    ENTP("ENTP"),
    ESFJ("ESFJ"),
    ESFP("ESFP"),
    ESTJ("ESTJ"),
    ESTP("ESTP");

    companion object {
        fun getMbtiByString(mbti : String) : MBTI? = values().find { it.value == mbti }

        fun getAllMbti(isAll : Boolean) : ArrayList<String> {
            val list = ArrayList<String>()
            if(isAll) list.add("전체")
            list.addAll(values().map(MBTI::value))
            return list
        }
    }
}