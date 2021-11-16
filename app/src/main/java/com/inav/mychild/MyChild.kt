package com.inav.mychild

import android.graphics.PointF
import java.io.Serializable
import java.time.LocalDate
import java.time.Period
import kotlin.random.Random

/*
* Class representing a Child.
* */
class MyChild(val name : String, private val birthday: LocalDate, val sex: Sex) : Serializable {

    val anthropometries = getTestAnthropometries()
    private val age = Period.between(birthday, LocalDate.now()).years

    val heightsAsPoints = mutableListOf<PointF>()
    val weightsAsPoints = mutableListOf<PointF>()
    init {
        anthropometries.forEach {
            val period = (it.date.toEpochDay() - birthday.toEpochDay()).toFloat()
            heightsAsPoints.add(PointF(period, it.height.toFloat()))
            weightsAsPoints.add(PointF(period, it.weight.toFloat()))
        }
    }

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

    private fun getTestAnthropometries() : List<Anthropometry>{
        val anthropometries = mutableListOf<Anthropometry>()

        // Let's make it at least a little bit like the truth
        val curDate = LocalDate.now()
        var maxCount = Period.between(birthday, curDate).years * 12
        if (maxCount == 0)
            maxCount = Period.between(birthday, curDate).months + 1

        var count = Random.nextInt(1, maxCount)

        val heights = mutableListOf<Double>()
        val weights = mutableListOf<Double>()
        val dates = mutableListOf<Long>()

        while(count > 0){
            dates.add(Random.nextLong(birthday.toEpochDay(), curDate.toEpochDay()))
            heights.add(Random.nextDouble(50.0, 200.0))
            weights.add(Random.nextDouble(3.0, 100.0))
            count--
        }
        val sortedDates = dates.sorted()
        val sortedHeights = heights.sorted()
        val sortedWeights = weights.sorted()

        // First date has to be set to a birthday
        anthropometries.add(Anthropometry(birthday, sortedHeights[0], sortedWeights[0]))

        for (i in 1 until dates.size){
            anthropometries.add(Anthropometry(LocalDate.ofEpochDay(sortedDates[i]), sortedHeights[i], sortedWeights[i]))
        }

        return anthropometries
    }
}


