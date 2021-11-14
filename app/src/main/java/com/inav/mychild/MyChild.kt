package com.inav.mychild

import java.time.LocalDate
import java.time.Period

class MyChild(val name : String, private val birthday: LocalDate, val sex: Sex) {

    private val age = Period.between(birthday, LocalDate.now()).years

    // TODO: internationalization
    fun ageToString() : String{
        return "$age ${getAgeSuffix(age)}"
    }

    //Returns string suffix for a specified ages.
    private fun getAgeSuffix(age : Int) : String{

        when (age % 100){
            in 11..19 -> return "лет"
            else -> when (age % 10){
                1 -> return "год"
                in 2..4 -> return "года"
            }
        }
        return "лет"
    }
}