package hu.bme.aut.android.cardwise.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import hu.bme.aut.android.cardwise.DataRepositoryProvider
import hu.bme.aut.android.cardwise.DeckIdProvider
import hu.bme.aut.android.cardwise.R
import hu.bme.aut.android.cardwise.data.DataRepository
import hu.bme.aut.android.cardwise.databinding.FragmentStudyListBinding
import hu.bme.aut.android.cardwise.databinding.FragmentStudyStatBinding
import kotlin.concurrent.thread

class StudyStatFragment : Fragment() {

    private lateinit var binding: FragmentStudyStatBinding
    private lateinit var dataRepository: DataRepository
    private var deckId: Long = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        deckId = (context as? DeckIdProvider)?.getSelectedDeckId()
            ?: throw RuntimeException("Activity must implement the DeckIdProvider interface!")
        dataRepository = (context as? DataRepositoryProvider)?.getDataRepository()
            ?: throw RuntimeException("Activity must implement the DataRepositoryProvider interface!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        loadStats()
    }

    private fun loadStats() {
        thread {
            val entries = listOf(
                PieEntry(1f, "Taken"),
                PieEntry(2f, "Remaining")
            )

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