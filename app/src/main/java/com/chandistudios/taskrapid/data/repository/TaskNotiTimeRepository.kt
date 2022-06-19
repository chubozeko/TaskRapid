package com.chandistudios.taskrapid.data.repository

import com.chandistudios.taskrapid.data.entity.NotiTime

class TaskNotiTimeRepository {
    private val timeList = getNotiTimeList()

    fun getNotTimeWithId(id: Long): NotiTime {
        timeList.forEach { nt ->
            if(nt.id == id)
                return nt
        }
        return timeList.get(0)
    }

    fun getNotiTimeList(): List<NotiTime> {
        return mutableListOf(
            NotiTime(id = 0, name = "At Task time", time = 0),
            NotiTime(id = 1, name = "5 minutes before Task", time = 5),
            NotiTime(id = 2, name = "10 minutes before Task", time = 10),
            NotiTime(id = 3, name = "15 minutes before Task", time = 15),
            NotiTime(id = 4, name = "30 minutes before Task", time = 30),
            NotiTime(id = 5, name = "45 minutes before Task", time = 45),
            NotiTime(id = 6, name = "An hour before Task", time = 60),
        )
    }
}