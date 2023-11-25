package hu.bme.aut.android.cardwise.fragments

import android.view.View
import com.kizitonwose.calendar.view.ViewContainer
import hu.bme.aut.android.cardwise.databinding.CalendarDayLayoutBinding
import hu.bme.aut.android.cardwise.databinding.CalendarMonthHeaderLayoutBinding

class MonthViewContainer(view: View) : ViewContainer(view) {
    val textView = CalendarMonthHeaderLayoutBinding.bind(view).monthHeaderText
}