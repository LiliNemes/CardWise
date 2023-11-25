package hu.bme.aut.android.cardwise.fragments

import android.view.View
import com.kizitonwose.calendar.view.ViewContainer
import hu.bme.aut.android.cardwise.databinding.CalendarDayLayoutBinding

class DayViewContainer(view: View) : ViewContainer(view) {

    val textView = CalendarDayLayoutBinding.bind(view).calendarDayText
}