package hu.bme.aut.android.cardwise.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import hu.bme.aut.android.cardwise.UserDataRepositoryProvider
import hu.bme.aut.android.cardwise.data.UserDataRepository
import hu.bme.aut.android.cardwise.databinding.FragmentStatsCalendarBinding
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale
import kotlin.concurrent.thread

class StatCalendarFragment : Fragment() {

    private lateinit var binding: FragmentStatsCalendarBinding
    private lateinit var userDataRepository: UserDataRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userDataRepository = (context as? UserDataRepositoryProvider)?.getUserDataRepository()
            ?: throw RuntimeException("Activity must implement the DataRepositoryProvider interface!")
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatsCalendarBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDailyUsage()
    }

    private fun initDailyUsage() {
        thread {
            val usage = userDataRepository.getDailyUsage()
            activity?.runOnUiThread {
                initCalendar(usage)
            }
        }
    }

    private fun initCalendar(datesUsed: List<LocalDate>) {

        binding.calendarView.dayBinder =DayBinder(datesUsed)
        binding.calendarView.monthHeaderBinder = MonthHeaderBinder()

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(3)
        val endMonth = currentMonth
        val daysOfWeek = daysOfWeek()

        binding.calendarView.setup(startMonth, endMonth, daysOfWeek.first())
        binding.calendarView.scrollToMonth(currentMonth)

        binding.calendarView.invalidate()
    }

    private fun createCalendar(year: Int, month: Int, day: Int): Calendar {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar
    }

    class DayBinder(private val datesUsed: List<LocalDate>): MonthDayBinder<DayViewContainer> {
        override fun bind(container: DayViewContainer, data: CalendarDay) {
            container.textView.text = data.date.dayOfMonth.toString()
            if (datesUsed.contains(data.date)) {
                container.textView.setBackgroundColor(Color.LTGRAY)

            }
        }

        override fun create(view: View): DayViewContainer {
            return DayViewContainer(view)
        }
    }

    class MonthHeaderBinder: MonthHeaderFooterBinder<MonthViewContainer> {
        override fun bind(container: MonthViewContainer, data: CalendarMonth) {
            container.textView.text = data.yearMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
        }

        override fun create(view: View): MonthViewContainer {
            return MonthViewContainer(view)
        }
    }
}

