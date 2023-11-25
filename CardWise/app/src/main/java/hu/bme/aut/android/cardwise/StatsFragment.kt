package hu.bme.aut.android.cardwise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.android.cardwise.adapter.StatsFragmentPagerAdapter
import hu.bme.aut.android.cardwise.databinding.FragmentStatsBinding
import hu.bme.aut.android.cardwise.databinding.FragmentStudyListBinding

class StatsFragment : Fragment() {

    private lateinit var binding: FragmentStatsBinding
    private lateinit var pagerAdapter: StatsFragmentPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerAdapter = StatsFragmentPagerAdapter(childFragmentManager)
        binding.statsPager.adapter = pagerAdapter
        binding.statsTabLayout.setupWithViewPager(binding.statsPager)
    }
}