package com.example.netschool.adapters

import android.content.Context
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.WeekViewEntity
import com.example.netschool.model.Lesson

class CalendarEventAdapter (
) : WeekView.SimpleAdapter<Lesson>() {

//    override fun onCreateEntity(
//        context: Context,
//        item: Lesson
//    ): WeekViewEntity {
//        return WeekViewEntity.Event.Builder(item)
//            .setId(item.id)
//            .setTitle(item.obj)
//            .setStartTime(item.startTime)
//            .setEndTime(item.endTime)
//            .build()
//    }


}