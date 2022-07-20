package com.finder.android.mbti.enumdata

import com.finder.android.mbti.R

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

    val mbtiImageResource : Int
    get() {
        return when(this) {
            ENFJ, ESFP, ISFP, ISFJ -> R.drawable.ic_enfj_character
            INFP, ENTJ, ENTP, ISTJ -> R.drawable.ic_infp_character
            INFJ, ESTP, INTJ, ESFJ -> R.drawable.ic_infj_character
            ENFP, ISTP, INTP, ESTJ -> R.drawable.ic_enfp_character
        }
    }

    val welcomeMentResource : Int
    get() {
        return when(this) {
            ENFJ -> R.string.msg_welcome_enfj
            ESFP -> R.string.msg_welcome_esfp
            ISFP -> R.string.msg_welcome_isfp
            ISFJ -> R.string.msg_welcome_isfj
            INFP -> R.string.msg_welcome_infp
            ENTJ -> R.string.msg_welcome_entj
            ENTP -> R.string.msg_welcome_entp
            ISTJ -> R.string.msg_welcome_istj
            INFJ -> R.string.msg_welcome_infj
            ESTP -> R.string.msg_welcome_estp
            INTJ -> R.string.msg_welcome_intj
            ESFJ -> R.string.msg_welcome_esfj
            ENFP -> R.string.msg_welcome_enfp
            ISTP -> R.string.msg_welcome_istp
            INTP -> R.string.msg_welcome_intp
            ESTJ -> R.string.msg_welcome_estj
        }
    }
    companion object {
        fun getMbtiByString(mbti : String?) : MBTI? = values().find { it.value == mbti }

        fun getAllMbti(isAll : Boolean) : ArrayList<String> {
            val list = ArrayList<String>()
            if(isAll) list.add("전체")
            list.addAll(values().map(MBTI::value))
            return list
        }
    }
}