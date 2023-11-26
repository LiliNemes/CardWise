package hu.bme.aut.android.cardwise.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import hu.bme.aut.android.cardwise.fragments.StatCalendarFragment
import hu.bme.aut.android.cardwise.fragments.StatDecksChartFragment
import hu.bme.aut.android.cardwise.fragments.StatUserChartFragment

class StatsFragmentPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm!!) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> StatCalendarFragment()
            1 -> StatDecksChartFragment()
            2 -> StatUserChartFragment()
            else ->  throw Exception("Out of bounds")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Calendar Stats"
            1 -> "Deck Stats"
            2 -> "User Stats"
            else ->  throw Exception("Out of bounds")
        }
    }

}