package com.innovitics.android.architecture.assessmentapplication.utils

import android.util.Log
import com.innovitics.android.architecture.assessmentapplication.data.entity.Date
import com.innovitics.android.architecture.assessmentapplication.data.entity.Match
import com.innovitics.android.architecture.assessmentapplication.database.MatchesEntity

lateinit var finalList: ArrayList<Any>
lateinit var map: MutableMap<String, ArrayList<MatchesEntity>>
lateinit var formattedDate: String
var oneArrayList = ArrayList<MatchesEntity>()

fun listConverter(matchesList: List<Match>): ArrayList<Any> {
    var index = -1
    map = HashMap()
    finalList = ArrayList()
    matchesList.forEachIndexed { _, match ->
        // check on match status to return the score
        val score = when (match.status) {
            "FINISHED" -> {
                "${match.score.fullTime.home} - ${match.score.fullTime.away}"
            }
            "HALF_TIME" -> {
                "${match.score.halfTime.home} - ${match.score.halfTime.away}"
            }
            "LIVE" -> {
                "${match.score.fullTime.home} - ${match.score.fullTime.away}"
            }
            else -> {
                "${parseDate(match.utcDate)}"
            }
        }

        // create MatchEntity from match model.
        val entity = MatchesEntity(
            match.id,
            false,
            score,
            match.status,
            parseDate(match.utcDate)!!,
            match.awayTeam.shortName,
            match.homeTeam.shortName
        )

        // format the date to the app date format.
        formattedDate = getDateToFormatted(match.utcDate)!!
        Log.d("Utils", formattedDate)
        if (map.containsKey(formattedDate)) {
            map[formattedDate]?.add(entity)
        } else {
            val entityList = ArrayList<MatchesEntity>()
            entityList.add(entity)
            map[formattedDate] = entityList
        }
    }

    // make the converted list in ascending order.
    val sortedMap: MutableMap<String, ArrayList<MatchesEntity>> = LinkedHashMap()
    map.keys.sorted().forEach { sortedMap[it] = map[it]!! }

    // create from today and tomorrow's matches if available one list.
    if (map.containsKey("Today")) {
        oneArrayList.addAll(map["Today"]!!)
    } else if (map.containsKey("Tomorrow")) {
        oneArrayList.addAll(map["Tomorrow"]!!)
    }
    if (oneArrayList.isNotEmpty()) {
        index = 0
        finalList.add(index, oneArrayList)
    }

    //convert all data header and list of matches and add them in one list.
    for (key in sortedMap.keys) {
        index += 1
        finalList.add(index, Date(key))
        sortedMap[key]?.map {
            index += 1
            finalList.add(index, it)
        }
    }
    return finalList
}