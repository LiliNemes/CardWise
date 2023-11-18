package hu.bme.aut.android.cardwise.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class StudyFragmentPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                StudyListFragment()
            }
            1 -> {
                StudyStatFragment()
            }
            else -> {
                StudyListFragment()
            }
        }
    }

}