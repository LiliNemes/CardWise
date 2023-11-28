package hu.bme.aut.android.cardwise.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import hu.bme.aut.android.cardwise.UserDataRepositoryProvider
import hu.bme.aut.android.cardwise.DeckIdProvider
import hu.bme.aut.android.cardwise.data.UserDataRepository
import hu.bme.aut.android.cardwise.databinding.FragmentStudyStatBinding
import kotlin.concurrent.thread

class StudyStatFragment : Fragment() {

    private lateinit var binding: FragmentStudyStatBinding
    private lateinit var userDataRepository: UserDataRepository
    private var deckId: Long = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        deckId = (context as? DeckIdProvider)?.getSelectedDeckId()
            ?: throw RuntimeException("Activity must implement the DeckIdProvider interface!")
        userDataRepository = (context as? UserDataRepositoryProvider)?.getUserDataRepository()
            ?: throw RuntimeException("Activity must implement the DataRepositoryProvider interface!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        loadStats()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyStatBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun loadStats() {
        thread {
            val stats = userDataRepository.getDeckStat(deckId)
            var entries = mutableListOf<PieEntry>()

            if (stats.neverCount > 0)
                entries.add(PieEntry(stats.neverCount.toFloat(), "Never"))
            if (stats.under20 > 0)
                entries.add(PieEntry(stats.under20.toFloat(), "< 20%"))
            if (stats.under50above20 > 0)
                entries.add(PieEntry(stats.under50above20.toFloat(), "20% - 50%"))
            if (stats.under80above50 > 0)
                entries.add(PieEntry(stats.under80above50.toFloat(), "50% - 80%"))
            if (stats.above80 > 0)
                entries.add(PieEntry(stats.above80.toFloat(), "> 80%"))

            val dataSet = PieDataSet(entries, "Statistics")
            dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

            activity?.runOnUiThread {
                val data = PieData(dataSet)
                binding.chartDeckStat.data = data
                binding.chartDeckStat.invalidate()
            }
        }
    }
}