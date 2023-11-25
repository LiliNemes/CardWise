package hu.bme.aut.android.cardwise.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import hu.bme.aut.android.cardwise.UserDataRepositoryProvider
import hu.bme.aut.android.cardwise.data.UserDataRepository
import hu.bme.aut.android.cardwise.databinding.FragmentStatsDecksChartBinding
import kotlin.concurrent.thread


class StatDecksChartFragment : Fragment() {

    private lateinit var binding: FragmentStatsDecksChartBinding
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
        binding = FragmentStatsDecksChartBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initChart()
    }

    private fun initChart() {
        thread {
            val barEntries = mutableListOf<BarEntry>()
            val labels = mutableListOf<String>()

            val decks = userDataRepository.getAllDecks()
            var idx = 0
            for (deck in decks) {
                labels.add(deck.name)
                val cardCnt = userDataRepository.getCardsForDeck(deck.id!!).count()
                barEntries.add(BarEntry(idx.toFloat(), cardCnt.toFloat()))
                idx++
            }

            activity?.runOnUiThread {
                binding.chartDeckStat.setDrawBarShadow(false)
                binding.chartDeckStat.setFitBars(true)
                binding.chartDeckStat.setDrawGridBackground(true)
                binding.chartDeckStat.setDrawValueAboveBar(true)

                val barDataSet = BarDataSet(barEntries, "# of Cards")
                barDataSet.color = Color.LTGRAY
                val barData = BarData(barDataSet)
                barData.barWidth = 0.9f
                barData.setValueTextSize(0f)

                val xAxis: XAxis =  binding.chartDeckStat.getXAxis()
                xAxis.setDrawGridLines(false)
                xAxis.textSize = 20f
                xAxis.textColor = Color.BLACK
                xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
                xAxis.xOffset = 80f
                xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                xAxis.labelCount = labels.size

                binding.chartDeckStat.legend.isEnabled = false

                binding.chartDeckStat.data = barData
                binding.chartDeckStat.invalidate()
            }
        }
    }
}